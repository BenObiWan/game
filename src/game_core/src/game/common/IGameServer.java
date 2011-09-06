package game.common;

import game.communication.action.IAction;
import game.communication.action.InconsistentActionTypeException;
import game.gameserver.IServerState;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * Interface describing a GameServer.
 * 
 * @author benobiwan
 * 
 */
public interface IGameServer extends Comparable<IGameServer>
{
	/**
	 * Asks this server to handle an Action.
	 * 
	 * @param client
	 *            the GameClient at the origin of this Action.
	 * @param act
	 *            the Action to handle.
	 * @throws InconsistentActionTypeException
	 *             if the action type field and the class of the action object
	 *             are inconsistent.
	 */
	void handleAction(IGameClient client, IAction act)
			throws InconsistentActionTypeException;

	/**
	 * Register a new Client to this GameServer. Used to be able to send Event
	 * to the client.
	 * 
	 * TODO Necessary?
	 * 
	 * @param client
	 *            the GameClient to register.
	 */
	void registerGameClient(IGameClient client);

	/**
	 * Check whether the client is registered on this GameServer or is
	 * anonymous.
	 * 
	 * @return true if the client is registered on this GameServer or is
	 *         anonymous.
	 */
	boolean isRegistered();

	/**
	 * Get the server name of this NetworkClientConfiguration.
	 * 
	 * @return the server name of this NetworkClientConfiguration.
	 */
	String getName();

	/**
	 * Get the network address of the remote server.
	 * 
	 * @return the network address of the remote server.
	 */
	InetSocketAddress getRemoteAddress();

	/**
	 * Get the name used for authentication on this server.
	 * 
	 * @return the name used for authentication on this server.
	 */
	String getClientName();

	/**
	 * Update the server state.
	 * 
	 * @param serverState
	 *            the state of the server.
	 */
	void updateServerState(IServerState serverState);

	/**
	 * Get the state of the server.
	 * 
	 * @return the state of the server.
	 */
	IServerState getServerState();

	/**
	 * Get whether the client is connected with this server or not.
	 * 
	 * @return true if the client is connected with this server or not.
	 */
	boolean isConnected();

	/**
	 * Get whether the client is trying to connect to this server or not.
	 * 
	 * @return true if the client is trying to connect to this server or not.
	 */
	boolean isConnecting();

	/**
	 * Get whether the local client can create a game on the server.
	 * 
	 * @return true if the local client can create a game on the server.
	 */
	boolean isGameCreationAllowed();

	/**
	 * Get the list of game available on this server.
	 * 
	 * @return the list of game available on this server.
	 */
	Set<IGameDescription> getAvailableGames();
}
