package game.communication.event;

/**
 * Implementation of the {@link IMultiCastGameEvent} interface describing game
 * events which are sent to all players on the same client.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractMultiCastGameEvent extends AbstractGameEvent
		implements IMultiCastGameEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 7917913225979551383L;

	/**
	 * Creates a new AbstractMultiCastGameEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 */
	protected AbstractMultiCastGameEvent(final int iGameId)
	{
		super(iGameId);
	}
}
