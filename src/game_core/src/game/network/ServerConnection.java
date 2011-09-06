package game.network;

import game.common.IGameServer;
import game.network.config.INetworkConfiguration;
import game.network.config.INetworkServerConfiguration;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.firewall.ConnectionThrottleFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * Object holding the server socket and responsible for creating and binding it.
 * 
 * @author benobiwan
 * 
 */
public final class ServerConnection extends AbstractConnection
{
	/**
	 * Network server configuration.
	 */
	private final INetworkServerConfiguration _networkServerConfiguration;

	/**
	 * The client authenticator, responsible for authenticating clients.
	 */
	private final ClientAuthenticator _authenticator;

	/**
	 * An IoFilter which blocks connections from connecting at a rate faster
	 * than the specified interval.
	 */
	private final ConnectionThrottleFilter _connectionThrottleFilter;

	/**
	 * An IoFilter which limits the maximum number of connection to this server.
	 */
	private MaxConnectionFilter _maxConnectionFilter;

	/**
	 * An IoFilter which handle the authentication of the client. Uses the
	 * ClientAuthenticator.
	 */
	private final AuthenticationFilter _authenticationFilter;

	/**
	 * Message Handler for the server.
	 */
	private final ServerHandler _serverHandler;

	/**
	 * SocketServer listening to the server port.
	 */
	private final NioSocketAcceptor _acceptor;

	/**
	 * Object representing the LocalGameServer, used to register the clients.
	 */
	private final IGameServer _locGameServer;

	/**
	 * Creates a new ServerConnection.
	 * 
	 * @param networkConfiguration
	 *            the network configuration to use.
	 * @param connectionList
	 *            list of all the network connections of this host.
	 * @param networkServerConfiguration
	 *            network server configuration.
	 * @param locGameServer
	 *            the local game server.
	 * @param authenticator
	 *            the client authenticator
	 */
	public ServerConnection(final INetworkConfiguration networkConfiguration,
			final ConnectionList connectionList,
			final INetworkServerConfiguration networkServerConfiguration,
			final IGameServer locGameServer,
			final ClientAuthenticator authenticator)
	{
		super(networkConfiguration, connectionList);
		_networkServerConfiguration = networkServerConfiguration;
		_locGameServer = locGameServer;
		_authenticator = authenticator;

		_serverHandler = new ServerHandler(_networkConfiguration,
				_connectionList, _locGameServer, _networkServerConfiguration);
		// creating filter
		_connectionThrottleFilter = new ConnectionThrottleFilter(
				_networkServerConfiguration.getAllowedInterval());
		_authenticationFilter = new AuthenticationFilter(_authenticator,
				_connectionList);
		_acceptor = new NioSocketAcceptor();
		// Basic configuration
		// _acceptor.getSessionConfig().setReadBufferSize(
		// _networkConfiguration.getBufferSize());
		// _acceptor.getSessionConfig().setSendBufferSize(
		// _networkConfiguration.getBufferSize());
		// Add filters
		if (_networkServerConfiguration.getNumberOfClient() != 0)
		{
			_maxConnectionFilter = new MaxConnectionFilter(
					_networkServerConfiguration.getNumberOfClient());
			_acceptor.getFilterChain().addLast("MaxConnection",
					_maxConnectionFilter);
		}
		_acceptor.getFilterChain().addLast("throttle",
				_connectionThrottleFilter);
		_acceptor.getFilterChain().addLast("codec", _protocolCodecFilter);
		_acceptor.getFilterChain().addLast("keepalive", _keepAliveFilter);
		_acceptor.getFilterChain().addLast("authentication",
				_authenticationFilter);
		// Add Handler
		_acceptor.setHandler(_serverHandler);
	}

	/**
	 * Activate the server part of the network by binding the socket to the
	 * configured port.
	 * 
	 * @throws IOException
	 *             an IO error occurred during the port binding.
	 */
	public void activateServer() throws IOException
	{
		_acceptor.bind(new InetSocketAddress(_networkServerConfiguration
				.getListenPort()));
	}

	/**
	 * Inactivate the server part of the network by unbinding the socket from
	 * the configured port.
	 */
	public void deactivateServer()
	{
		_acceptor.unbind();
	}

	/**
	 * Check whether the server is activated.
	 * 
	 * @return true if the server is activated.
	 */
	public boolean isServerActivated()
	{
		return _acceptor.isActive();
	}
}
