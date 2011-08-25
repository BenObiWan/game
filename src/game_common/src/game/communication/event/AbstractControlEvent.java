package game.communication.event;

/**
 * Subclass of {@link AbstractEvent} describing all events not related to an
 * active game.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractControlEvent extends AbstractEvent implements
		IControlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -4801486573257193209L;

	/**
	 * The type of this control event.
	 */
	protected final ControlEventType _type;

	/**
	 * Creates a new ControlEvent.
	 * 
	 * @param type
	 *            type of this control event.
	 */
	protected AbstractControlEvent(final ControlEventType type)
	{
		_type = type;
	}

	@Override
	public final ControlEventType getType()
	{
		return _type;
	}
}
