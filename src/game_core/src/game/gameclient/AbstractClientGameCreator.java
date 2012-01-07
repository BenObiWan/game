package game.gameclient;

import game.common.IGameServer;
import game.common.IPlayerDescription;
import game.communication.action.InconsistentActionTypeException;
import game.communication.action.gamecreation.UpdateStatusCrAction;
import game.communication.event.IGameCreationEvent;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

/**
 * Abstract implementation of the {@link IClientGameCreator} interface. Used on
 * the client side to join a game.
 * 
 * @author benobiwan
 * 
 * @param <CLIENT_GAME_TYPE>
 *            the type of game to create.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure the game.
 * @param <EVENT_TYPE>
 *            the type of {@link IGameEvent} handled by the game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IClientSidePlayer} playing this game.
 * @param <PLAYER_CONF>
 *            the type of player configuration.
 * @param <CLIENT_CHANGE_LISTENER>
 *            the type of {@link IClientSidePlayerChangeListener} for this game
 *            creator.
 */
public abstract class AbstractClientGameCreator<CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, PLAYER_CONF, CONF_TYPE>, PLAYER_CONF extends IPlayerConfiguration, PLAYER_TYPE extends IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, CLIENT_CHANGE_LISTENER>, CLIENT_CHANGE_LISTENER extends IClientSidePlayerChangeListener>
		implements
		IClientGameCreator<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, PLAYER_TYPE, CLIENT_CHANGE_LISTENER>
{
	/**
	 * Logger object.
	 */
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AbstractClientGameCreator.class);

	/**
	 * Lock to protect the access to the other parameters of the object.
	 */
	protected transient final Object _lock = new Object();

	/**
	 * The game server on which this game is created.
	 */
	protected transient IGameServer _gameServer;

	/**
	 * Boolean telling whether the local client is the creator of this game.
	 */
	protected transient boolean _bIsCreator;

	/**
	 * The local game client.
	 */
	protected transient LocalGameClient _gameClient;

	/**
	 * Id of the game.
	 */
	protected transient int _iGameId;

	/**
	 * Id of the player.
	 */
	protected transient int _iPlayerId;

	/**
	 * Configuration of the game.
	 */
	protected transient CONF_TYPE _conf;

	/**
	 * List of player playing in this game.
	 */
	protected transient Set<IPlayerDescription> _playerList;

	/**
	 * Boolean telling whether of not this player is ready.
	 */
	protected transient boolean _bReady;

	/**
	 * {@link EventBus} used to propagates events to all change listener of this
	 * game creator.
	 */
	protected final EventBus _eventBus = new EventBus();

	@Override
	public boolean isCreator()
	{
		synchronized (_lock)
		{
			return _bIsCreator;
		}
	}

	@Override
	public void handleGameCreationEvent(final IGameCreationEvent evt)
			throws InconsistentEventTypeException
	{
		// TODO AbstractClientGameCreator handleGameCreationEvent
	}

	@Override
	public IGameServer getGameServer()
	{
		synchronized (_lock)
		{
			return _gameServer;
		}
	}

	@Override
	public void initialize(final boolean bCreator,
			final LocalGameClient locGameClient, final IGameServer server,
			final int iGameId, final int iPlayerId)
	{
		synchronized (_lock)
		{
			_gameServer = server;
			_bIsCreator = bCreator;
			_iGameId = iGameId;
			_gameClient = locGameClient;
			_iPlayerId = iPlayerId;
		}
	}

	@Override
	public int getGameId()
	{
		synchronized (_lock)
		{
			return _iGameId;
		}
	}

	@Override
	public int getPlayerId()
	{
		synchronized (_lock)
		{
			return _iPlayerId;
		}
	}

	@Override
	public CONF_TYPE getConfiguration()
	{
		synchronized (_lock)
		{
			return _conf;
		}
	}

	@Override
	public void setConfiguration(final CONF_TYPE gameConfiguration)
	{
		synchronized (_lock)
		{
			_conf = gameConfiguration;
			// _eventBus.
		}
	}

	@Override
	public void setClientSidePlayerList(final Set<IPlayerDescription> playerList)
	{
		synchronized (_lock)
		{
			_playerList = playerList;
			// _eventBus.
		}
	}

	@Override
	public Set<IPlayerDescription> getClientSidePlayerList()
	{
		synchronized (_lock)
		{
			return Collections.unmodifiableSet(_playerList);
		}
	}

	@Override
	public void updateReadyStatus(final boolean bReadyStatus)
	{
		synchronized (_lock)
		{
			if (_bReady != bReadyStatus)
			{
				_bReady = bReadyStatus;
				final UpdateStatusCrAction act = new UpdateStatusCrAction(
						_iGameId, _iGameId, _bReady);
				try
				{
					_gameServer.handleAction(_gameClient, act);
				}
				catch (final InconsistentActionTypeException e)
				{
					LOGGER.error(e.getLocalizedMessage(), e);
				}
			}
		}
	}

	@Override
	public void registerGameCreatorChangeListener(
			final IGameCreatorChangeListener o)
	{
		_eventBus.register(o);
	}

}
