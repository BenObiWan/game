package game.gameserver;

import game.common.IGameServer;
import game.communication.action.CommonGameActionType;
import game.communication.action.ICommonGameAction;
import game.communication.action.IGameAction;
import game.communication.action.InconsistentActionTypeException;
import game.communication.action.game.EndTurnCmnAction;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * An abstract game representing a game on the server side.
 * 
 * @author benobiwan
 * 
 * @param <ACTION_TYPE>
 *            the type of {@link IGameAction} handled by this game.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure this
 *            game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IServerSidePlayer} used in this game.
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration}.
 */
public abstract class AbstractServerSideGame<ACTION_TYPE extends IGameAction, CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, PLAYER_CONF extends IPlayerConfiguration, PLAYER_TYPE extends IServerSidePlayer<PLAYER_CONF>>
		implements
		IServerSideGame<ACTION_TYPE, CONF_TYPE, PLAYER_CONF, PLAYER_TYPE>
{
	/**
	 * Id of the game.
	 */
	protected final int _iGameId;

	/**
	 * Local game server.
	 */
	protected final IGameServer _locGameServer;

	/**
	 * Object used to ensure that only one action is resolved at a time. Must be
	 * synchronized in children classes in every handle action methods.
	 */
	protected final Object _lockAction = new Object();

	/**
	 * List of players in this game.
	 */
	protected transient final ConcurrentSkipListSet<PLAYER_TYPE> _playerList = new ConcurrentSkipListSet<PLAYER_TYPE>();

	/**
	 * Creates a new AbstractServerSideGame.
	 * 
	 * @param locGameServer
	 *            the local game server.
	 * @param iGameId
	 *            the id of the game.
	 */
	protected AbstractServerSideGame(final IGameServer locGameServer,
			final int iGameId)
	{
		_locGameServer = locGameServer;
		_iGameId = iGameId;
	}

	@Override
	public int compareTo(final IServerSideGame<?, ?, ?, ?> o)
	{
		return getGameId() - o.getGameId();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
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
		final AbstractServerSideGame<?, ?, ?, ?> other = (AbstractServerSideGame<?, ?, ?, ?>) obj;
		if (_iGameId != other._iGameId)
		{
			return false;
		}
		return true;
	}

	@Override
	public final int getGameId()
	{
		return _iGameId;
	}

	@Override
	public void handleCommonGameAction(final IServerSidePlayer<?> player,
			final ICommonGameAction act) throws InconsistentActionTypeException
	{
		switch (act.getType())
		{
		case END_TURN:
			if (act instanceof EndTurnCmnAction)
			{
				handleAction(player, (EndTurnCmnAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						CommonGameActionType.END_TURN, act.getClass());
			}
			break;
		}
	}

	/**
	 * Convenient method to handle an {@link ICommonGameAction} not used in this
	 * particular game.
	 * 
	 * @param player
	 *            the player doing the action.
	 * @param act
	 *            the action to handle.
	 */
	@SuppressWarnings("unused")
	protected void handleUnsupportedCommonAction(
			final IServerSidePlayer<?> player, final ICommonGameAction act)
	{
		// TODO AbstractServerSideGame handleUnsupportedCommonAction
	}

	/**
	 * Handle a {@link EndTurnCmnAction}.
	 * 
	 * @param player
	 *            the player doing the action.
	 * @param act
	 *            the action to handle.
	 */
	public abstract void handleAction(final IServerSidePlayer<?> player,
			final EndTurnCmnAction act);

	@Override
	public boolean isInThisGame(final IServerSidePlayer<?> player)
	{
		return _playerList.contains(player);
	}
}
