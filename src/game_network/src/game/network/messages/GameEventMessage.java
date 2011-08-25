package game.network.messages;

import game.communication.event.AbstractEvent;

/**
 * A message used by a GameServer to transmit an Event to a GameClient.
 * 
 * @author benobiwan
 * 
 */
public class GameEventMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Event to transmit.
	 */
	private final AbstractEvent _event;

	/**
	 * Create a new GameEventMessage with the specified Event as payload.
	 * 
	 * @param evt
	 *            the Event to send.
	 */
	public GameEventMessage(final AbstractEvent evt)
	{
		super(MessageType.GAME_EVENT);
		_event = evt;
	}

	/**
	 * Get this message's Event object.
	 * 
	 * @return this message's Event object.
	 */
	public AbstractEvent getEvent()
	{
		return _event;
	}
}
