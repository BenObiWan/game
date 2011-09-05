package game.network.config;

/**
 * An Exception warning that the authentication informations given by the client
 * are invalid.
 * 
 * @author benobiwan
 * 
 */
public final class WrongAuthenticationException extends Exception
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8057490869799359909L;

	/**
	 * Create a new WrongAuthenticationException.
	 */
	public WrongAuthenticationException()
	{
		// nothing to do
	}

	/**
	 * Create a new WrongAuthenticationException with the specified message.
	 * 
	 * @param message
	 *            the message associated to this WrongAuthenticationException.
	 */
	public WrongAuthenticationException(final String message)
	{
		super(message);
	}

	/**
	 * Create a new WrongAuthenticationException with the specified cause.
	 * 
	 * @param cause
	 *            the cause for this WrongAuthenticationException.
	 */
	public WrongAuthenticationException(final Throwable cause)
	{
		super(cause);
	}

	/**
	 * Create a new WrongAuthenticationException with the specified message and
	 * cause.
	 * 
	 * @param message
	 *            the message associated to this WrongAuthenticationException.
	 * @param cause
	 *            the cause for this WrongAuthenticationException.
	 */
	public WrongAuthenticationException(final String message,
			final Throwable cause)
	{
		super(message, cause);
	}

}
