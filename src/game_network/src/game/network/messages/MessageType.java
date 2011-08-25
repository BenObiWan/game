package game.network.messages;

/**
 * An enum representing the different types of message.
 * 
 * @author benobiwan
 * 
 */
public enum MessageType
{
	// messages for the KeepAliveFilter
	/**
	 * Keep alive request message.
	 */
	KEEP_ALIVE_REQUEST,

	/**
	 * Keep alive response message.
	 */
	KEEP_ALIVE_RESPONSE,

	// messages for the AuthenticationFilter
	/**
	 * Authentication message.
	 */
	AUTHENTICATE,

	/**
	 * Request authentication message.
	 */
	REQUEST_AUTHENTICATION,

	/**
	 * Wrong authentication message.
	 */
	WRONG_AUTHENTICATION,

	/**
	 * Registration message.
	 */
	REGISTER,

	/**
	 * Registration error message.
	 */
	REGISTRATION_ERROR,

	/**
	 * Authentication successful message.
	 */
	AUTHENTICATION_SUCCESSFUL,

	// Other messages
	/**
	 * Unexpected message.
	 */
	UNEXPECTED_MESSAGE,

	/**
	 * Game event message.
	 */
	GAME_EVENT,

	/**
	 * Game action message.
	 */
	GAME_ACTION;
}
