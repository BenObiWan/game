package game.network;

import game.communication.IGameClient;
import game.communication.event.InconsistentEventTypeException;
import game.network.config.INetworkConfiguration;
import game.network.config.RegistrationType;
import game.network.messages.AbstractMessage;
import game.network.messages.AuthenticateMessage;
import game.network.messages.AuthenticationSuccessfulMessage;
import game.network.messages.GameEventMessage;
import game.network.messages.MessageType;
import game.network.messages.RegistrationErrorMessage;
import game.network.messages.RequestAuthenticationMessage;
import game.network.messages.UnexpectedMessage;
import game.network.messages.WrongAuthenticationMessage;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

/**
 * A handler for the client part of the program.
 * 
 * @author benobiwan
 * 
 */
public final class ClientHandler extends NetworkHandler
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = Logger.getLogger(ClientHandler.class);

	/**
	 * The local game client.
	 */
	private final IGameClient _localClient;

	/**
	 * Create a new ClientHandler.
	 * 
	 * @param networkConfiguration
	 *            the network configuration.
	 * @param connectionList
	 *            list of all the network connections of this host.
	 * @param localClient
	 *            the local game client.
	 */
	public ClientHandler(final INetworkConfiguration networkConfiguration,
			final ConnectionList connectionList, final IGameClient localClient)
	{
		super(networkConfiguration, connectionList);
		_localClient = localClient;
	}

	@Override
	public void messageReceived(final IoSession session, final Object message)
	{
		if (message instanceof AbstractMessage)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug(((AbstractMessage) message).getMessageType());
			}
			// load the DistantGameServer attribute
			final Object attribute = session
					.getAttribute(INetworkConfiguration.PEER_ATTRIBUTE);
			if (attribute != null && attribute instanceof DistantGameServer)
			{
				final DistantGameServer server = (DistantGameServer) attribute;
				try
				{
					// select action based on message type
					switch (((AbstractMessage) message).getMessageType())
					{
					case GAME_EVENT:
						if (message instanceof GameEventMessage)
						{
							handleMessage(session, server,
									(GameEventMessage) message);
						}
						else
						{
							throw new InconsistentMessageTypeException(
									MessageType.GAME_EVENT, message.getClass());
						}
						break;
					case AUTHENTICATION_SUCCESSFUL:
						if (message instanceof AuthenticationSuccessfulMessage)
						{
							handleMessage(session, server,
									(AuthenticationSuccessfulMessage) message);
						}
						else
						{
							throw new InconsistentMessageTypeException(
									MessageType.AUTHENTICATION_SUCCESSFUL,
									message.getClass());
						}
						break;
					case REQUEST_AUTHENTICATION:
						if (message instanceof RequestAuthenticationMessage)
						{
							handleMessage(session, server,
									(RequestAuthenticationMessage) message);
						}
						else
						{
							throw new InconsistentMessageTypeException(
									MessageType.REQUEST_AUTHENTICATION,
									message.getClass());
						}
						break;
					case WRONG_AUTHENTICATION:
						if (message instanceof RequestAuthenticationMessage)
						{
							handleMessage(session, server,
									(WrongAuthenticationMessage) message);
						}
						else
						{
							throw new InconsistentMessageTypeException(
									MessageType.WRONG_AUTHENTICATION,
									message.getClass());
						}
						break;
					case REGISTRATION_ERROR:
						if (message instanceof RequestAuthenticationMessage)
						{
							handleMessage(session, server,
									(RequestAuthenticationMessage) message);
						}
						else
						{
							throw new InconsistentMessageTypeException(
									MessageType.REGISTRATION_ERROR,
									message.getClass());
						}
						break;
					default:
						session.write(new UnexpectedMessage());
						break;
					}
				}
				catch (final InconsistentMessageTypeException e)
				{
					LOGGER.error(e.getMessage());
				}
			}
			else
			{
				LOGGER.error("IoSession doesn't have an attached DistantGameServer, should never happen.");
			}
		}
	}

	/**
	 * Handle a {@link GameEventMessage}.
	 * 
	 * @param session
	 *            the {@link IoSession} on which we received the message.
	 * @param server
	 *            the distant server which send the message.
	 * @param message
	 *            the received message.
	 */
	private void handleMessage(
			@SuppressWarnings("unused") final IoSession session,
			final DistantGameServer server, final GameEventMessage message)
	{
		try
		{
			_localClient.handleEvent(server, message.getEvent());
		}
		catch (final InconsistentEventTypeException e)
		{
			LOGGER.error(e);
		}
	}

	/**
	 * Handle a {@link AuthenticationSuccessfulMessage}.
	 * 
	 * @param session
	 *            the {@link IoSession} on which we received the message.
	 * @param server
	 *            the distant server which send the message.
	 * @param message
	 *            the received message.
	 */
	private void handleMessage(
			@SuppressWarnings("unused") final IoSession session,
			final DistantGameServer server,
			final AuthenticationSuccessfulMessage message)
	{
		server.setConnectionId(message.getConnectionId());
	}

	/**
	 * Handle a {@link RequestAuthenticationMessage}.
	 * 
	 * @param session
	 *            the {@link IoSession} on which we received the message.
	 * @param server
	 *            the distant server which send the message.
	 * @param message
	 *            the received message.
	 */
	private void handleMessage(final IoSession session,
			final DistantGameServer server,
			final RequestAuthenticationMessage message)
	{
		final RegistrationType regType = message.getRegistrationType();
		AuthenticateMessage newMess;
		if (regType == RegistrationType.NONE
				|| (regType == RegistrationType.OPTIONAL && !server
						.isRegistered()))
		{
			newMess = new AuthenticateMessage(server.getClientName());
			session.write(newMess);
		}
		else
		{
			// TODO soit envoyer l'id soit la demander au client
		}
	}

	/**
	 * Handle a {@link RegistrationErrorMessage}.
	 * 
	 * @param session
	 *            the {@link IoSession} on which we received the message.
	 * @param server
	 *            the distant server which send the message.
	 * @param message
	 *            the received message.
	 */
	@SuppressWarnings("unused")
	private void handleMessage(final IoSession session,
			final DistantGameServer server,
			final RegistrationErrorMessage message)
	{
		// TODO message REGISTRATION_ERROR dans le client.
	}

	/**
	 * Handle a {@link WrongAuthenticationMessage}.
	 * 
	 * @param session
	 *            the {@link IoSession} on which we received the message.
	 * @param server
	 *            the distant server which send the message.
	 * @param message
	 *            the received message.
	 */
	@SuppressWarnings("unused")
	private void handleMessage(final IoSession session,
			final DistantGameServer server,
			final WrongAuthenticationMessage message)
	{
		// TODO message WRONG_AUTHENTICATION dans le client.
	}
}
