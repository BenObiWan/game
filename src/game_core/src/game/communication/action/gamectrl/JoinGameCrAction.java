package game.communication.action.gamectrl;

import game.communication.action.AbstractGameCtrlAction;
import game.communication.action.GameCtrlActionType;

/**
 * The action of joining a game.
 * 
 * @author benobiwan
 * 
 */
public final class JoinGameCrAction extends AbstractGameCtrlAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3135036353906290460L;

	/**
	 * Creates a new JoinGameCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 * @param iPlayerId
	 *            the id of the player joining the game.
	 */
	public JoinGameCrAction(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, GameCtrlActionType.JOIN_GAME);
	}
}
