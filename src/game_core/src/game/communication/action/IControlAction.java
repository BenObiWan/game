package game.communication.action;

/**
 * * Subclass of {@link IAction} describing action not related to an active
 * game.
 * 
 * @author benobiwan
 * 
 */
public interface IControlAction extends IAction
{
	/**
	 * Get the type of this IControlAction.
	 * 
	 * @return the type of this IControlAction.
	 */
	ControlActionType getType();
}
