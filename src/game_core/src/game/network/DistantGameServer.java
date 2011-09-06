package game.network;

import game.common.IGameClient;
import game.common.IGameListDescription;
import game.common.IGameServer;
import game.communication.action.IAction;
import game.gameserver.IServerState;
import game.network.config.INetworkClientConfigurationElement;
import game.network.config.INetworkConfiguration;

import java.net.InetSocketAddress;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

/**
 * An object describing a distant server to which the Client is connected.
 * 
 * @author benobiwan
 * 
 */
public final class DistantGameServer extends DistantPeer implements IGameServer
{
	/**
	 * The configuration describing the configuration to the server.
	 */
	private final INetworkClientConfigurationElement _netClientConf;

	/**
	 * The ConnectionId associated with this connection to this server. 0 if the
	 * connection is registered.
	 */
	private long _lConnectionId = 0;

	/**
	 * TODO Javadoc
	 */
	@SuppressWarnings("unused")
	private final boolean _bKeepConnection = true;

	/**
	 * Connection to the server.
	 */
	private ClientConnection _connec;

	/**
	 * Global network configuration.
	 */
	private final INetworkConfiguration _networkConfiguration;

	/**
	 * List of all connections.
	 */
	private final ConnectionList _connectionList;

	/**
	 * Local game client.
	 */
	private final IGameClient _locGameClient;

	/**
	 * Lock for access to the {@link IServerState} object.
	 */
	private final Object _lockState = new Object();

	/**
	 * State of the server.
	 */
	private IServerState _serverState;

	/**
	 * Create a new DistantGameServer.
	 * 
	 * @param networkConfiguration
	 *            the network configuration.
	 * @param connectionList
	 *            the list of all connections.
	 * @param networkClientConfiguration
	 *            the configuration describing the configuration to the server.
	 * @param locGameClient
	 *            the local game client.
	 */
	public DistantGameServer(
			final INetworkConfiguration networkConfiguration,
			final ConnectionList connectionList,
			final INetworkClientConfigurationElement networkClientConfiguration,
			final IGameClient locGameClient)
	{
		super(networkClientConfiguration.getServerName());
		_netClientConf = networkClientConfiguration;
		_networkConfiguration = networkConfiguration;
		_connectionList = connectionList;
		_locGameClient = locGameClient;
	}

	@Override
	public void handleAction(final IGameClient client, final IAction act)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void registerGameClient(final IGameClient client)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public InetSocketAddress getRemoteAddress()
	{
		return _netClientConf.getRemoteAddress();
	}

	@Override
	public String getClientName()
	{
		return _netClientConf.getClientName();
	}

	@Override
	public int compareTo(final IGameServer o)
	{
		return _strName.compareTo(o.getName());
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final DistantGameServer other = (DistantGameServer) obj;
		if (_strName == null)
		{
			if (other._strName != null)
			{
				return false;
			}
		}
		else if (!_strName.equals(other._strName))
		{
			return false;
		}
		return true;
	}

	@Override
	public void connectionLost()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public final String toString()
	{
		return _strName;
	}

	/**
	 * Set the ConnectionId associated with this connection to this server. 0 if
	 * the connection is registered.
	 * 
	 * @param lConnectionId
	 *            the new ConnectionId.
	 */
	public void setConnectionId(final long lConnectionId)
	{
		_lConnectionId = lConnectionId;
	}

	/**
	 * Get the ConnectionId associated with this connection to this server. 0 if
	 * the connection is registered.
	 * 
	 * @return the ConnectionId associated with this connection to this server.
	 */
	public long getConnectionId()
	{
		return _lConnectionId;
	}

	@Override
	public boolean isRegistered()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @throws InterruptedException
	 */
	public void connect() throws InterruptedException
	{
		if (isConnected())
		{
			return;
		}
		else if (_connec != null)
		{
			// TODO
		}
		else
		{
			_connec = new ClientConnection(_networkConfiguration,
					_connectionList, _netClientConf, _locGameClient);
			final IoSession session = _connec.connect(30);
			session.setAttribute(INetworkConfiguration.PEER_ATTRIBUTE, this);
		}
	}

	@Override
	public void updateServerState(final IServerState serverState)
	{
		synchronized (_lockState)
		{
			_serverState = serverState;
		}
	}

	@Override
	public IServerState getServerState()
	{
		synchronized (_lockState)
		{
			return _serverState;
		}
	}

	@Override
	public boolean isGameCreationAllowed()
	{
		// TODO check whether the client has the right to create a game on this
		// server.
		return isConnected();
	}

	@Override
	public Set<IGameListDescription> getAvailableGames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Quand il recoit un EventMessage, le ClientHandler appele la fonction
	 * handleEvent du LocalGameClient en filant le DistantGameServer concerné en
	 * paramètre.
	 */
}
