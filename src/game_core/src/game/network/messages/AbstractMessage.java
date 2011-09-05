package game.network.messages;

import java.io.Serializable;

/**
 * An abstract message.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractMessage implements Serializable
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The type of the Message.
	 */
	protected final MessageType _type;

	/**
	 * Create a new Message.
	 * 
	 * @param type
	 *            the type of the Message.
	 */
	protected AbstractMessage(final MessageType type)
	{
		_type = type;
	}

	/**
	 * Get the type of the message.
	 * 
	 * @return the type of the message.
	 */
	public final MessageType getMessageType()
	{
		return _type;
	}
}
