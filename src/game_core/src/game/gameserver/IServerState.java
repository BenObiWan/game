package game.gameserver;

/**
 * Interface for the description of the state of a server. Used to be sent to
 * the client and to display on the interface.
 * 
 * @author benobiwan
 * 
 */
public interface IServerState
{
	/**
	 * Get the current number of clients on the server.
	 * 
	 * @return the current number of clients on the server.
	 */
	int getNbClients();

	/**
	 * Get the maximum number of clients on the server.
	 * 
	 * @return the maximum number of clients on the server.
	 */
	int getMaxNbClients();

	/**
	 * Get the current and maximum number of client on this server as a String.
	 * 
	 * @return the current and maximum number of client on this server as a
	 *         String.
	 */
	String getNbCLientsString();

	/**
	 * Get the current number of games on the server.
	 * 
	 * @return the current number of games on the server.
	 */
	int getNbGames();

	/**
	 * Get the maximum number of games on the server.
	 * 
	 * @return the maximum number of games on the server.
	 */
	int getMaxNbGames();

	/**
	 * Get the current and maximum number of client on this server as a String.
	 * 
	 * @return the current and maximum number of client on this server as a
	 *         String.
	 */
	String getNbGamesString();
}
