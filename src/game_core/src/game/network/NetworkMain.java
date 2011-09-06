package game.network;

import game.common.IGameClient;
import game.common.IGameServer;
import game.network.config.INetworkClientConfigurationList;
import game.network.config.INetworkConfiguration;
import game.network.config.INetworkConfigurationLoader;
import game.network.config.INetworkServerConfiguration;

import java.io.IOException;

/**
 * Main networking class.
 * 
 * @author benobiwan
 * 
 */
public final class NetworkMain
{
	/**
	 * Common network configuration.
	 */
	private final INetworkConfiguration _networkConfiguration;

	/**
	 * Network server configuration.
	 */
	private final INetworkServerConfiguration _networkServerConfiguration;

	/**
	 * List of all network client configuration.
	 */
	private final INetworkClientConfigurationList _networkClientConfigurationList;

	/**
	 * Object used to authenticate clients.
	 */
	private final ClientAuthenticator _authenticator;

	/**
	 * List of all connections.
	 */
	private final ConnectionList _connectionList;

	/**
	 * Local game server.
	 */
	private final IGameServer _locGameServer;

	/**
	 * Local game client.
	 */
	private final IGameClient _locGameClient;

	/**
	 * Object holding the server socket.
	 */
	private final ServerConnection _serverConnection;

	/**
	 * Create a new NetworkMain.
	 * 
	 * @param locGameClient
	 *            the local GameClient.
	 * @param locGameServer
	 *            the local GameServer.
	 * @param networkConfigurationLoader
	 *            the network configuration loader.
	 */
	public NetworkMain(final IGameClient locGameClient,
			final IGameServer locGameServer,
			final INetworkConfigurationLoader networkConfigurationLoader)
	{
		_networkServerConfiguration = networkConfigurationLoader
				.getNetworkServerConfiguration();
		_networkConfiguration = networkConfigurationLoader
				.getNetworkConfiguration();
		_networkClientConfigurationList = networkConfigurationLoader
				.getNetworkINetworkClientConfigurationList();
		_locGameClient = locGameClient;
		_locGameServer = locGameServer;

		_connectionList = new ConnectionList(_networkConfiguration,
				_networkServerConfiguration, _networkClientConfigurationList,
				locGameServer, locGameClient);

		_authenticator = new ClientAuthenticator(
				_networkConfiguration.getRegistrationType(), _connectionList);

		_serverConnection = new ServerConnection(_networkConfiguration,
				_connectionList, _networkServerConfiguration, _locGameServer,
				_authenticator);
	}

	// public void connectToServer(final INetworkClientConfigurationElement
	// newConf)
	// throws InterruptedException
	// {
	// final String strServerName = newConf.getServerName();
	// DistantGameServer dist = _connectionList.getServer(strServerName);
	// if (dist != null)
	// {
	// // TODO changement de conf?
	// dist.connect();
	// }
	// else
	// {
	// dist = new DistantGameServer(_networkConfiguration,
	// _connectionList, newConf, _locGameClient);
	// _connectionList.addServer(dist);
	// dist.connect();
	// }
	// }

	/**
	 * Activate the network part of the GameServer. Configure it if it hasn't
	 * already been configured.
	 * 
	 * @throws IOException
	 *             an error occurred during the opening of the serverSocket.
	 */
	public void activateServer() throws IOException
	{
		_serverConnection.activateServer();
	}

	/**
	 * Deactivate the network part of the GameServer.
	 */
	public void deactivateServer()
	{
		_serverConnection.deactivateServer();
	}

	/**
	 * Check whether the network part of the GameServer has been activated.
	 * 
	 * @return true if the network part of the GameServer has been activated.
	 */
	public boolean isServerActivated()
	{
		return _serverConnection.isServerActivated();
	}

	/**
	 * Get the list of all connections.
	 * 
	 * @return the list of all connections.
	 */
	public ConnectionList getConnectionList()
	{
		return _connectionList;
	}
}
