package game.communication.action;

/**
 * Subclass of {@link IAction} describing action related an active game.
 * 
 * @author benobiwan
 * 
 */
public interface IGameAction extends IAction
{
	/**
	 * Get the id of the game concerned with the action.
	 * 
	 * @return the id of the game concerned with the action.
	 */
	int getGameId();

	/**
	 * Get the id of the player doing the action.
	 * 
	 * @return the id of the player doing the action.
	 */
	int getPlayerId();
}
