package game.communication.action;

/**
 * Subclass of {@link AbstractAction} describing all actions taking place during
 * the course of a game.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameAction extends AbstractAction implements
		IGameAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -297422031720528381L;

	/**
	 * Id of the game concerned with the action.
	 */
	protected final int _iGameId;

	/**
	 * Id of the player doing the action.
	 */
	protected final int _iPlayerId;

	/**
	 * Creates a new GameAction.
	 * 
	 * @param iGameId
	 *            id of the game concerned with the action.
	 * @param iPlayerId
	 *            id of the player doing the action.
	 */
	protected AbstractGameAction(final int iGameId, final int iPlayerId)
	{
		super();
		_iGameId = iGameId;
		_iPlayerId = iPlayerId;
	}

	@Override
	public final int getGameId()
	{
		return _iGameId;
	}

	@Override
	public final int getPlayerId()
	{
		return _iPlayerId;
	}
}
