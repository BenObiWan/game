package game.communication.event;

/**
 * Enum describing the different types of {@link ICommonGameEvent}.
 * 
 * @author benobiwan
 * 
 */
public enum CommonGameEventType
{
	/**
	 * "Your turn" event.
	 */
	YOUR_TURN,

	/**
	 * "Turn timeout" event.
	 */
	TURN_TIMEOUT,

	/**
	 * "Unauthorized action" event.
	 */
	UNAUTHORIZED_ACTION,

	/**
	 * "Can't act" event.
	 */
	CANT_ACT,

	/**
	 * "Unsupported action" event.
	 */
	UNSUPPORTED_ACTION;
}
