package game.network.config;

import common.config.IConfigurationList;

/**
 * List of all the {@link INetworkClientConfigurationElement} containing
 * information about all the servers registered on this client.
 * 
 * @author benobiwan
 * 
 */
public interface INetworkClientConfigurationList extends
		IConfigurationList<INetworkClientConfigurationElement>
{
	/**
	 * Tag of this configuration node.
	 */
	String NETWORK_CLIENT_CONFIGURATION_LIST_TAG = "networkClient";

	/**
	 * Tag for the number of connection retry.
	 */
	String NUMBER_OF_CONNNECTION_RETRY_TAG = "NumberOfConnectionRetry";

	/**
	 * Get the number of retry on a connection to a server.
	 * 
	 * @return the number of retry on a connection to a server.
	 */
	int getNumberOfConnectionRetry();
}
