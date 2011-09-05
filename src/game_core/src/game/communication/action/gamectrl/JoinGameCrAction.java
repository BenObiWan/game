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
	 * Id of the player joining the game. The id is given by the client.
	 */
	private final int _iPlayerId;

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
		super(iGameId, GameCtrlActionType.JOIN_GAME);
		_iPlayerId = iPlayerId;
	}

	/**
	 * Get the id of the player joining the game.
	 * 
	 * @return the id of the player joining the game.
	 */
	public int getPlayerId()
	{
		return _iPlayerId;
	}
}