package game.communication.event;

/**
 * Enum describing the different types of {@link IControlEvent}.
 * 
 * @author benobiwan
 * 
 */
public enum ControlEventType
{
	/**
	 * Game joined event.
	 */
	GAME_JOINED,

	/**
	 * Game creation stated event.
	 */
	GAME_CREATION_STARTED,

	/**
	 * Server state event.
	 */
	SERVER_STATE;
}
