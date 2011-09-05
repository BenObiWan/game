package game.communication.action;

/**
 * Subclass of {@link AbstractGameAction} describing action related to the
 * creation of a game.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameCreationAction extends AbstractAction
		implements IGameCreationAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -5541357898491865972L;

	/**
	 * Id of the game concerned with the action.
	 */
	protected final int _iGameId;

	/**
	 * Type of this game creation action.
	 */
	protected final GameCreationActionType _type;

	/**
	 * Creates a new AbstractGameCreationAction.
	 * 
	 * @param iGameId
	 *            id of the game concerned with the action.
	 * @param type
	 *            type of this game creation action.
	 */
	protected AbstractGameCreationAction(final int iGameId,
			final GameCreationActionType type)
	{
		super();
		_iGameId = iGameId;
		_type = type;
	}

	@Override
	public final int getGameId()
	{
		return _iGameId;
	}

	@Override
	public final GameCreationActionType getType()
	{
		return _type;
	}
}
