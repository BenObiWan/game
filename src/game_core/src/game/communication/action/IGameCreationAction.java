package game.communication.action;

/**
 * Subclass of {@link IAction} describing action related to the creation of
 * games.
 * 
 * @author benobiwan
 * 
 */
public interface IGameCreationAction extends IAction
{
	/**
	 * Get the type of this IGameCreationAction.
	 * 
	 * @return the type of this IGameCreationAction.
	 */
	GameCreationActionType getType();

	/**
	 * Get the id of the game concerned with the action.
	 * 
	 * @return the id of the game concerned with the action.
	 */
	int getGameId();
}
