package game.communication.action.gamecreation;

import game.communication.action.AbstractGameCreationAction;
import game.communication.action.GameCreationActionType;

/**
 * The action of updating one's status.
 * 
 * @author benobiwan
 * 
 */
public final class UpdateStatusCrAction extends AbstractGameCreationAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3135034553906290460L;

	/**
	 * The status of this player : ready to play or not.
	 */
	private final boolean _bReadyStatus;

	/**
	 * Creates a new UpdateStatusCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 * @param iPlayerId
	 *            id of the player doing the action.
	 * @param bReadyStatus
	 *            the new status of the player.
	 */
	public UpdateStatusCrAction(final int iGameId, final int iPlayerId,
			final boolean bReadyStatus)
	{
		super(iGameId, iPlayerId, GameCreationActionType.UPDATE_STATUS);
		_bReadyStatus = bReadyStatus;
	}

	/**
	 * Get the new status of the player.
	 * 
	 * @return true if the player is ready.
	 */
	public boolean isReady()
	{
		return _bReadyStatus;
	}
}
