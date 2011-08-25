package game.communication.action;

/**
 * Object describing a action issued by a Client to the GameServer.
 * Implementation of {@link IAction}.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractAction implements IAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 3281246118765107376L;

	/**
	 * Creates a new AbstractAction.
	 */
	protected AbstractAction()
	{
		// nothing to do
	}
}
