package game.communication.action;

/**
 * Enum describing the different types of {@link IGameCreationAction}.
 * 
 * @author benobiwan
 * 
 */
public enum GameCreationActionType
{
	/**
	 * Update status action.
	 */
	UPDATE_STATUS,

	/**
	 * Start game action.
	 */
	START_GAME,

	/**
	 * Send game configuration status.
	 */
	SEND_GAME_CONF,

	/**
	 * Send player configuration status.
	 */
	SEND_PLAYER_CONF;
}
