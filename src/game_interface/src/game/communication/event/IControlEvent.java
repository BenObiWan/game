package game.communication.event;

/**
 * Subclass of {@link IEvent} describing all events not related to an active
 * game.
 * 
 * @author benobiwan
 * 
 */
public interface IControlEvent extends IEvent
{
	/**
	 * Get the type of this control event.
	 * 
	 * @return the type of this control event.
	 */
	ControlEventType getType();
}
