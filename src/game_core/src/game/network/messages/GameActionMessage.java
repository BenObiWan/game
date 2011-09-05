package game.network.messages;

import game.communication.action.AbstractAction;

/**
 * A message used by a GameClient to transmit an Action to a GameServer.
 * 
 * @author benobiwan
 * 
 */
public class GameActionMessage extends AbstractMessage
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Action to transmit.
	 */
	private final AbstractAction _action;

	/**
	 * Create a new GameActionMessage with the specified Action as payload.
	 * 
	 * @param act
	 *            the Action to send.
	 */
	public GameActionMessage(final AbstractAction act)
	{
		super(MessageType.GAME_ACTION);
		_action = act;
	}

	/**
	 * Get this message's Action object.
	 * 
	 * @return this message's Action object.
	 */
	public AbstractAction getAction()
	{
		return _action;
	}
}
