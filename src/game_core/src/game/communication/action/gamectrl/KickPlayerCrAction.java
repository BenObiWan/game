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
	 * The id of the kicked player.
	 */
	private final int _iKickedPlayerId;

	/**
	 * Creates a new KickPlayerCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 * @param iPlayerId
	 *            id of the player doing the action.
	 * @param iKickedPlayerId
	 *            the id of the kicked player.
	 */
	public KickPlayerCrAction(final int iGameId, final int iPlayerId,
			final int iKickedPlayerId)
	{
		super(iGameId, iPlayerId, GameCtrlActionType.KICK_PLAYER);
		_iKickedPlayerId = iKickedPlayerId;
	}

	/**
	 * Get the id of the kicked player.
	 * 
	 * @return the id of the kicked player.
	 */
	public int getKickedPlayerId()
	{
		return _iKickedPlayerId;
	}
}
