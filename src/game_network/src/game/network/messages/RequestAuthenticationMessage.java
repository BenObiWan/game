package game.network.messages;

import game.network.config.RegistrationType;

/**
 * Message requesting authentication from the client, and telling him what types
 * of registration are available on the server.
 * 
 * @author benobiwan
 * 
 */
public final class RequestAuthenticationMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Type of registration available on the server.
	 */
	private final RegistrationType _registrationType;

	/**
	 * Create a new RequestAuthenticationMessage.
	 * 
	 * @param registrationType
	 *            the type of registration of the server.
	 */
	public RequestAuthenticationMessage(final RegistrationType registrationType)
	{
		super(MessageType.REQUEST_AUTHENTICATION);
		_registrationType = registrationType;
	}

	/**
	 * Get the type of registration of the server.
	 * 
	 * @return the type of registration of the server.
	 */
	public RegistrationType getRegistrationType()
	{
		return _registrationType;
	}
}