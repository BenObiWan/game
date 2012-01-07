package game.communication.action.game;

import game.communication.action.ICommonGameAction;
import game.communication.action.IGameAction;
import game.communication.action.InconsistentActionTypeException;
import game.gameserver.IServerSidePlayer;

/**
 * Handler of {@link IGameAction}.
 * 
 * @author benobiwan
 */
public interface IGameActionHandler
{
	/**
	 * Handle an {@link ICommonGameAction}.
	 * 
	 * @param player
	 *            the player doing the action.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             if the action type field and the class of the action object
	 *             are inconsistent.
	 */
	void handleCommonGameAction(final IServerSidePlayer<?> player,
			final ICommonGameAction act) throws InconsistentActionTypeException;

	/**
	 * Handle a {@link EndTurnCmnAction}.
	 * 
	 * @param player
	 *            the player doing the action.
	 * @param act
	 *            the action to handle.
	 */
	void handleEndTurnCmnAction(final IServerSidePlayer<?> player,
			final EndTurnCmnAction act);
}
