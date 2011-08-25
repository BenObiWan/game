package game.network;

import game.network.config.INetworkConfiguration;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Abstract IOHandler implementation with code common to both client and server.
 * 
 * @author benobiwan
 * 
 */
public abstract class NetworkHandler extends IoHandlerAdapter
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = Logger.getLogger(NetworkHandler.class);

	/**
	 * Message used to log when the type of a received message is inconsistent
	 * with the object class.
	 */
	protected static final String ERROR_INCONSISTENT_TYPE = "MessageType attribute inconsistent with Message Object class : ";

	/**
	 * The network configuration.
	 */
	protected final INetworkConfiguration _networkConfiguration;

	/**
	 * List of all the network connections of this host.
	 */
	protected final ConnectionList _connectionList;

	/**
	 * Creates a new NetworkHandler.
	 * 
	 * @param networkConfiguration
	 *            the network configuration.
	 * @param connectionList
	 *            list of all the network connections of this host.
	 */
	public NetworkHandler(final INetworkConfiguration networkConfiguration,
			final ConnectionList connectionList)
	{
		_networkConfiguration = networkConfiguration;
		_connectionList = connectionList;
	}

	@Override
	public void exceptionCaught(final IoSession session, final Throwable cause)
	{
		LOGGER.error(cause);
	}

	@Override
	public void sessionClosed(final IoSession session)
	{
		final Object attribute = session
				.getAttribute(INetworkConfiguration.PEER_ATTRIBUTE);
		if (attribute != null && attribute instanceof DistantPeer)
		{
			final DistantPeer peer = (DistantPeer) attribute;
			peer.connectionLost();
		}
		_connectionList.removeIoSession(session);
	}

	@Override
	public void sessionOpened(final IoSession session)
	{
		_connectionList.addIoSession(session);
	}
}
