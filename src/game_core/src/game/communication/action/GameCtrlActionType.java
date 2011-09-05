package game.communication.action;

/**
 * Enum describing the different types of {@link IGameCtrlAction}.
 * 
 * @author benobiwan
 * 
 */
public enum GameCtrlActionType
{
	/**
	 * Join game action.
	 */
	JOIN_GAME,

	/**
	 * Leave game action.
	 */
	LEAVE_GAME,

	/**
	 * Kick player action.
	 */
	KICK_PLAYER,

	/**
	 * Add an artificial intelligence to the game.
	 */
	ADD_AI, ;
}
