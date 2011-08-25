package game.network.config;

import java.net.InetSocketAddress;

import common.config.IConfigurationListElement;

/**
 * Object holding the configuration information to connect to a distant server.
 * 
 * @author benobiwan
 * 
 */
public interface INetworkClientConfigurationElement extends
		IConfigurationListElement<INetworkClientConfigurationElement>
{
	/**
	 * Tag for the server name.
	 */
	String SERVER_NAME_TAG = "ServerName";

	/**
	 * Tag for the server remote address.
	 */
	String REMOTE_ADDRESS_TAG = "RemoteAddress";

	/**
	 * Tag for the server remote port.
	 */
	String REMOTE_PORT_TAG = "RemotePort";

	/**
	 * Tag for the client name on this server.
	 */
	String CLIENT_NAME_TAG = "ClientName";

	/**
	 * Tag for the client auth on this server.
	 */
	String CLIENT_AUTH_TAG = "ClientAuth";

	/**
	 * Get the server name of this NetworkClientConfiguration.
	 * 
	 * @return the server name of this NetworkClientConfiguration.
	 */
	String getServerName();

	/**
	 * Get the network address of the remote server.
	 * 
	 * @return the network address of the remote server.
	 */
	InetSocketAddress getRemoteAddress();

	/**
	 * Get the name used for authentication on this server.
	 * 
	 * @return the name used for authentication on this server.
	 */
	String getClientName();

	/**
	 * Get the authentication string used on this server.
	 * 
	 * @return the authentication string used on this server.
	 */
	String getClientAuth();
}
