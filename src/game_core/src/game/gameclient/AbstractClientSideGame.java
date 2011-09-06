package game.gameclient;

import game.common.IGameClient;
import game.common.IGameServer;
import game.communication.event.CommonGameEventType;
import game.communication.event.ICommonGameEvent;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.communication.event.game.CantActCmnEvent;
import game.communication.event.game.TurnTimeoutCmnEvent;
import game.communication.event.game.UnauthorizedActionCmnEvent;
import game.communication.event.game.UnsupportedActionCmnEvent;
import game.communication.event.game.YourTurnCmnEvent;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

/**
 * Abstract implementation of the {@link IClientSideGame} interface.
 * 
 * @author benobiwan
 * 
 * @param <EVENT_TYPE>
 *            the type of {@link IGameEvent} handled by this game.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure this
 *            game.
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration}.
 */
public abstract class AbstractClientSideGame<EVENT_TYPE extends IGameEvent, PLAYER_CONF extends IPlayerConfiguration, CONF_TYPE extends IGameConfiguration<PLAYER_CONF>>
		implements IClientSideGame<EVENT_TYPE, PLAYER_CONF, CONF_TYPE>
{
	/**
	 * The local game client.
	 */
	protected final IGameClient _locGameClient;

	/**
	 * The game server hosting this game.
	 */
	protected final IGameServer _gameServer;

	/**
	 * Id of the game.
	 */
	protected final int _iGameId;

	/**
	 * Creates a new AbstractClientSideGame.
	 * 
	 * @param locGameClient
	 *            The local game client.
	 * @param distantGameServer
	 *            the game server hosting this game.
	 * @param iGameId
	 *            id of the game.
	 */
	protected AbstractClientSideGame(final IGameClient locGameClient,
			final IGameServer distantGameServer, final int iGameId)
	{
		_locGameClient = locGameClient;
		_gameServer = distantGameServer;
		_iGameId = iGameId;
	}

	@Override
	public int compareTo(final IClientSideGame<?, ?, ?> o)
	{
		int iComp = getGameServer().compareTo(o.getGameServer());
		if (iComp == 0)
		{
			iComp = getGameId() - o.getGameId();
		}
		return iComp;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_gameServer == null) ? 0 : _gameServer.hashCode());
		result = prime * result + _iGameId;
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
		final AbstractClientSideGame<?, ?, ?> other = (AbstractClientSideGame<?, ?, ?>) obj;
		if (_gameServer == null)
		{
			if (other._gameServer != null)
			{
				return false;
			}
		}
		else if (!_gameServer.equals(other._gameServer))
		{
			return false;
		}
		if (_iGameId != other._iGameId)
		{
			return false;
		}
		return true;
	}

	@Override
	public int getGameId()
	{
		return _iGameId;
	}

	@Override
	public IGameServer getGameServer()
	{
		return _gameServer;
	}

	@Override
	public void handleEvent(final ICommonGameEvent evt)
			throws InconsistentEventTypeException
	{
		switch (evt.getType())
		{
		case YOUR_TURN:
			if (evt instanceof YourTurnCmnEvent)
			{
				handleEvent((YourTurnCmnEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						CommonGameEventType.YOUR_TURN, evt.getClass());
			}
			break;
		case TURN_TIMEOUT:
			if (evt instanceof TurnTimeoutCmnEvent)
			{
				handleEvent((TurnTimeoutCmnEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						CommonGameEventType.TURN_TIMEOUT, evt.getClass());
			}
			break;
		case UNAUTHORIZED_ACTION:
			if (evt instanceof UnauthorizedActionCmnEvent)
			{
				handleEvent((UnauthorizedActionCmnEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						CommonGameEventType.UNAUTHORIZED_ACTION, evt.getClass());
			}
			break;
		case CANT_ACT:
			if (evt instanceof CantActCmnEvent)
			{
				handleEvent((CantActCmnEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						CommonGameEventType.CANT_ACT, evt.getClass());
			}
			break;
		case UNSUPPORTED_ACTION:
			if (evt instanceof UnsupportedActionCmnEvent)
			{
				handleEvent((UnsupportedActionCmnEvent) evt);
			}
			else
			{
				throw new InconsistentEventTypeException(
						CommonGameEventType.UNSUPPORTED_ACTION, evt.getClass());
			}
			break;
		}
	}

	/**
	 * Convenient method to handle an {@link ICommonGameEvent} not used in this
	 * particular game.
	 * 
	 * @param evt
	 *            the action to handle.
	 */
	@SuppressWarnings("unused")
	protected void handleUnsupportedCommonEvent(final ICommonGameEvent evt)
	{
		// TODO AbstractClientSideGame handleUnsupportedCommonEvent
	}

	/**
	 * Handle a {@link YourTurnCmnEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	public abstract void handleEvent(final YourTurnCmnEvent evt);

	/**
	 * Handle a {@link TurnTimeoutCmnEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	public abstract void handleEvent(final TurnTimeoutCmnEvent evt);

	/**
	 * Handle a {@link UnauthorizedActionCmnEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	public abstract void handleEvent(final UnauthorizedActionCmnEvent evt);

	/**
	 * Handle a {@link CantActCmnEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	public abstract void handleEvent(final CantActCmnEvent evt);

	/**
	 * Handle a {@link UnsupportedActionCmnEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 */
	public abstract void handleEvent(final UnsupportedActionCmnEvent evt);
}
