package game.network;

import game.network.config.INetworkConfiguration;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

/**
 * Abstract object holding the socket used by the host. Hold the code common to
 * the client and the server side of the network connection.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractConnection
{
	/**
	 * The network configuration.
	 */
	protected final INetworkConfiguration _networkConfiguration;

	/**
	 * List of all the network connections of this host.
	 */
	protected final ConnectionList _connectionList;

	/**
	 * An IoFilter responsible for coding and decoding the message addressed to
	 * and by this host.
	 */
	protected final ProtocolCodecFilter _protocolCodecFilter;

	/**
	 * An IoFilter which sends message to keep the connection alive beetween
	 * this host and it's distant peer.
	 */
	protected final KeepAliveFilter _keepAliveFilter;

	/**
	 * Creates a new AbstractConnection.
	 * 
	 * @param networkConfiguration
	 *            the network configuration.
	 * @param connectionList
	 *            list of all the network connections of this host.
	 */
	protected AbstractConnection(
			final INetworkConfiguration networkConfiguration,
			final ConnectionList connectionList)
	{
		_networkConfiguration = networkConfiguration;
		_connectionList = connectionList;
		// creating filter
		_protocolCodecFilter = new ProtocolCodecFilter(
				new ObjectSerializationCodecFactory());
		_keepAliveFilter = new KeepAliveFilter(
				new ActiveKeepAliveMessageFactory(), IdleStatus.READER_IDLE);
		_keepAliveFilter.setRequestInterval(_networkConfiguration
				.getKeepAliveRequestInterval());
		_keepAliveFilter.setRequestTimeout(_networkConfiguration
				.getKeepAliveRequestTimeout());
	}
}
