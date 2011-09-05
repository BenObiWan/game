package game.communication.event;

/**
 * Subclass of {@link IGameEvent} describing events that can happened during the
 * creation and the course of games.
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
