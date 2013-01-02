package game.communication.event;

/**
 * Implementation of the {@link IUniCastGameEvent} interface describing game
 * events which are sent to only one player.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractUniCastGameEvent extends AbstractGameEvent
		implements IUniCastGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 7917913225979551383L;

	/**
	 * Id of the player concerned by the event.
	 */
	protected final int _iPlayerId;

	/**
	 * Creates a new AbstractUniCastGameEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            id of the player concerned by the event.
	 */
	protected AbstractUniCastGameEvent(final int iGameId, final int iPlayerId)
	{
		super(iGameId);
		_iPlayerId = iPlayerId;
	}

	@Override
	public final int getPlayerId()
	{
		return _iPlayerId;
	}
}
