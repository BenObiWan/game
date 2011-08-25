package game.network.messages;

/**
 * Message telling the client that there was an error during his registration.
 * 
 * @author benobiwan
 * 
 */
public final class RegistrationErrorMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Error message on the registration attempt.
	 */
	private final String _strReason;

	/**
	 * Create a new RegistrationErrorMessage.
	 * 
	 * @param strReason
	 *            the reason for the registration error message.
	 */
	public RegistrationErrorMessage(final String strReason)
	{
		super(MessageType.REGISTRATION_ERROR);
		_strReason = strReason;
	}

	/**
	 * Get the reason for the registration error message.
	 * 
	 * @return the reason for the registration error message.
	 */
	public String getReason()
	{
		return _strReason;
	}
}
