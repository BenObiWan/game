package game.communication.event;

/**
 * Subclass of {@link IGameEvent} describing game events which are sent to only
 * one player.
 * 
 * @author benobiwan
 * 
 */
public interface IUniCastGameEvent extends IGameEvent
{
	/**
	 * Get the id of the player concerned by the event.
	 * 
	 * @return the id of the player concerned by the event.
	 */
	int getPlayerId();
}
