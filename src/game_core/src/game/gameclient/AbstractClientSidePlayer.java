package game.gameclient;

import game.common.IGameServer;
import game.communication.event.GameCreationEventType;
import game.communication.event.GameCtrlEventType;
import game.communication.event.IGameCreationEvent;
import game.communication.event.IGameCtrlEvent;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.communication.event.gamecreation.ConfigurationUpdateCrEvent;
import game.communication.event.gamecreation.GameCreatedCrEvent;
import game.communication.event.gamectrl.GameDestroyedCrEvent;
import game.communication.event.gamectrl.GameFullCrEvent;
import game.communication.event.gamectrl.GameLeftCrEvent;
import game.communication.event.gamectrl.KickedFromGameCrEvent;
import game.communication.event.gamectrl.PlayerListUpdateCrEvent;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

import com.google.common.eventbus.EventBus;

/**
 * Abstract implementation of the {@link IClientSidePlayer} interface.
 * Description of a player on the client side.
 * 
 * @author benobiwan
 * 
 * @param <CLIENT_GAME_TYPE>
 *            the type of game to create.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure the game.
 * @param <EVENT_TYPE>
 *            the type of {@link IGameEvent} handled by the game.
 * @param <CLIENT_TYPE>
 *            the type of client.
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration}.
 * @param <CLIENT_CHANGE_LISTENER>
 *            the type of {@link IClientSidePlayerChangeListener} for this
 *            player.
 */
public abstract class AbstractClientSidePlayer<CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, PLAYER_CONF, CONF_TYPE>, CLIENT_TYPE extends IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, CLIENT_CHANGE_LISTENER>, PLAYER_CONF extends IPlayerConfiguration, CLIENT_CHANGE_LISTENER extends IClientSidePlayerChangeListener>
		implements
		IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, CLIENT_CHANGE_LISTENER>
{
	/**
	 * Id of the player.
	 */
	protected final int _iPlayerId;

	/**
	 * The name of the player.
	 */
	protected final String _strName;

	/**
	 * The game the client joined.
	 */
	protected CLIENT_GAME_TYPE _game;

	/**
	 * The game in creation the client joined.
	 */
	protected final IClientGameCreator<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, CLIENT_TYPE, CLIENT_CHANGE_LISTENER> _gameCreator;

	/**
	 * The configuration of the game.
	 */
	protected CONF_TYPE _conf;

	/**
	 * The configuration of the player.
	 */
	protected PLAYER_CONF _playerConf;

	/**
	 * Lock for accessing the different parameters of this
	 * AbstractClientSidePlayer.
	 */
	protected final Object _lock = new Object();

	/**
	 * The {@link IGameServer} where this client is playing.
	 */
	protected final IGameServer _gameServer;

	/**
	 * The {@link LocalGameClient}.
	 */
	protected final LocalGameClient _localGameClient;

	/**
	 * {@link EventBus} used to propagates events to all change listener of this
	 * player.
	 */
	protected final EventBus _eventBus = new EventBus();

	/**
	 * Creates a new AbstractClientSidePlayer.
	 * 
	 * @param iPlayerId
	 *            id of the player.
	 * @param strName
	 *            the name of the player.
	 * @param server
	 *            the {@link IGameServer} where this client is playing.
	 * @param localGameClient
	 *            the {@link LocalGameClient}.
	 * @param gameCreator
	 *            the {@link IClientGameCreator} which created this player.
	 */
	protected AbstractClientSidePlayer(
			final int iPlayerId,
			final String strName,
			final IGameServer server,
			final LocalGameClient localGameClient,
			final IClientGameCreator<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, CLIENT_TYPE, CLIENT_CHANGE_LISTENER> gameCreator)
	{
		_iPlayerId = iPlayerId;
		_strName = strName;
		_gameServer = server;
		_localGameClient = localGameClient;
		_gameCreator = gameCreator;
	}

	@Override
	public String getName()
	{
		return _strName;
	}

	@Override
	public int getId()
	{
		return _iPlayerId;
	}

	@Override
	public IClientSideGame<?, ?, ?> getGame()
	{
		synchronized (_lock)
		{
			return _game;
		}
	}

	@Override
	public IClientGameCreator<?, ?, ?, ?, ?, ?> getGameCreator()
	{
		return _gameCreator;
	}

	@Override
	public boolean isGameInCreation()
	{
		synchronized (_lock)
		{
			return (_game == null);
		}
	}

	@Override
	public int getGameId()
	{
		synchronized (_lock)
		{
			if (isGameInCreation())
			{
				return _gameCreator.getGameId();
			}
			return _game.getGameId();
		}
	}

	@Override
	public IGameServer getServer()
	{
		return _gameServer;
	}

	@Override
	public void handleGameCreationEvent(final IGameCreationEvent evt)
			throws InconsistentEventTypeException
	{
		switch (evt.getType())
		{
		case CONFIGURATION_UPDATE:
			if (evt instanceof ConfigurationUpdateCrEvent)
			{
				handleConfigurationUpdateCrEvent((ConfigurationUpdateCrEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						GameCreationEventType.CONFIGURATION_UPDATE,
						evt.getClass());
			}
			break;
		case GAME_CREATED:
			if (evt instanceof GameCreatedCrEvent)
			{
				handleGameCreatedCrEvent((GameCreatedCrEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						GameCreationEventType.GAME_CREATED, evt.getClass());
			}
			break;

		}
	}

	@Override
	public void handleGameCtrlEvent(final IGameCtrlEvent evt)
			throws InconsistentEventTypeException
	{
		if (isGameInCreation())
		{
			_gameCreator.handleGameCtrlEvent(evt);
		}
		else
		{
			switch (evt.getType())
			{
			case PLAYER_LIST_UPDATE:
				if (evt instanceof PlayerListUpdateCrEvent)
				{
					handlePlayerListUpdateCrEvent((PlayerListUpdateCrEvent) evt);
				}
				else
				{
					throw new InconsistentEventTypeException(
							GameCtrlEventType.PLAYER_LIST_UPDATE,
							evt.getClass());
				}
				break;
			case KICKED_FROM_GAME:
				if (evt instanceof KickedFromGameCrEvent)
				{
					handleKickedFromGameCrEvent((KickedFromGameCrEvent) evt);
				}
				else
				{
					throw new InconsistentEventTypeException(
							GameCtrlEventType.KICKED_FROM_GAME, evt.getClass());
				}
				break;
			case GAME_LEFT:
				if (evt instanceof GameLeftCrEvent)
				{
					handleGameLeftCrEvent((GameLeftCrEvent) evt);
				}
				else
				{
					throw new InconsistentEventTypeException(
							GameCtrlEventType.GAME_LEFT, evt.getClass());
				}
				break;
			case GAME_DESTROYED:
				if (evt instanceof GameDestroyedCrEvent)
				{
					handleGameDestroyedCrEvent((GameDestroyedCrEvent) evt);
				}
				else
				{
					throw new InconsistentEventTypeException(
							GameCtrlEventType.GAME_DESTROYED, evt.getClass());
				}
				break;
			case GAME_FULL:
				if (evt instanceof GameFullCrEvent)
				{
					handleGameFullCrEvent((GameFullCrEvent) evt);
				}
				else
				{
					throw new InconsistentEventTypeException(
							GameCtrlEventType.GAME_FULL, evt.getClass());
				}
				break;
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void handleConfigurationUpdateCrEvent(
			final ConfigurationUpdateCrEvent evt)
	{
		final IGameConfiguration<?> conf = evt.getGameConfiguration();
		try
		{
			synchronized (_lock)
			{
				_conf = (CONF_TYPE) conf;
			}
		}
		catch (final ClassCastException e)
		{
			// TODO class error
		}
	}

	@Override
	public void handlePlayerListUpdateCrEvent(final PlayerListUpdateCrEvent evt)
	{
		// TODO handlePlayerListUpdateCrEvent
	}

	@Override
	public void handleGameCreatedCrEvent(final GameCreatedCrEvent evt)
	{
		synchronized (_lock)
		{
			_game = _gameCreator.createGame();
		}
	}

	@Override
	public void handleKickedFromGameCrEvent(final KickedFromGameCrEvent evt)
	{
		deleteClient();
	}

	@Override
	public void handleGameLeftCrEvent(final GameLeftCrEvent evt)
	{
		deleteClient();
	}

	@Override
	public void handleGameDestroyedCrEvent(final GameDestroyedCrEvent evt)
	{
		deleteClient();
	}

	@Override
	public void handleGameFullCrEvent(final GameFullCrEvent evt)
	{
		deleteClient();
	}

	/**
	 * Delete this {@link IClientSidePlayer} from the {@link LocalGameClient}.
	 */
	private void deleteClient()
	{
		_localGameClient.removeClientSidePlayer(this);
	}

	@Override
	public PLAYER_CONF getPlayerConfiguration()
	{
		synchronized (_lock)
		{
			return _playerConf;
		}
	}

	@Override
	public void registerClientChangeListener(final CLIENT_CHANGE_LISTENER o)
	{
		_eventBus.register(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPlayerConfiguration(final IPlayerConfiguration conf)
	{
		try
		{
			synchronized (_lock)
			{
				_playerConf = (PLAYER_CONF) conf;
			}
		}
		catch (final ClassCastException e)
		{
			// TODO class error
		}
	}
}
