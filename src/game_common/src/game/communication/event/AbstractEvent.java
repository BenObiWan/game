package game.communication.event;

/**
 * Object describing an event sent by the GameServer to one of his client.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractEvent implements IEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -9048729109530336271L;

	/**
	 * Creates a new AbstractEvent.
	 */
	protected AbstractEvent()
	{
		// nothing to do
	}
}
