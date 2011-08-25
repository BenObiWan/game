package game.gameclient;

import game.communication.IGameClient;
import game.communication.IGameServer;
import game.communication.event.IGameCreationEvent;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.config.IGameConfiguration;

import java.util.Observable;

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
 */
public abstract class AbstractClientGameCreator<CONF_TYPE extends IGameConfiguration, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, CONF_TYPE>, PLAYER_TYPE extends IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE>>
		extends Observable
		implements
		IClientGameCreator<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_TYPE>
{
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
	protected transient IGameClient _gameClient;

	/**
	 * Id of the game.
	 */
	protected transient int _iGameId;

	/**
	 * Configuration of the game.
	 */
	protected transient CONF_TYPE _conf;

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
			final IGameClient locGameClient, final IGameServer server,
			final int iGameId)
	{
		synchronized (_lock)
		{
			_gameServer = server;
			_bIsCreator = bCreator;
			_iGameId = iGameId;
			_gameClient = locGameClient;
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
			setChanged();
			notifyObservers();
		}
	}

}
