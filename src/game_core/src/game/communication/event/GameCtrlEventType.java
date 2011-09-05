package game.communication.event;

/**
 * Enum describing the different types of {@link IGameCtrlEvent}.
 * 
 * @author benobiwan
 * 
 */
public enum GameCtrlEventType
{
	/**
	 * Kicked from game event.
	 */
	KICKED_FROM_GAME,

	/**
	 * Game left game event.
	 */
	GAME_LEFT,

	/**
	 * Game destroyed game event.
	 */
	GAME_DESTROYED,

	/**
	 * Game full game event.
	 */
	GAME_FULL,

	/**
	 * Player list update event.
	 */
	PLAYER_LIST_UPDATE, ;
}
