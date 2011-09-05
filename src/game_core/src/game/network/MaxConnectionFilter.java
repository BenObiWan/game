package game.network;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * A IoFilter limiting the number of concurrent connection.
 * 
 * @author benobiwan
 * 
 */
public final class MaxConnectionFilter extends IoFilterAdapter
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(MaxConnectionFilter.class);

	/**
	 * The maximum number of connections.
	 */
	private final int _iMaxConnection;

	/**
	 * The current number of connection.
	 */
	private volatile int _iCurrentConnection;

	/**
	 * Message used to log when a connection was refused.
	 */
	private final String _strWarnMessage;

	/**
	 * Create a new MaxConnectionFilter.
	 * 
	 * @param iMaxConnection
	 *            the maximum number of allowed connection.
	 */
	public MaxConnectionFilter(final int iMaxConnection)
	{
		_iMaxConnection = iMaxConnection;
		_iCurrentConnection = 0;
		_strWarnMessage = "Maximum number of connection reached :"
				+ _iMaxConnection + ". Refusing new connection.";
	}

	@Override
	public void sessionCreated(final NextFilter nextFilter,
			final IoSession session)
	{
		if (_iCurrentConnection < _iMaxConnection)
		{
			_iCurrentConnection++;
			nextFilter.sessionCreated(session);
		}
		else
		{
			LOGGER.warn(_strWarnMessage);
			session.close(true);
		}
	}

	@Override
	public void sessionClosed(final NextFilter nextFilter,
			final IoSession session)
	{
		_iCurrentConnection--;
		nextFilter.sessionClosed(session);
	}
}
