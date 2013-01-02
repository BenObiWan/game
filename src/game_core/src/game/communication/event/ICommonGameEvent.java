package game.communication.event;

/**
 * Subclass of {@link IUniCastGameEvent} describing events related to an active game
 * and common to many games.
 * 
 * @author benobiwan
 * 
 */
public interface ICommonGameEvent extends IUniCastGameEvent
{
	/**
	 * Get the type of this ICommonGameEvent.
	 * 
	 * @return the type of this ICommonGameEvent.
	 */
	CommonGameEventType getType();
}
