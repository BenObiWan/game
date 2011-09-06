package game.network;

import game.common.IGameServer;
import game.communication.action.AbstractAction;
import game.communication.action.InconsistentActionTypeException;
import game.network.config.INetworkConfiguration;
import game.network.config.INetworkServerConfiguration;
import game.network.messages.AbstractMessage;
import game.network.messages.GameActionMessage;
import game.network.messages.MessageType;
import game.network.messages.UnexpectedMessage;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A handler for the server part of the program.
 * 
 * @author benobiwan
 * 
 */
public final class ServerHandler extends NetworkHandler
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerHandler.class);

	/**
	 * Configuration of the network server.
	 */
	@SuppressWarnings("unused")
	private final INetworkServerConfiguration _networkServerConfiguration;

	/**
	 * Local game server object.
	 */
	private final IGameServer _localServer;

	/**
	 * Create a new ServerHandler.
	 * 
	 * @param networkConfiguration
	 *            the network configuration.
	 * @param connectionList
	 *            list of all the network connections of this host.
	 * @param localServer
	 *            the local game server.
	 * @param networkServerConfiguration
	 *            configuration of the network server.
	 */
	public ServerHandler(final INetworkConfiguration networkConfiguration,
			final ConnectionList connectionList, final IGameServer localServer,
			final INetworkServerConfiguration networkServerConfiguration)
	{
		super(networkConfiguration, connectionList);
		_localServer = localServer;
		_networkServerConfiguration = networkServerConfiguration;
	}

	@Override
	public void messageReceived(final IoSession session, final Object message)
	{
		if (message instanceof AbstractMessage)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug(((AbstractMessage) message).getMessageType()
						.toString());
			}
			// load the DistantGameClient attribute
			final Object attribute = session
					.getAttribute(INetworkConfiguration.PEER_ATTRIBUTE);
			if (attribute != null && attribute instanceof DistantGameClient)
			{
				final DistantGameClient client = (DistantGameClient) attribute;
				try
				{
					// select action based on message type
					switch (((AbstractMessage) message).getMessageType())
					{
					case GAME_ACTION:
						if (message instanceof GameActionMessage)
						{
							handleMessage(session, client,
									(GameActionMessage) message);
						}
						else
						{
							throw new InconsistentMessageTypeException(
									MessageType.GAME_ACTION, message.getClass());
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
				LOGGER.error("IoSession doesn't have an attached DistantGameClient, should never happen.");
			}
		}
	}

	/**
	 * Handle a {@link GameActionMessage}.
	 * 
	 * @param session
	 *            the {@link IoSession} on which we received the message.
	 * @param client
	 *            the distant client which send the message.
	 * @param message
	 *            the received message.
	 */
	private void handleMessage(
			@SuppressWarnings("unused") final IoSession session,
			final DistantGameClient client, final GameActionMessage message)
	{
		final AbstractAction act = message.getAction();
		try
		{
			_localServer.handleAction(client, act);
		}
		catch (final InconsistentActionTypeException e)
		{
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}
}
