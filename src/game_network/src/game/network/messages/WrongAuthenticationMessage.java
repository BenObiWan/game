package game.network.messages;

/**
 * Message telling the client that he has submitted wrong authentication
 * informations.
 * 
 * @author benobiwan
 * 
 */
public final class WrongAuthenticationMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Error message on the authentication attempt.
	 */
	private final String _strReason;

	/**
	 * Create a new WrongAuthenticationMessage.
	 * 
	 * @param strReason
	 *            the reason for the wrong authentication message.
	 */
	public WrongAuthenticationMessage(final String strReason)
	{
		super(MessageType.WRONG_AUTHENTICATION);
		_strReason = strReason;
	}

	/**
	 * Get the reason for the wrong authentication message.
	 * 
	 * @return the reason for the wrong authentication message.
	 */
	public String getReason()
	{
		return _strReason;
	}
}
