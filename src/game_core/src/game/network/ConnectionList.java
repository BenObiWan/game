package game.network;

import game.common.IGameClient;
import game.common.IGameServer;
import game.network.config.INetworkClientConfigurationElement;
import game.network.config.INetworkClientConfigurationList;
import game.network.config.INetworkConfiguration;
import game.network.config.INetworkServerConfiguration;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An objecting containing the list of all connected IoSession,
 * DistantGameClient and DistantGameServer.
 * 
 * @author benobiwan
 * 
 */
public final class ConnectionList extends Observable
{
	/**
	 * Logger object.
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ConnectionList.class);

	/**
	 * Map containing all session ordered by there id.
	 */
	private final ConcurrentSkipListMap<Long, IoSession> _ioSessionListById = new ConcurrentSkipListMap<Long, IoSession>();

	/**
	 * List of all {@link IoSession} in the order they where added to this list.
	 */
	private final Vector<IoSession> _ioSessionListByIndex = new Vector<IoSession>();

	/**
	 * A map containing all current servers.
	 */
	private final ConcurrentSkipListMap<String, DistantGameServer> _distantServerList = new ConcurrentSkipListMap<String, DistantGameServer>();

	/**
	 * List of all {@link IGameServer} in the order they where added to this
	 * list.
	 */
	private final Vector<IGameServer> _gameServerListByIndex = new Vector<IGameServer>();

	/**
	 * A map containing all current clients.
	 */
	private final ConcurrentSkipListMap<String, DistantGameClient> _connectedClientList = new ConcurrentSkipListMap<String, DistantGameClient>();

	/**
	 * A map containing all current unregistered clients using their connection
	 * id as a key.
	 */
	private final ConcurrentSkipListMap<Long, DistantGameClient> _notRegisteredClientIdList = new ConcurrentSkipListMap<Long, DistantGameClient>();

	/**
	 * Object used to assure the thread safety during client addition and
	 * removal.
	 */
	private final Object _lockAddClient = new Object();

	/**
	 * List of all currently disconnected clients, the informations are kept
	 * during a certain amount of time.
	 */
	protected final ConcurrentSkipListSet<DistantGameClient> _disconnectedClientList = new ConcurrentSkipListSet<DistantGameClient>();

	/**
	 * A TimerTask used to check whether a disconnected client has reached is
	 * timeout, and if it his the case, remove all it's information.
	 */
	private final DisconnectionChecker _disconnectionChecker;

	/**
	 * The timer used to scan the disconnected client list.
	 */
	private final Timer _disconnectionTimer;

	/**
	 * The server configuration.
	 */
	private final INetworkServerConfiguration _networkServerConfiguration;

	/**
	 * List of all network client configuration.
	 */
	private final INetworkClientConfigurationList _networkClientConfigurationList;

	/**
	 * Common network configuration.
	 */
	private final INetworkConfiguration _networkConfiguration;

	/**
	 * Local game client.
	 */
	private final IGameClient _locGameClient;

	/**
	 * Creates a new ConnectionList.
	 * 
	 * @param networkConfiguration
	 *            the common network configuration.
	 * 
	 * @param networkServerConfiguration
	 *            the NetworkServerConfiguration to use.
	 * @param networkClientConfigurationList
	 * @param locGameServer
	 *            the local game server.
	 * @param locGameClient
	 *            the local game client.
	 */
	public ConnectionList(
			final INetworkConfiguration networkConfiguration,
			final INetworkServerConfiguration networkServerConfiguration,
			final INetworkClientConfigurationList networkClientConfigurationList,
			final IGameServer locGameServer, final IGameClient locGameClient)
	{
		_networkServerConfiguration = networkServerConfiguration;
		_networkClientConfigurationList = networkClientConfigurationList;
		_networkConfiguration = networkConfiguration;
		_locGameClient = locGameClient;

		_disconnectionChecker = new DisconnectionChecker(
				_networkServerConfiguration.getClientConnectionTimeout());
		_disconnectionTimer = new Timer("Disconnection Timer", true);
		// makes checks four time more frequent than the allowed timeout
		final int iCheckPeriod = _networkServerConfiguration
				.getClientConnectionTimeout() * 250;
		_disconnectionTimer.scheduleAtFixedRate(_disconnectionChecker,
				iCheckPeriod, iCheckPeriod);
		_gameServerListByIndex.add(locGameServer);
		for (final INetworkClientConfigurationElement confElement : _networkClientConfigurationList
				.getElements())
		{
			final DistantGameServer server = new DistantGameServer(
					_networkConfiguration, this, confElement, locGameClient);
			_gameServerListByIndex.add(server);
			_distantServerList.put(server.getName(), server);
		}
	}

	/**
	 * Get the number GameServer.
	 * 
	 * @return the number GameServer.
	 */
	public int getNumberOfServers()
	{
		return _gameServerListByIndex.size();
	}

	/**
	 * Get the DistantGameServer registered with the specified name.
	 * 
	 * @param strServerName
	 *            the name of the DistantGameServer we want to get.
	 * @return the DistantGameServer registered with the specified name.
	 */
	public DistantGameServer getServer(final String strServerName)
	{
		return _distantServerList.get(strServerName);
	}

	/**
	 * Add a new DistantGameClient to the list.
	 * 
	 * @param client
	 *            the DistantGameClient to add.
	 */
	public void addClient(final DistantGameClient client)
	{
		synchronized (_lockAddClient)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Adding client : " + client.getName());
			}
			_connectedClientList.put(client.getName(), client);
			if (!client.isRegistered())
			{
				_notRegisteredClientIdList
						.put(client.getConnectionId(), client);
			}
		}
	}

	/**
	 * Remove a Client from the Client list.
	 * 
	 * @param client
	 *            the Client to remove.
	 */
	public void removeClient(final DistantGameClient client)
	{
		synchronized (_lockAddClient)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("removing client : " + client.getName());
			}
			_connectedClientList.remove(client.getName());
			_notRegisteredClientIdList.remove(client.getConnectionId());
			_disconnectedClientList.remove(client);
		}
	}

	/**
	 * Get the number clients.
	 * 
	 * @return the number GameClient.
	 */
	public int getNumberOfClients()
	{
		return _connectedClientList.size();
	}

	/**
	 * Get the unregistered DistantGameClient with the specified connection id.
	 * 
	 * @param lConnectionId
	 *            the connection id of the DistantGameClient we are looking for.
	 * @return the unregistered DistantGameClient with the specified connection
	 *         id.
	 */
	public DistantGameClient getUnregisteredClient(final Long lConnectionId)
	{
		return _notRegisteredClientIdList.get(lConnectionId);
	}

	/**
	 * Check whether the client with the specified id is connected.
	 * 
	 * @param strId
	 *            the id of the client we want to check.
	 * 
	 * @return true if the specified client is connected.
	 */
	public boolean isClientConnected(final String strId)
	{
		return _connectedClientList.containsKey(strId);
	}

	/**
	 * Mark a client as disconnected.
	 * 
	 * @param client
	 *            the disconnected client.
	 */
	public void markClientAsDisconnected(final DistantGameClient client)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Client marked as disconnected : " + client.getName());
		}
		_disconnectedClientList.add(client);
	}

	/**
	 * Mark a client as reconnected.
	 * 
	 * @param client
	 *            the reconnected client.
	 */
	public void unmarkClientAsDisconnected(final DistantGameClient client)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Client marked as reconnected : " + client.getName());
		}
		_disconnectedClientList.remove(client);
	}

	/**
	 * Get the number of disconnected clients.
	 * 
	 * @return the number of disconnected clients.
	 */
	public int getNumberOfDisconnectedClient()
	{
		return _disconnectedClientList.size();
	}

	/**
	 * Add an IOSession to the list.
	 * 
	 * @param session
	 *            the session to add.
	 */
	public void addIoSession(final IoSession session)
	{
		synchronized (_ioSessionListByIndex)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Adding IoSession id : " + session.getId());
			}
			if (_ioSessionListById.containsKey(Long.valueOf(session.getId())))
			{
				LOGGER.error("Session " + session.getId() + " duplicated???");
			}
			else
			{
				_ioSessionListById.put(Long.valueOf(session.getId()), session);
				_ioSessionListByIndex.add(session);
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Remove an IOSession from the list.
	 * 
	 * @param session
	 *            the session to remove.
	 */
	public void removeIoSession(final IoSession session)
	{
		synchronized (_ioSessionListByIndex)
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Removing IoSession id : " + session.getId());
			}
			_ioSessionListById.remove(Long.valueOf(session.getId()));
			_ioSessionListByIndex.remove(session);
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Get the number of IoSession.
	 * 
	 * @return the number of IoSession.
	 */
	public int getNumberOfSessions()
	{
		return _ioSessionListByIndex.size();
	}

	/**
	 * Get the IoSession at the specified index.
	 * 
	 * @param iIndex
	 *            the index of the desired IoSession.
	 * @return the IoSession at the specified index.
	 */
	public IoSession getIoSessionAtIndex(final int iIndex)
	{
		return _ioSessionListByIndex.get(iIndex);
	}

	/**
	 * Get the {@link IGameServer} at the specified index in the list.
	 * 
	 * @param iIndex
	 *            the index of the {@link IGameServer}.
	 * @return the selected {@link IGameServer}.
	 */
	public IGameServer getServerAtIndex(final int iIndex)
	{
		return _gameServerListByIndex.get(iIndex);
	}

	/**
	 * An internal class used to check whether a disconnected client has reached
	 * is timeout, and if it his the case, remove all it's information.
	 * 
	 * @author benobiwan
	 * 
	 */
	private final class DisconnectionChecker extends TimerTask
	{
		/**
		 * The time in milliseconds a client informations are kept after his
		 * connection as been marked as lost. (transformed in milliseconds in
		 * the constructor)
		 */
		private final long _lClientMaxConnectionTimeout;

		/**
		 * Create a new DiconnectionChecker.
		 * 
		 * @param lClientMaxConnectionTimeout
		 *            the time in seconds a client informations are kept after
		 *            his connection as been marked as lost. (transformed in
		 *            milliseconds in the constructor)
		 */
		public DisconnectionChecker(final long lClientMaxConnectionTimeout)
		{
			super();
			_lClientMaxConnectionTimeout = lClientMaxConnectionTimeout * 1000;
		}

		@Override
		public void run()
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("DiconnectionChecker launched.");
			}
			final long lAllowedDisconnectionTime = System.currentTimeMillis()
					- _lClientMaxConnectionTimeout;
			for (final DistantGameClient client : _disconnectedClientList)
			{
				if (client.isConnected())
				{
					if (LOGGER.isDebugEnabled())
					{
						LOGGER.debug(client.getName()
								+ " removed from disconnected list.");
					}
					_disconnectedClientList.remove(client);
				}
				else
				{
					if (client.getDisconnectionTime() < lAllowedDisconnectionTime)
					{
						if (LOGGER.isDebugEnabled())
						{
							LOGGER.debug(client.getName()
									+ " has reached is timeout, removing.");
						}
						client.clientLost();
						removeClient(client);
					}
				}
			}

		}
	}
}
