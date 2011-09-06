package game.network;

import game.common.IGameClient;
import game.network.config.INetworkClientConfigurationElement;
import game.network.config.INetworkConfiguration;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * Object describing a connection attempt to a distant server.
 * 
 * @author benobiwan
 * 
 */
public final class ClientConnection extends AbstractConnection
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ClientConnection.class);

	/**
	 * Message Handler for the client.
	 */
	private final ClientHandler _clientHandler;

	/**
	 * Configuration object used to connect to this server.
	 */
	private final INetworkClientConfigurationElement _networkClientConfiguration;

	/**
	 * the local game client, used to register the server.
	 */
	private final IGameClient _locGameClient;

	/**
	 * Socket for the connection to the distant server.
	 */
	private final NioSocketConnector _connection;

	/**
	 * 
	 * @param networkConfiguration
	 *            the network configuration.
	 * @param connectionList
	 *            list of all the network connections of this host.
	 * @param networkClientConfiguration
	 *            the client configuration holding the information to connect to
	 *            the server.
	 * @param locGameClient
	 *            the local game client.
	 */
	public ClientConnection(
			final INetworkConfiguration networkConfiguration,
			final ConnectionList connectionList,
			final INetworkClientConfigurationElement networkClientConfiguration,
			final IGameClient locGameClient)
	{
		super(networkConfiguration, connectionList);
		_locGameClient = locGameClient;
		_networkClientConfiguration = networkClientConfiguration;
		_clientHandler = new ClientHandler(_networkConfiguration,
				_connectionList, _locGameClient);
		_connection = new NioSocketConnector();
		// Basic configuration
		// _connection.getSessionConfig().setReadBufferSize(
		// _networkConfiguration.getBufferSize());
		// _connection.getSessionConfig().setSendBufferSize(
		// _networkConfiguration.getBufferSize());
		// Add filters
		_connection.getFilterChain().addLast("codec", _protocolCodecFilter);
		_connection.getFilterChain().addLast("keepalive", _keepAliveFilter);
		// Add Handler
		_connection.setHandler(_clientHandler);
	}

	/**
	 * Connect to the distant server.
	 * 
	 * @param lTimeout
	 *            the timeout to use for this connection attempt.
	 * @return the IoSession describing the connection with the server. null if
	 *         the connection wasn't established.
	 * @throws InterruptedException
	 *             if the connection attempt was interrupted.
	 */
	public IoSession connect(@SuppressWarnings("unused") final long lTimeout)
			throws InterruptedException
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("connect");
		}
		final ConnectFuture connect = _connection
				.connect(_networkClientConfiguration.getRemoteAddress());
		connect.await();
		// connect.await(lTimeout)
		// if (!connect.isConnected())
		// {
		// connect.cancel();
		// }
		return connect.getSession();
	}
}
