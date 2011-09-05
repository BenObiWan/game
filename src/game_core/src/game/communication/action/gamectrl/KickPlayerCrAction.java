package game.communication.action.gamectrl;

import game.communication.action.AbstractGameCtrlAction;
import game.communication.action.GameCtrlActionType;

/**
 * The action of kicking a player from a game.
 * 
 * @author benobiwan
 * 
 */
public final class KickPlayerCrAction extends AbstractGameCtrlAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3135779842306290460L;

	/**
	 * Creates a new KickPlayerCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 */
	public KickPlayerCrAction(final int iGameId)
	{
		super(iGameId, GameCtrlActionType.KICK_PLAYER);
	}
}
