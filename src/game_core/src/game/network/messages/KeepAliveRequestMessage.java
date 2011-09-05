package game.network.messages;

/**
 * A Message representing a keep alive request send by the
 * ActiveKeepAliveMessageFactory.
 * 
 * @author benobiwan
 * 
 */
public final class KeepAliveRequestMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new KeepAliveRequestMessage.
	 */
	public KeepAliveRequestMessage()
	{
		super(MessageType.KEEP_ALIVE_REQUEST);
	}
}
