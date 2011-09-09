package game.communication.action.gamecreation;

import game.communication.action.AbstractGameCreationAction;
import game.communication.action.GameCreationActionType;

/**
 * The action of starting a game.
 * 
 * @author benobiwan
 * 
 */
public final class StartGameCrAction extends AbstractGameCreationAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -31350345697290460L;

	/**
	 * Creates a new StartGameCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 */
	public StartGameCrAction(final int iGameId)
	{
		super(iGameId, GameCreationActionType.START_GAME);
	}
}
