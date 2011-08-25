package game.communication.action;

/**
 * Exception warning that the type field of an {@link IAction} and it's class
 * are inconsistent.
 * 
 * @author benobiwan
 * 
 */
public final class InconsistentActionTypeException extends Exception
{
	/**
	 * Message used to log when the type of a received {@link IAction} is
	 * inconsistent with the object class.
	 */
	private static final String ERROR_INCONSISTENT_TYPE_1 = "Type attribute inconsistent with IAction Object class : ";

	/**
	 * Message used to log when the class of the received {@link IAction} is
	 * inconsistent with the awaited class for this {@link IAction}.
	 */
	private static final String ERROR_INCONSISTENT_TYPE_2 = "IAction Object class, is iconsistent with the awaited class. Awaited class : ";

	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3638038547695284997L;

	/**
	 * Type Field of the {@link IAction}.
	 */
	private Enum<?> _type;

	/**
	 * Class of the {@link IAction}.
	 */
	private final Class<?> _effectiveClass;

	/**
	 * The awaited class of the {@link IAction}.
	 */
	private Class<?> _awaitedClass;

	/**
	 * Creates a new InconsistentActionTypeException.
	 * 
	 * @param type
	 *            the type field of the {@link IAction}.
	 * @param effectiveClass
	 *            the class of the {@link IAction}.
	 */
	public InconsistentActionTypeException(final Enum<?> type,
			final Class<?> effectiveClass)
	{
		_type = type;
		_effectiveClass = effectiveClass;
	}

	/**
	 * Creates a new InconsistentActionTypeException
	 * 
	 * @param awaitedClass
	 *            the awaited class for the {@link IAction}.
	 * @param effectiveClass
	 *            the actual class of the {@link IAction}.
	 */
	public InconsistentActionTypeException(final Class<?> awaitedClass,
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
