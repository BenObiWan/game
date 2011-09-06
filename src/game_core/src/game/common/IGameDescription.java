package game.common;

/**
 * Description of a game in the list of game available in the application.
 * 
 * @author benobiwan
 * 
 */
public interface IGameDescription extends Comparable<IGameDescription>
{
	/**
	 * Get the name of the game.
	 * 
	 * @return the name of the game.
	 */
	String getName();

	/**
	 * Get the version of the game.
	 * 
	 * @return the version of the game.
	 */
	String getVersion();
}
