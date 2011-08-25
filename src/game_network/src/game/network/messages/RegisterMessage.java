package game.network.messages;

/**
 * A Message send by a GameClient to a GameServer to register an id.
 * 
 * @author benobiwan
 * 
 */
public final class RegisterMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id we want to register.
	 */
	private final String _strId;

	/**
	 * The authentication information.
	 */
	private final String _strAuth;

	/**
	 * Create a new RegisterMessage.
	 * 
	 * @param strId
	 *            the name we want to register on the server.
	 * @param strAuth
	 *            the authentication information.
	 */
	public RegisterMessage(final String strId, final String strAuth)
	{
		super(MessageType.REGISTER);
		_strId = strId;
		_strAuth = strAuth;
	}

	/**
	 * Get the id we want to register.
	 * 
	 * @return the id we want to register.
	 */
	public String getId()
	{
		return _strId;
	}

	/**
	 * Get the authentication information.
	 * 
	 * @return the authentication information.
	 */
	public String getAuth()
	{
		return _strAuth;
	}
}
