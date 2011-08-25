package game.network.config;

/**
 * An enum describing the different registration type of a server. Either no
 * authentication, an optional authentication, or a mandatory authentication.
 * 
 * @author benobiwan
 * 
 */
public enum RegistrationType
{
	/**
	 * No registration is available on the server.
	 */
	NONE,

	/**
	 * The registration is optional on the server.
	 */
	OPTIONAL,

	/**
	 * The registration is mandatory on the server.
	 */
	MANDATORY;
}
