package game.core;

import game.common.IGameDescription;
import game.gameclient.ILocalClientUI;
import game.gameclient.LocalGameClient;
import game.gameserver.LocalGameServer;
import game.network.NetworkMain;
import game.network.config.INetworkConfigurationLoader;
import game.network.config.NetworkXMLFileConfigurationLoader;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Set;

/**
 * The application core.
 * 
 * @author benobiwan
 * 
 */
public final class ApplicationCore
{
	/**
	 * Local game server.
	 */
	private final LocalGameServer _locGameServer;

	/**
	 * Local game client.
	 */
	private final LocalGameClient _locGameClient;

	/**
	 * Network main.
	 */
	private final NetworkMain _netMain;

	/**
	 * Creates a new ApplicationCore.
	 * 
	 * @param gameDescriptionSet
	 *            set of game description loaded while launching the
	 *            application.
	 */
	public ApplicationCore(final Set<IGameDescription> gameDescriptionSet)
	{
		_locGameServer = new LocalGameServer(gameDescriptionSet);
		// TODO read name in configuration file
		_locGameClient = new LocalGameClient("loc");
		_locGameServer.registerGameClient(_locGameClient);
		_locGameClient.registerGameServer(_locGameServer);
		final INetworkConfigurationLoader netConfLoader = new NetworkXMLFileConfigurationLoader(
				ManagementFactory.getPlatformMBeanServer());
		_netMain = new NetworkMain(_locGameClient, _locGameServer,
				netConfLoader);
	}

	/**
	 * Activate the network server.
	 * 
	 * @throws IOException
	 *             if an error occurred during the activation of the server.
	 */
	public void activateNetworkServer() throws IOException
	{
		_netMain.activateServer();
	}

	/**
	 * Deactivate the network server.
	 */
	public void deactivateNetworkServer()
	{
		_netMain.deactivateServer();
	}

	/**
	 * Stop the application.
	 */
	public void stop()
	{
		if (_netMain.isServerActivated())
		{
			_netMain.deactivateServer();
		}
		// TODO stop the application.
	}

	/**
	 * Get the network main.
	 * 
	 * @return the network main.
	 */
	public NetworkMain getNetworkMain()
	{
		return _netMain;
	}

	/**
	 * Get the local game server.
	 * 
	 * @return the local game server.
	 */
	public LocalGameServer getLocalGameServer()
	{
		return _locGameServer;
	}

	/**
	 * Get the local game client.
	 * 
	 * @return the local game client.
	 */
	public LocalGameClient getLocalGameClient()
	{
		return _locGameClient;
	}

	public void setClientUI(final ILocalClientUI clientUI)
	{
		_locGameClient.setClientUI(clientUI);
	}
}
