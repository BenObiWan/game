package game.communication.event;

/**
 * Implementation of the {@link AbstractGameCtrlEvent} interface describing all
 * event that can happened during the creation and the course of games.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameCtrlEvent extends AbstractEvent implements
		IGameCtrlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3276431974266073866L;

	/**
	 * Id of the game concerned with the action.
	 */
	protected final int _iGameId;

	/**
	 * Id of the player concerned by the event. The id is given by the client.
	 */
	protected final int _iPlayerId;

	/**
	 * Type of this game creation event.
	 */
	protected final GameCtrlEventType _type;

	/**
	 * Creates a new AbstractGameCtrlEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player concerned by the event.
	 * @param type
	 *            type of this game control event.
	 */
	protected AbstractGameCtrlEvent(final int iGameId, final int iPlayerId,
			final GameCtrlEventType type)
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
	public final GameCtrlEventType getType()
	{
		return _type;
	}

	@Override
	public final int getPlayerId()
	{
		return _iPlayerId;
	}
}
