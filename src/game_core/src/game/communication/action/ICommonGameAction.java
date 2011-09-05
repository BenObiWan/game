package game.communication.action;

/**
 * * Subclass of {@link IGameAction} describing action related to an active game
 * and common to many games.
 * 
 * @author benobiwan
 * 
 */
public interface ICommonGameAction extends IGameAction
{
	/**
	 * Get the type of this ICommonGameAction.
	 * 
	 * @return the type of this ICommonGameAction.
	 */
	CommonGameActionType getType();
}
