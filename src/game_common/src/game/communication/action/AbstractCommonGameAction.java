package game.communication.action;

/**
 * Subclass of {@link AbstractGameAction} describing action common to many
 * games.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractCommonGameAction extends AbstractGameAction
		implements ICommonGameAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -5541354598491865972L;

	/**
	 * Type of this common game action.
	 */
	protected final CommonGameActionType _type;

	/**
	 * Creates a new AbstractCommonGameAction.
	 * 
	 * @param iGameId
	 *            id of the game concerned with the action.
	 * @param iPlayerId
	 *            id of the player doing the action.
	 * @param type
	 *            type of this common game action.
	 */
	protected AbstractCommonGameAction(final int iGameId, final int iPlayerId,
			final CommonGameActionType type)
	{
		super(iGameId, iPlayerId);
		_type = type;
	}

	@Override
	public final CommonGameActionType getType()
	{
		return _type;
	}
}
