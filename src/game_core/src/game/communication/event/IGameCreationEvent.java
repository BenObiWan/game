package game.communication.event;

/**
 * Subclass of {@link IEvent} describing events related to the creation of
 * games.
 * 
 * TODO should subclass IMultiCastGameEvent.
 * 
 * @author benobiwan
 * 
 */
public interface IGameCreationEvent extends IEvent
{
	/**
	 * Get the type of this IGameCreationEvent.
	 * 
	 * @return the type of this IGameCreationEvent.
	 */
	GameCreationEventType getType();

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
