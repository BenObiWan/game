package game.communication.event;

/**
 * Exception warning that the type field of an {@link IEvent} and it's class are
 * inconsistent.
 * 
 * @author benobiwan
 * 
 */
public final class InconsistentEventTypeException extends Exception
{
	/**
	 * Message used to log when the type of a received {@link IEvent} is
	 * inconsistent with the object class.
	 */
	private static final String ERROR_INCONSISTENT_TYPE_1 = "Type attribute inconsistent with IAction Object class : ";

	/**
	 * Message used to log when the class of the received {@link IEvent} is
	 * inconsistent with the awaited class for this {@link IEvent}.
	 */
	private static final String ERROR_INCONSISTENT_TYPE_2 = "IEvent Object class, is iconsistent with the awaited class. Awaited class : ";

	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3638038547695284997L;

	/**
	 * Type Field of the {@link IEvent}.
	 */
	private Enum<?> _type;

	/**
	 * Class of the {@link IEvent}.
	 */
	private final Class<?> _effectiveClass;

	/**
	 * The awaited class of the {@link IEvent}.
	 */
	private Class<?> _awaitedClass;

	/**
	 * Creates a new InconsistentEventTypeException.
	 * 
	 * @param type
	 *            the type field of the {@link IEvent}.
	 * @param effectiveClass
	 *            the class of the {@link IEvent}.
	 */
	public InconsistentEventTypeException(final Enum<?> type,
			final Class<?> effectiveClass)
	{
		_type = type;
		_effectiveClass = effectiveClass;
	}

	/**
	 * Creates a new InconsistentEventTypeException.
	 * 
	 * @param awaitedClass
	 *            the awaited class of the {@link IEvent}.
	 * @param effectiveClass
	 *            the effective class of the {@link IEvent}.
	 */
	public InconsistentEventTypeException(final Class<?> awaitedClass,
			final Class<?> effectiveClass)
	{
		_awaitedClass = awaitedClass;
		_effectiveClass = effectiveClass;
	}

	@Override
	public String getMessage()
	{
		if (_awaitedClass != null)
		{
			return ERROR_INCONSISTENT_TYPE_2 + _awaitedClass
					+ ". Received class : " + _effectiveClass + "class.";
		}
		return ERROR_INCONSISTENT_TYPE_1 + _type + " " + _effectiveClass
				+ "class.";
	}
}
