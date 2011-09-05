package game.network.config;

import common.config.IConfigurationBranch;

/**
 * Configuration for the network server part of the program.
 * 
 * @author benobiwan
 * 
 */
public interface INetworkServerConfiguration extends IConfigurationBranch
{
	/**
	 * Tag of this configuration node.
	 */
	String NETWORK_SERVER_CONFIGURATION_TAG = "server";

	/**
	 * Tag for the listen port.
	 */
	String LISTEN_PORT_TAG = "ListenPort";

	/**
	 * Tag for the maximum number of client.
	 */
	String NUMBER_OF_CLIENT_TAG = "MaxNumberOfClient";

	/**
	 * Tag for the connection timeout.
	 */
	String CLIENT_CONNECTION_TIMEOUT_TAG = "ClientConnectionTimeout";

	/**
	 * Tag for the allowed interval between connections from the same client.
	 */
	String ALLOWED_INTERVAL_TAG = "AllowedInterval";

	/**
	 * Get the server TCP listen port.
	 * 
	 * @return the server TCP listen port.
	 */
	int getListenPort();

	/**
	 * Get the maximum number of clients, 0 is infinite.
	 * 
	 * @return the maximum number of clients.
	 */
	int getNumberOfClient();

	/**
	 * Get the allowed interval between connections from the same client in
	 * milliseconds.
	 * 
	 * @return the allowed interval between connections from the same client in
	 *         milliseconds.
	 */
	long getAllowedInterval();

	/**
	 * Get the time in seconds a client informations are kept after his
	 * connection as been marked as lost.
	 * 
	 * @return the time in seconds a client informations are kept.
	 */
	int getClientConnectionTimeout();
}
