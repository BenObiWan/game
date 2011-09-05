package game.communication.action;

/**
 * Subclass of {@link IAction} describing action that can happened during the
 * creation and the course of games.
 * 
 * @author benobiwan
 * 
 */
public interface IGameCtrlAction extends IAction
{
	/**
	 * Get the type of this IGameCtrlAction.
	 * 
	 * @return the type of this IGameCtrlAction.
	 */
	GameCtrlActionType getType();

	/**
	 * Get the id of the game concerned with the action.
	 * 
	 * @return the id of the game concerned with the action.
	 */
	int getGameId();
}
