package game.network.messages;

/**
 * Message informing the client that his authentication was successful. Also
 * convey the ConnectionId if the connection is unregistered.
 * 
 * @author benobiwan
 * 
 */
public final class AuthenticationSuccessfulMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ConnectionId associated with this message. 0 if the session is
	 * registered.
	 */
	private final long _lConnectionId;

	/**
	 * Create a new AuthenticationSuccessfulMessage, without specifying a
	 * ConnectionId meaning that the connection is registered.
	 */
	public AuthenticationSuccessfulMessage()
	{
		super(MessageType.AUTHENTICATION_SUCCESSFUL);
		_lConnectionId = 0;
	}

	/**
	 * Create a new AuthenticationSuccessfulMessage specifying a ConnectionId
	 * meaning that the connection isn't registered.
	 * 
	 * @param lConnectionId
	 *            the ConnectionId associated with this message.
	 */
	public AuthenticationSuccessfulMessage(final long lConnectionId)
	{
		super(MessageType.AUTHENTICATION_SUCCESSFUL);
		_lConnectionId = lConnectionId;
	}

	/**
	 * Get the ConnectionId associated with this message.
	 * 
	 * @return the ConnectionId associated with this message.
	 */
	public long getConnectionId()
	{
		return _lConnectionId;
	}
}
