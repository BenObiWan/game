package game.network.config;

import common.config.IConfigurationBranch;

/**
 * Network configuration of the program. Hold all configuration common to the
 * client and server part.
 * 
 * @author benobiwan
 * 
 */
public interface INetworkConfiguration extends IConfigurationBranch
{
	/**
	 * Tag of this configuration node.
	 */
	String NETWORK_CONFIGURATION_TAG = "network";

	/**
	 * Tag for the period between KeepAlive requests.
	 */
	String KEEP_ALIVE_REQUEST_INTERVAL_TAG = "KeepAliveRequestInterval";

	/**
	 * Tag for the KeepAlive request timeout before closing the connection.
	 */
	String KEEP_ALIVE_REQUEST_TIMEOUT_TAG = "KeepAliveRequestTimeout";

	/**
	 * Tag for the registration type available to the clients.
	 */
	String REGISTRATION_TYPE_TAG = "RegistrationType";

	/**
	 * Attribute used to attach to the IoSession the information about the
	 * distant peer.
	 */
	String PEER_ATTRIBUTE = "Peer";

	/**
	 * Get the KeepAlive request timeout before closing the connection.
	 * 
	 * @return the KeepAlive request timeout before closing the connection.
	 */
	int getKeepAliveRequestTimeout();

	/**
	 * Get the period between KeepAlive requests.
	 * 
	 * @return the period between KeepAlive requests.
	 */
	int getKeepAliveRequestInterval();

	/**
	 * Get the type of registration available on the server.
	 * 
	 * @return the type of registration available on the server.
	 */
	RegistrationType getRegistrationType();
}
