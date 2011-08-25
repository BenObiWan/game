package game.network.messages;

/**
 * Message telling the other part that the last message was unexpected and not
 * handled by this node.
 * 
 * @author benobiwan
 * 
 */
public final class UnexpectedMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new UnexpectedMessage.
	 */
	public UnexpectedMessage()
	{
		super(MessageType.UNEXPECTED_MESSAGE);
	}
}
