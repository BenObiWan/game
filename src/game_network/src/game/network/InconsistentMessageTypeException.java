package game.network;

import game.network.messages.AbstractMessage;
import game.network.messages.MessageType;

/**
 * Exception warning that the type field of an {@link AbstractMessage} and it's
 * class are inconsistent.
 * 
 * @author benobiwan
 * 
 */
public final class InconsistentMessageTypeException extends Exception
{
	/**
	 * Message used to log when the type of a received {@link AbstractMessage}
	 * is inconsistent with the object class.
	 */
	private static final String ERROR_INCONSISTENT_TYPE = "MessageType attribute inconsistent with Message Object class : ";

	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3638038547695284997L;

	/**
	 * Type Field of the {@link AbstractMessage}.
	 */
	private final MessageType _type;

	/**
	 * Class of the {@link AbstractMessage}.
	 */
	private final Class<?> _effectiveClass;

	/**
	 * Creates a new InconsistentMessageTypeException.
	 * 
	 * @param type
	 *            the type field of the {@link AbstractMessage}.
	 * @param effectiveClass
	 *            the class of the {@link AbstractMessage}.
	 */
	public InconsistentMessageTypeException(final MessageType type,
			final Class<?> effectiveClass)
	{
		_type = type;
		_effectiveClass = effectiveClass;
	}

	@Override
	public String getMessage()
	{
		return ERROR_INCONSISTENT_TYPE + _type + " " + _effectiveClass
				+ "class.";
	}
}
