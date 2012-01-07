package game.communication.action.gamectrl;

import game.communication.action.AbstractGameCtrlAction;
import game.communication.action.GameCtrlActionType;

/**
 * The action of leaving a game.
 * 
 * @author benobiwan
 * 
 */
public final class LeaveGameCrAction extends AbstractGameCtrlAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3135036353906290460L;

	/**
	 * Creates a new LeaveGameCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 * @param iPlayerId
	 *            the id of the player leaving the game.
	 */
	public LeaveGameCrAction(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, GameCtrlActionType.LEAVE_GAME);
	}
}
