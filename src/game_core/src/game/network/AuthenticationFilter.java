package game.network;

import game.network.config.INetworkConfiguration;
import game.network.config.RegistrationType;
import game.network.config.WrongAuthenticationException;
import game.network.messages.AbstractMessage;
import game.network.messages.AuthenticateMessage;
import game.network.messages.AuthenticationSuccessfulMessage;
import game.network.messages.MessageType;
import game.network.messages.RegisterMessage;
import game.network.messages.RegistrationErrorMessage;
import game.network.messages.RequestAuthenticationMessage;
import game.network.messages.WrongAuthenticationMessage;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An IoFilter which check, when a message is received, whether the connection
 * has been authenticated. If it hasn't ask for authentication and drop the
 * message. Authentication and registration message are delegated to a
 * ClientAuthenticator.
 * 
 * @author benobiwan
 * 
 */
public final class AuthenticationFilter extends IoFilterAdapter
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationFilter.class);

	/**
	 * ClientAuthenticator object used to authenticate clients.
	 */
	private final ClientAuthenticator _clientAuth;
	/**
	 * DistantPeerList object containing the list of all connected clients.
	 */
	private final ConnectionList _connectionList;

	/**
	 * The RequestAuthenticationMessage send upon connection to each client.
	 */
	private final RequestAuthenticationMessage _requestMessage;

	/**
	 * Create a new AuthenticationFilter.
	 * 
	 * @param clientAuth
	 *            the client authenticator.
	 * @param distantPeerList
	 *            the object containing the list of all connected clients.
	 */
	public AuthenticationFilter(final ClientAuthenticator clientAuth,
			final ConnectionList distantPeerList)
	{
		_clientAuth = clientAuth;
		_connectionList = distantPeerList;
		_requestMessage = new RequestAuthenticationMessage(
				_clientAuth.getRegistrationType());
	}

	/**
	 * Get the RegistrationType of the server.
	 * 
	 * @return the RegistrationType of the server.
	 */
	public RegistrationType getRegistrationType()
	{
		return _clientAuth.getRegistrationType();
	}

	@Override
	public void sessionOpened(final NextFilter nextFilter,
			final IoSession session)
	{
		// TODO really necessary?
		// request for authentication.
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Auth filter send request message.");
		}
		session.write(_requestMessage);
		nextFilter.sessionOpened(session);
	}

	@Override
	public void sessionClosed(final NextFilter nextFilter,
			final IoSession session)
	{
		final DistantGameClient cli = ((DistantGameClient) session
				.getAttribute(INetworkConfiguration.PEER_ATTRIBUTE));
		if (cli != null)
		{
			_connectionList.markClientAsDisconnected(cli);
		}
		nextFilter.sessionClosed(session);
	}

	@Override
	public void messageReceived(final NextFilter nextFilter,
			final IoSession session, final Object message)
	{
		DistantGameClient cli = ((DistantGameClient) session
				.getAttribute(INetworkConfiguration.PEER_ATTRIBUTE));
		final MessageType type = ((AbstractMessage) message).getMessageType();

		if (type == MessageType.AUTHENTICATE
				&& message instanceof AuthenticateMessage)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug(((AbstractMessage) message).getMessageType()
						.toString());
			}
			final AuthenticateMessage mess = (AuthenticateMessage) message;
			if (cli == null)
			{
				try
				{
					cli = _clientAuth.authenticateClient(session, mess.getId(),
							mess.getAuth(), mess.getConnectionId());
					session.setAttribute(INetworkConfiguration.PEER_ATTRIBUTE,
							cli);
					session.write(new AuthenticationSuccessfulMessage(cli
							.getConnectionId().longValue()));
				}
				catch (final WrongAuthenticationException e)
				{
					session.write(new WrongAuthenticationMessage(e.getMessage()));
				}
			}
			else
			{
				// TODO received an AuthenticateMessage but already
				// Authenticated
			}
		}
		else if (type == MessageType.REGISTER
				&& message instanceof RegisterMessage)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug(((AbstractMessage) message).getMessageType()
						.toString());
			}
			final RegisterMessage mess = (RegisterMessage) message;
			if (cli == null)
			{
				try
				{
					cli = _clientAuth.registerClient(session, mess.getId(),
							mess.getAuth());
					session.setAttribute(INetworkConfiguration.PEER_ATTRIBUTE,
							cli);
				}
				catch (final WrongAuthenticationException e)
				{
					session.write(new RegistrationErrorMessage(e.getMessage()));
				}
			}
			else
			{
				// TODO received an RegisterMessage but already Authenticated
			}
		}
		else
		{
			// message not destined to this filter
			if (cli != null)
			{
				// client is authenticated, continue.
				nextFilter.messageReceived(session, message);
			}
			else
			{
				// client is unauthenticated, request for authentication and
				// drop received message.
				session.write(_requestMessage);
			}
		}
	}
}
