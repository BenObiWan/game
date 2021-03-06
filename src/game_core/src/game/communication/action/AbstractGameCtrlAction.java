package game.communication.action;

/**
 * Subclass of {@link AbstractGameAction} describing action that can happened
 * during the creation and the course of games.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameCtrlAction extends AbstractAction implements
		IGameCtrlAction
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
	 * Type of this game control action.
	 */
	protected final GameCtrlActionType _type;

	/**
	 * Id of the player doing the action.
	 */
	protected final int _iPlayerId;

	/**
	 * Creates a new AbstractGameCtrlAction.
	 * 
	 * @param iGameId
	 *            id of the game concerned with the action.
	 * @param iPlayerId
	 *            id of the player doing the action.
	 * @param type
	 *            type of this game control action.
	 */
	protected AbstractGameCtrlAction(final int iGameId, final int iPlayerId,
			final GameCtrlActionType type)
	{
		super();
		_iGameId = iGameId;
		_type = type;
		_iPlayerId = iPlayerId;
	}

	@Override
	public final int getGameId()
	{
		return _iGameId;
	}

	@Override
	public final GameCtrlActionType getType()
	{
		return _type;
	}

	@Override
	public int getPlayerId()
	{
		return _iPlayerId;
	}
}
