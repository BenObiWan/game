package game.communication.event;

/**
 * Implementation of the {@link IGameCreationEvent} interface describing all
 * event related to the creation of a game.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameCreationEvent extends AbstractEvent implements
		IGameCreationEvent
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
	 * Id of the player which created the game. The id is given by the client.
	 */
	protected final int _iPlayerId;

	/**
	 * Type of this game creation event.
	 */
	protected final GameCreationEventType _type;

	/**
	 * Creates a new AbstractGameCreationEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player which created the game.
	 * @param type
	 *            type of this game creation event.
	 */
	protected AbstractGameCreationEvent(final int iGameId, final int iPlayerId,
			final GameCreationEventType type)
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
	public final GameCreationEventType getType()
	{
		return _type;
	}

	@Override
	public final int getPlayerId()
	{
		return _iPlayerId;
	}
}
