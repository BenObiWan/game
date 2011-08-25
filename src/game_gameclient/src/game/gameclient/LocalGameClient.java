package game.gameclient;

import game.communication.IGameClient;
import game.communication.IGameListDescription;
import game.communication.IGameServer;
import game.communication.action.InconsistentActionTypeException;
import game.communication.action.control.CreateGameCtrlAction;
import game.communication.action.gamectrl.JoinGameCrAction;
import game.communication.event.ControlEventType;
import game.communication.event.IControlEvent;
import game.communication.event.IEvent;
import game.communication.event.IGameCreationEvent;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.communication.event.control.GameCreationStartedCtrlEvent;
import game.communication.event.control.GameJoinedCtrlEvent;
import game.communication.event.control.ServerStateCtrlEvent;
import game.gameserver.IServerSidePlayer;

import java.util.Observable;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.log4j.Logger;

/**
 * Object describing the local GameClient. It receives the Event from all the
 * GameServer, and dispatch them to the different Players.
 * 
 * @author benobiwan
 * 
 */
public final class LocalGameClient extends Observable implements IGameClient
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(LocalGameClient.class);

	/**
	 * The name used locally by this client.
	 */
	private final String _strLocalName;

	/**
	 * Id which will be used for the next player.
	 */
	private int _iNextPlayerId = 1;

	/**
	 * Lock to protect the use of _iNextPlayerId.
	 */
	private final Object _lockNextPlayer = new Object();

	/**
	 * List of server side player on this client.
	 */
	private final ConcurrentSkipListMap<Integer, IServerSidePlayer> _serverSidePlayerList = new ConcurrentSkipListMap<Integer, IServerSidePlayer>();

	/**
	 * List of client side player on this client.
	 */
	private final ConcurrentSkipListMap<Integer, IClientSidePlayer<?, ?, ?>> _clientSidePlayerList = new ConcurrentSkipListMap<Integer, IClientSidePlayer<?, ?, ?>>();

	/**
	 * Creates a new LocalGameClient.
	 * 
	 * @param strLocalName
	 *            the name of the local client.
	 */
	public LocalGameClient(final String strLocalName)
	{
		_strLocalName = strLocalName;
	}

	@Override
	public String getName()
	{
		return _strLocalName;
	}

	@Override
	public void handleEvent(final IGameServer server, final IEvent evt)
			throws InconsistentEventTypeException
	{
		if (evt instanceof IControlEvent)
		{
			handleControlEvent(server, (IControlEvent) evt);
		}
		else if (evt instanceof IGameCreationEvent)
		{
			final IGameCreationEvent event = (IGameCreationEvent) evt;
			final IClientSidePlayer<?, ?, ?> player = _clientSidePlayerList
					.get(Integer.valueOf(event.getPlayerId()));
			if (player == null)
			{
				// TODO player is null
			}
			else if (!player.isGameInCreation())
			{
				// TODO game is not in creation
			}
			else if (event.getGameId() != player.getGameId())
			{
				// TODO game id is different
			}
			else if (server.equals(player.getServer()))
			{
				// TODO the server from which the message is coming is not the
				// good one.
			}
			else
			{
				player.handleGameCreationEvent(event);
			}
		}
		else if (evt instanceof IGameEvent)
		{
			final IGameEvent event = (IGameEvent) evt;
			final IClientSidePlayer<?, ?, ?> player = _clientSidePlayerList
					.get(Integer.valueOf(event.getPlayerId()));
			if (player == null)
			{
				// TODO player is null
			}
			else if (player.isGameInCreation())
			{
				// TODO game is in creation
			}
			else if (event.getGameId() != player.getGameId())
			{
				// TODO game id is different
			}
			else if (server.equals(player.getServer()))
			{
				// TODO the server from which the message is coming is not the
				// good one.
			}
			else
			{
				player.handleGameEvent(event);
			}
		}
		else
		{
			LOGGER.error("Unknown event type.");
		}
	}

	@Override
	public void registerGameServer(final IGameServer server)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public int compareTo(final IGameClient o)
	{
		return _strLocalName.compareTo(o.getName());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_strLocalName == null) ? 0 : _strLocalName.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final LocalGameClient other = (LocalGameClient) obj;
		if (_strLocalName == null)
		{
			if (other._strLocalName != null)
			{
				return false;
			}
		}
		else if (!_strLocalName.equals(other._strLocalName))
		{
			return false;
		}
		return true;
	}

	/**
	 * Method handling all the control event.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             the type field of the {@link IControlEvent} and it's class
	 *             are inconsistent.
	 */
	private void handleControlEvent(final IGameServer server,
			final IControlEvent evt) throws InconsistentEventTypeException
	{
		switch (evt.getType())
		{
		case GAME_CREATION_STARTED:
			if (evt instanceof GameCreationStartedCtrlEvent)
			{
				handleControlEvent(server, (GameCreationStartedCtrlEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						ControlEventType.GAME_CREATION_STARTED, evt.getClass());
			}
			break;
		case GAME_JOINED:
			if (evt instanceof GameJoinedCtrlEvent)
			{
				handleControlEvent(server, (GameJoinedCtrlEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						ControlEventType.GAME_JOINED, evt.getClass());
			}
			break;
		case SERVER_STATE:
			if (evt instanceof ServerStateCtrlEvent)
			{
				handleControlEvent(server, (ServerStateCtrlEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						ControlEventType.SERVER_STATE, evt.getClass());
			}
			break;
		}
	}

	/**
	 * Handle a {@link GameJoinedCtrlEvent}.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 */
	private void handleControlEvent(final IGameServer server,
			final GameJoinedCtrlEvent evt)
	{
		final IClientGameCreator<?, ?, ?, ?> gameCreator = evt
				.getClientGameCreator();
		gameCreator.initialize(true, this, server, evt.getGameId());
		final IClientSidePlayer<?, ?, ?> player = gameCreator.createPlayer(evt
				.getPlayerId());
		_clientSidePlayerList.put(Integer.valueOf(evt.getPlayerId()), player);
		setChanged();
		notifyObservers();
		// TODO handleControlEvent GameJoinedEvent
	}

	/**
	 * Handle a {@link GameCreationStartedCtrlEvent}.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 */
	private void handleControlEvent(final IGameServer server,
			final GameCreationStartedCtrlEvent evt)
	{
		final IClientGameCreator<?, ?, ?, ?> gameCreator = evt
				.getClientGameCreator();
		gameCreator.initialize(true, this, server, evt.getGameId());
		final IClientSidePlayer<?, ?, ?> player = gameCreator.createPlayer(evt
				.getPlayerId());
		_clientSidePlayerList.put(Integer.valueOf(evt.getPlayerId()), player);
		setChanged();
		notifyObservers();
		// TODO handleControlEvent GameCreationStartedCtrlEvent
	}

	/**
	 * Handle a {@link ServerStateCtrlEvent}.
	 * 
	 * @param server
	 *            the game server from which the control event is coming.
	 * @param evt
	 *            the event to handle.
	 */
	private void handleControlEvent(final IGameServer server,
			final ServerStateCtrlEvent evt)
	{
		server.updateServerState(evt.getServerState());
		setChanged();
		notifyObservers();
		// TODO update game list
	}

	/**
	 * Creates a new game on the specified {@link IGameServer}.
	 * 
	 * @param gameDescription
	 *            the description of the game to create.
	 * @param server
	 *            the {@link IGameServer} on which to create the game.
	 */
	public void sendCreateGame(final IGameListDescription gameDescription,
			final IGameServer server)
	{
		final CreateGameCtrlAction act = new CreateGameCtrlAction(
				gameDescription.createServerGameCreator(), getNextPlayerId());
		try
		{
			server.handleAction(this, act);
		}
		catch (final InconsistentActionTypeException e)
		{
			LOGGER.error(e);
		}
	}

	/**
	 * Join a game on the specified {@link IGameServer}.
	 * 
	 * @param server
	 *            the {@link IGameServer} on which to join a game.
	 * @param iGameId
	 *            the id to join.
	 * @param iPlayerId
	 *            the id of the player which want to join.
	 */
	public void sendJoinGame(final IGameServer server, final int iGameId,
			final int iPlayerId)
	{
		final JoinGameCrAction act = new JoinGameCrAction(iGameId, iPlayerId);
		try
		{
			server.handleAction(this, act);
		}
		catch (final InconsistentActionTypeException e)
		{
			LOGGER.error(e);
		}
	}

	/**
	 * Get the id which will be used for the next player.
	 * 
	 * @return the id which will be used for the next player.
	 */
	private int getNextPlayerId()
	{
		synchronized (_lockNextPlayer)
		{
			return _iNextPlayerId++;
		}
	}

	@Override
	public IServerSidePlayer getServerSidePlayer(final int iPlayerId)
	{
		return _serverSidePlayerList.get(Integer.valueOf(iPlayerId));
	}

	@Override
	public void addServerSidePlayer(final IServerSidePlayer player)
	{
		_serverSidePlayerList.put(Integer.valueOf(player.getId()), player);
	}

	@Override
	public void removeServerSidePlayer(final IServerSidePlayer player)
	{
		_serverSidePlayerList.remove(Integer.valueOf(player.getId()));
	}

	@Override
	public boolean containServerSidePlayer(final IServerSidePlayer player)
	{
		return _serverSidePlayerList
				.containsKey(Integer.valueOf(player.getId()));
	}

	/**
	 * Remove an {@link IClientSidePlayer} from the game client.
	 * 
	 * @param player
	 *            the {@link IClientSidePlayer} to remove.
	 */
	public void removeClientSidePlayer(final IClientSidePlayer<?, ?, ?> player)
	{
		_clientSidePlayerList.remove(Integer.valueOf(player.getId()));
		setChanged();
		notifyObservers();
	}
}
