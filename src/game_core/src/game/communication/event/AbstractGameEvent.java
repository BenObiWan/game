package game.communication.event;

/**
 * Implementation of the {@link IGameEvent} interface describing all event
 * taking place during the course of a game.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameEvent extends AbstractEvent implements
		IGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -7668718619898109420L;

	/**
	 * Id of the game concerned with the event.
	 */
	protected final int _iGameId;

	/**
	 * Creates a new AbstractGameEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 */
	protected AbstractGameEvent(final int iGameId)
	{
		_iGameId = iGameId;
	}

	@Override
	public final int getGameId()
	{
		return _iGameId;
	}
}