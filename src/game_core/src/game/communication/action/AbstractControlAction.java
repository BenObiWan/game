package game.communication.action;

/**
 * Subclass of {@link AbstractAction} describing action not related to an active
 * game.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractControlAction extends AbstractAction implements
		IControlAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 611252503039202289L;

	/**
	 * Type of this control action.
	 */
	protected final ControlActionType _actionType;

	/**
	 * Creates a new AbstractControlAction.
	 * 
	 * @param type
	 *            the type of this action.
	 * 
	 */
	protected AbstractControlAction(final ControlActionType type)
	{
		super();
		_actionType = type;
	}

	@Override
	public final ControlActionType getType()
	{
		return _actionType;
	}
}
