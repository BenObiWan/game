package game.network.messages;

/**
 * A Message representing a keep alive response send by the
 * ActiveKeepAliveMessageFactory.
 * 
 * @author benobiwan
 * 
 */
public final class KeepAliveResponseMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new KeepAliveResponseMessage.
	 */
	public KeepAliveResponseMessage()
	{
		super(MessageType.KEEP_ALIVE_RESPONSE);
	}
}
