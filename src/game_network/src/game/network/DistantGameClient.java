package game.network;

import game.communication.IGameClient;
import game.communication.IGameServer;
import game.communication.event.IEvent;
import game.gameserver.IServerSidePlayer;
import game.network.config.INetworkConfiguration;

import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.mina.core.session.IoSession;

/**
 * An object describing a distant client to which the server is connected.
 * 
 * @author benobiwan
 * 
 */
public final class DistantGameClient extends DistantPeer implements IGameClient
{
	/**
	 * The connection id associated with this client. Used only if it is
	 * unregistered, equal to 0 otherwise.
	 */
	private final Long _lConnectionId;

	/**
	 * A boolean indicating whether this client is registered on this game
	 * server or not.
	 */
	private boolean _bRegistered;

	/**
	 * The time at which this client disconnected. If the client is still
	 * connected it is equal to Long.MAX_VALUE.
	 */
	private long _lDisconnectionTime = Long.MAX_VALUE;

	/**
	 * List of server side player on this client.
	 */
	private final ConcurrentSkipListMap<Integer, IServerSidePlayer> _serverSidePlayerList = new ConcurrentSkipListMap<Integer, IServerSidePlayer>();

	/**
	 * Create a new unregistered client.
	 * 
	 * @param ioSession
	 *            the IoSession associated with this client connection.
	 * @param strId
	 *            the id (name) of this client.
	 * @param lConnectionId
	 *            the connection id of this unregistered client.
	 */
	public DistantGameClient(final IoSession ioSession, final String strId,
			final Long lConnectionId)
	{
		super(ioSession, strId);
		_lConnectionId = lConnectionId;
		_bRegistered = false;
	}

	/**
	 * Create a new registered client.
	 * 
	 * @param ioSession
	 *            the IoSession associated with this client connection.
	 * @param strId
	 *            the id (name) of this client.
	 */
	public DistantGameClient(final IoSession ioSession, final String strId)
	{
		super(ioSession, strId);
		_lConnectionId = Long.valueOf(0);
		_bRegistered = true;
	}

	@Override
	public void handleEvent(final IGameServer server, final IEvent evt)
	{
		// TODO send the event to the distant client

	}

	@Override
	public void registerGameServer(final IGameServer server)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(final IGameClient o)
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
		final DistantGameClient other = (DistantGameClient) obj;
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
		_lDisconnectionTime = System.currentTimeMillis();
	}

	/**
	 * Method called when the connection to the client is severed, and there is
	 * no hope of him reconnecting (the timeout value has been reached, the
	 * client has disconnected properly, the client was kicked from this server.
	 */
	public void clientLost()
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Get the time at which this client disconnected. If the client is still
	 * connected it is equal to Long.MAX_VALUE.
	 * 
	 * @return the time at which this client disconnected.
	 */
	public long getDisconnectionTime()
	{
		return _lDisconnectionTime;
	}

	/**
	 * Get the connection id associated with this client. Equal to 0 if the
	 * client is registered.
	 * 
	 * @return the connection id associated with this client.
	 */
	public Long getConnectionId()
	{
		return _lConnectionId;
	}

	/**
	 * Change the registration status of this DistantGameClient. Called when the
	 * client registers on the game server.
	 * 
	 * @param bRegistered
	 *            the new registration status of this DistantGameClient.
	 */
	public void setRegistered(final boolean bRegistered)
	{
		_bRegistered = bRegistered;
	}

	/**
	 * Check whether this client is registered or not.
	 * 
	 * @return true if this client is registered.
	 */
	public boolean isRegistered()
	{
		return _bRegistered;
	}

	/**
	 * Close the IoSession associated with this client and change it to the
	 * specified IoSession.
	 * 
	 * @param newSession
	 *            the new IoSession to use.
	 */
	public void closeAndChangeSession(final IoSession newSession)
	{
		_lDisconnectionTime = Long.MAX_VALUE;
		if (_ioSession != null)
		{
			_ioSession.removeAttribute(INetworkConfiguration.PEER_ATTRIBUTE);
			_ioSession.close(true);
		}
		_ioSession = newSession;
	}

	@Override
	public IServerSidePlayer getServerSidePlayer(final int iPlayerId)
	{
		return _serverSidePlayerList.get(Integer.valueOf(iPlayerId));
	}

	@Override
	public void addServerSidePlayer(final IServerSidePlayer player)
	{
		_serverSidePlayerList.put(Integer.valueOf(player.getId()), player);
	}

	@Override
	public void removeServerSidePlayer(final IServerSidePlayer player)
	{
		_serverSidePlayerList.remove(Integer.valueOf(player.getId()));
	}

	@Override
	public boolean containServerSidePlayer(final IServerSidePlayer player)
	{
		return _serverSidePlayerList
				.containsKey(Integer.valueOf(player.getId()));
	}

	/*
	 * Quand il recoit un ActionMessage, le ServerHandler appele la fonction
	 * handleAction du LocalGameServer en filant le DistantGameClient concerné
	 * en paramètre.
	 */
}
