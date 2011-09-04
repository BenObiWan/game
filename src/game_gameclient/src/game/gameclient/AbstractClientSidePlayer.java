package game.gameclient;

import game.communication.IGameServer;
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
 */
public abstract class AbstractClientSidePlayer<CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, PLAYER_CONF, CONF_TYPE>, CLIENT_TYPE extends IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF>, PLAYER_CONF extends IPlayerConfiguration>
		implements
		IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF>
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
	protected IClientGameCreator<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, CLIENT_TYPE> _gameCreator;

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
	 */
	protected AbstractClientSidePlayer(final int iPlayerId,
			final String strName, final IGameServer server,
			final LocalGameClient localGameClient)
	{
		_iPlayerId = iPlayerId;
		_strName = strName;
		_gameServer = server;
		_localGameClient = localGameClient;
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
	public IClientGameCreator<?, ?, ?, ?, ?> getGameCreator()
	{
		synchronized (_lock)
		{
			return _gameCreator;
		}
	}

	@Override
	public boolean isGameInCreation()
	{
		synchronized (_lock)
		{
			return (_game != null);
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
						GameCtrlEventType.PLAYER_LIST_UPDATE, evt.getClass());
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

	/**
	 * Handle a {@link ConfigurationUpdateCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	@SuppressWarnings("unchecked")
	private void handleConfigurationUpdateCrEvent(
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

	/**
	 * Handle a {@link PlayerListUpdateCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	@SuppressWarnings("unused")
	private void handlePlayerListUpdateCrEvent(final PlayerListUpdateCrEvent evt)
	{
		// TODO handlePlayerListUpdateCrEvent
	}

	/**
	 * Handle a {@link GameCreatedCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	private void handleGameCreatedCrEvent(
			@SuppressWarnings("unused") final GameCreatedCrEvent evt)
	{
		synchronized (_lock)
		{
			_game = _gameCreator.createGame();
		}
	}

	/**
	 * Handle a {@link KickedFromGameCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	private void handleKickedFromGameCrEvent(
			@SuppressWarnings("unused") final KickedFromGameCrEvent evt)
	{
		deleteClient();
	}

	/**
	 * Handle a {@link GameLeftCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	private void handleGameLeftCrEvent(
			@SuppressWarnings("unused") final GameLeftCrEvent evt)
	{
		deleteClient();
	}

	/**
	 * Handle a {@link GameDestroyedCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	private void handleGameDestroyedCrEvent(
			@SuppressWarnings("unused") final GameDestroyedCrEvent evt)
	{
		deleteClient();
	}

	/**
	 * Handle a {@link GameFullCrEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	private void handleGameFullCrEvent(
			@SuppressWarnings("unused") final GameFullCrEvent evt)
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
}
