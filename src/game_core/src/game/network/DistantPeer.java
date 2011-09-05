package game.network;

import java.util.Observable;

import org.apache.mina.core.session.IoSession;

/**
 * The representation of a connection to a DistantPeer. May be a Server or a
 * Client.
 * 
 * @author benobiwan
 * 
 */
public abstract class DistantPeer extends Observable
{
	/**
	 * The IoSession connecting to this DistantPeer.
	 */
	protected IoSession _ioSession;

	/**
	 * Name of this DistantPeer.
	 */
	protected final String _strName;

	/**
	 * Creates a new DistantPeer.
	 * 
	 * Used when acting as a server. (the connection is already established)
	 * 
	 * @param ioSession
	 *            the IoSession describing the connection to the client.
	 * @param strName
	 *            the name of the client.
	 */
	protected DistantPeer(final IoSession ioSession, final String strName)
	{
		super();
		_ioSession = ioSession;
		_strName = strName;
	}

	/**
	 * Creates a new DistantPeer.
	 * 
	 * Used when acting as a client. (the connection has to be established)
	 * 
	 * @param strName
	 *            the name of the server.
	 */
	protected DistantPeer(final String strName)
	{
		super();
		_strName = strName;
	}

	/**
	 * Returns the name of this DistantPeer.
	 * 
	 * @return the name of this DistantPeer.
	 */
	public final String getName()
	{
		return _strName;
	}

	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_strName == null) ? 0 : _strName.hashCode());
		return result;
	}

	/**
	 * Check whether this DistantPeer is connected.
	 * 
	 * @return true if this DistantPeer is connected.
	 */
	public final boolean isConnected()
	{
		if (_ioSession == null)
		{
			return false;
		}
		return _ioSession.isConnected();
	}

	/**
	 * Get whether we are trying to connect to this DistantPeer.
	 * 
	 * @return true if we are trying to connect to this DistantPeer.
	 */
	public final boolean isConnecting()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method called when the connection has been lost.
	 */
	public abstract void connectionLost();
}
