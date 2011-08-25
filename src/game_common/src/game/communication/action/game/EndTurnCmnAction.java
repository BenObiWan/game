package game.communication.action.game;

import game.communication.action.AbstractCommonGameAction;
import game.communication.action.CommonGameActionType;

/**
 * The action of ending once turn.
 * 
 * @author benobiwan
 * 
 */
public final class EndTurnCmnAction extends AbstractCommonGameAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 2243462203936548330L;

	/**
	 * Creates a new EndTurnCmnAction.
	 * 
	 * @param iGameId
	 *            id of the game concerned with the action.
	 * @param iPlayerId
	 *            id of the player doing the action.
	 */
	public EndTurnCmnAction(final int iGameId, final int iPlayerId)
	{
		super(iGameId, iPlayerId, CommonGameActionType.END_TURN);
	}
}
