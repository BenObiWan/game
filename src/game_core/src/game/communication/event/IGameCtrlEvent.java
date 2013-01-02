package game.communication.event;

/**
 * Subclass of {@link IUniCastGameEvent} describing events that can happened
 * during the creation and the course of games.
 * 
 * TODO should subclass GameEvent. Some are MultiCast, others are UniCast.
 * 
 * @author benobiwan
 * 
 */
public interface IGameCtrlEvent extends IEvent
{
	/**
	 * Get the type of this IGameCtrlEvent.
	 * 
	 * @return the type of this IGameCtrlEvent.
	 */
	GameCtrlEventType getType();

	/**
	 * Get the id of the game concerned by the event.
	 * 
	 * @return the id of the game concerned by the event.
	 */
	int getGameId();

	/**
	 * Get the id of the player concerned by the event.
	 * 
	 * @return the id of the player concerned by the event.
	 */
	int getPlayerId();
}
