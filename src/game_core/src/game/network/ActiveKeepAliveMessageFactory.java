package game.network;

import game.network.messages.KeepAliveRequestMessage;
import game.network.messages.KeepAliveResponseMessage;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of the KeepAliveMessageFactory. There is only one
 * occurrence of each message per factory.
 * 
 * @author benobiwan
 * 
 */
public final class ActiveKeepAliveMessageFactory implements
		KeepAliveMessageFactory
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActiveKeepAliveMessageFactory.class);

	/**
	 * The KeepAliveRequestMessage used by this factory.
	 */
	private final KeepAliveRequestMessage _requestMessage;

	/**
	 * The KeepAliveResponseMessage used by this factory.
	 */
	private final KeepAliveResponseMessage _responseMessage;

	/**
	 * Create a new active keep-alive factory.
	 */
	public ActiveKeepAliveMessageFactory()
	{
		_requestMessage = new KeepAliveRequestMessage();
		_responseMessage = new KeepAliveResponseMessage();
	}

	@Override
	public Object getRequest(final IoSession session)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Getting Keepalive request message.");
		}
		return _requestMessage;
	}

	@Override
	public Object getResponse(final IoSession session, final Object request)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Getting Keepalive response message.");
		}
		return _responseMessage;
	}

	@Override
	public boolean isRequest(final IoSession session, final Object message)
	{
		return (message instanceof KeepAliveRequestMessage);
	}

	@Override
	public boolean isResponse(final IoSession session, final Object message)
	{
		return (message instanceof KeepAliveResponseMessage);
	}

}
