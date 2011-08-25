package game.communication.event.control;

import game.communication.event.AbstractControlEvent;
import game.communication.event.ControlEventType;
import game.gameclient.IClientGameCreator;

/**
 * The event of a joined game.
 * 
 * @author benobiwan
 * 
 */
public final class GameJoinedCtrlEvent extends AbstractControlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -6438633723807817360L;

	/**
	 * Object used on the client side to create a game of the appropriate type.
	 */
	private final IClientGameCreator<?, ?, ?, ?> _gameCreator;

	/**
	 * The id of the game the client joined.
	 */
	private final int _iGameId;

	/**
	 * The player id the client gave to the server when it asked to join.
	 */
	private final int _iPlayerId;

	/**
	 * Creates a new GameJoinedCtrlEvent.
	 * 
	 * @param iGameId
	 *            the id of the game the client joined.
	 * @param iPlayerId
	 *            the player id the client gave to the server when it asked to
	 *            join.
	 * @param gameCreator
	 *            the object used on the client side to create a game of the
	 *            appropriate type.
	 */
	public GameJoinedCtrlEvent(final int iGameId, final int iPlayerId,
			final IClientGameCreator<?, ?, ?, ?> gameCreator)
	{
		super(ControlEventType.GAME_JOINED);
		_iGameId = iGameId;
		_gameCreator = gameCreator;
		_iPlayerId = iPlayerId;
	}

	/**
	 * Get the object used on the client side to create a game of the
	 * appropriate type.
	 * 
	 * @return the object used on the client side to create a game of the
	 *         appropriate type.
	 */
	public IClientGameCreator<?, ?, ?, ?> getClientGameCreator()
	{
		return _gameCreator;
	}

	/**
	 * Get the id of the game the client joined.
	 * 
	 * @return the id of the game the client joined.
	 */
	public int getGameId()
	{
		return _iGameId;
	}

	/**
	 * Get the player id the client gave to the server when it asked to join.
	 * 
	 * @return the player id the client gave to the server when it asked to
	 *         join.
	 */
	public int getPlayerId()
	{
		return _iPlayerId;
	}
}
