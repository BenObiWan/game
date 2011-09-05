package game.communication.event;

/**
 * Subclass of {@link IEvent} describing all event taking place during the
 * course of a game.
 * 
 * @author benobiwan
 * 
 */
public interface IGameEvent extends IEvent
{
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
