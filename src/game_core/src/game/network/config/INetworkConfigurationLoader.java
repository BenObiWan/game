package game.network.config;

import common.config.IConfigurationLoader;

/**
 * {@link IConfigurationLoader} which loads the network configuration.
 * 
 * @author benobiwan
 * 
 */
public interface INetworkConfigurationLoader extends IConfigurationLoader
{
	/**
	 * Prefix for the network configuration.
	 */
	String NETWORK_CONF_PREFIX = "network";

	/**
	 * Get the common network configuration.
	 * 
	 * @return the common network configuration.
	 */
	INetworkConfiguration getNetworkConfiguration();

	/**
	 * Get the network server configuration.
	 * 
	 * @return the network server configuration.
	 */
	INetworkServerConfiguration getNetworkServerConfiguration();

	/**
	 * Get the list of network client configuration.
	 * 
	 * @return the list of network client configuration.
	 */
	INetworkClientConfigurationList getNetworkINetworkClientConfigurationList();
}
