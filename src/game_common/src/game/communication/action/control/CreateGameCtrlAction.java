package game.communication.action.control;

import game.communication.action.AbstractControlAction;
import game.communication.action.ControlActionType;
import game.gameserver.IServerGameCreator;

/**
 * Action of creating a game on the server.
 * 
 * @author benobiwan
 * 
 */
public final class CreateGameCtrlAction extends AbstractControlAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 6202750647299072767L;

	/**
	 * The object used on the server to create a game of the appropriate type.
	 */
	private final IServerGameCreator<?, ?, ?, ?, ?> _gameCreator;

	/**
	 * Id of the player creating the game. The id is given by the client.
	 */
	private final int _iPlayerId;

	/**
	 * Create a new CreateGameCtrlAction.
	 * 
	 * @param gameCreator
	 *            the object used on the server to create a game.
	 * @param iPlayerId
	 *            the id of the player creating the game.
	 */
	public CreateGameCtrlAction(
			final IServerGameCreator<?, ?, ?, ?, ?> gameCreator,
			final int iPlayerId)
	{
		super(ControlActionType.CREATE_GAME);
		_gameCreator = gameCreator;
		_iPlayerId = iPlayerId;
	}

	/**
	 * Get the object used on the server to create a game of the appropriate
	 * type.
	 * 
	 * @return the object used on the server to create a game of the appropriate
	 *         type.
	 */
	public IServerGameCreator<?, ?, ?, ?, ?> getGameCreator()
	{
		return _gameCreator;
	}

	/**
	 * Get the id of the player creating the game.
	 * 
	 * @return the id of the player creating the game.
	 */
	public int getCreatorPlayerId()
	{
		return _iPlayerId;
	}
}
