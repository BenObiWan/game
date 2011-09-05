package game.communication.event.gamectrl;

import game.common.IPlayerDescription;
import game.communication.event.AbstractGameCtrlEvent;
import game.communication.event.GameCtrlEventType;

import java.util.SortedSet;

/**
 * The event describing an update in the player list of the game.
 * 
 * @author benobiwan
 * 
 */
public final class PlayerListUpdateCrEvent extends AbstractGameCtrlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883659495207914L;

	/**
	 * List of players.
	 */
	private final SortedSet<IPlayerDescription> _playerList;

	/**
	 * creates a new PlayerListUpdateCrEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player concerned by the event.
	 * @param playerList
	 *            the list of players.
	 */
	public PlayerListUpdateCrEvent(final int iGameId, final int iPlayerId,
			final SortedSet<IPlayerDescription> playerList)
	{
		super(iGameId, iPlayerId, GameCtrlEventType.PLAYER_LIST_UPDATE);
		_playerList = playerList;
	}

	/**
	 * Get the list of players.
	 * 
	 * @return the list of players.
	 */
	public SortedSet<IPlayerDescription> getPlayerList()
	{
		return _playerList;
	}
}
