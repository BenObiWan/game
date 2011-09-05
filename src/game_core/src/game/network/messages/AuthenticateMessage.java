package game.network.messages;

/**
 * A Message send by a GameClient to a GameServer to initiate authentication.
 * 
 * @author benobiwan
 * 
 */
public final class AuthenticateMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id used to authenticate.
	 */
	private final String _strId;

	/**
	 * The authentication information.
	 */
	private final String _strAuth;

	/**
	 * The connection Id if the client was connected before and the connection
	 * was lost.
	 */
	private final Long _lConnectionId;

	/**
	 * Create a new AuthenticateMessage authenticating as an anonymous client.
	 * 
	 * @param strId
	 *            the name we want to use on the server.
	 */
	public AuthenticateMessage(final String strId)
	{
		this(strId, null, Long.valueOf(0));
	}

	/**
	 * Create a new AuthenticateMessage authenticating as a registered client.
	 * 
	 * @param strId
	 *            the name we want to use on the server.
	 * @param strAuth
	 *            the authentication information.
	 */
	public AuthenticateMessage(final String strId, final String strAuth)
	{
		this(strId, strAuth, Long.valueOf(0));
	}

	/**
	 * Create a new AuthenticateMessage authenticating as an anonymous client
	 * and trying to resume a previous connection.
	 * 
	 * @param strId
	 *            the name we want to use on the server.
	 * @param lConnectionId
	 *            the id of the connection we want to resume.
	 */
	public AuthenticateMessage(final String strId, final Long lConnectionId)
	{
		this(strId, null, lConnectionId);

	}

	/**
	 * Create a new AuthenticateMessage.
	 * 
	 * @param strId
	 *            the name we want to use on the server.
	 * @param strAuth
	 *            the authentication information.
	 * @param lConnectionId
	 *            the id of the connection we want to resume. 0 if we don't want
	 *            to resume a connection.
	 */
	private AuthenticateMessage(final String strId, final String strAuth,
			final Long lConnectionId)
	{
		super(MessageType.AUTHENTICATE);
		_strId = strId;
		_strAuth = strAuth;
		_lConnectionId = lConnectionId;
	}

	/**
	 * Get the id used.
	 * 
	 * @return the id used.
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

	/**
	 * get the connection Id. 0 if we don't want to resume a session.
	 * 
	 * @return the connection Id.
	 */
	public Long getConnectionId()
	{
		return _lConnectionId;
	}
}
