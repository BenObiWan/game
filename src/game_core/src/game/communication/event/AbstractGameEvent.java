package game.communication.event;

/**
 * Implementation of the {@link IUniCastGameEvent} interface describing all event
 * taking place during the course of a game.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameEvent extends AbstractEvent implements
		IUniCastGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 7917913225979551383L;

	/**
	 * Id of the game concerned with the event.
	 */
	protected final int _iGameId;

	/**
	 * Id of the player concerned by the event.
	 */
	protected final int _iPlayerId;

	/**
	 * Creates a new GameAction.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            id of the player concerned by the event.
	 */
	protected AbstractGameEvent(final int iGameId, final int iPlayerId)
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
