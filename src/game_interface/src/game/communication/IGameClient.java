package game.communication;

import game.communication.event.IEvent;
import game.communication.event.InconsistentEventTypeException;
import game.gameserver.IServerGameCreator;
import game.gameserver.IServerSidePlayer;

/**
 * Interface describing a GameClient. It can be either the LocalClient or a
 * DistantClient.
 * 
 * @author benobiwan
 * 
 */
public interface IGameClient extends Comparable<IGameClient>
{
	/**
	 * Returns the name of this client.
	 * 
	 * @return the name of this client.
	 */
	String getName();

	/**
	 * Asks this GameClient to handle an event.
	 * 
	 * @param server
	 *            the GameServer at the origin of this Event.
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             if the event type field and the class of the event object are
	 *             inconsistent.
	 */
	void handleEvent(IGameServer server, IEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Register a new GameServer to this client. Used to be able to send Action
	 * to the Server.
	 * 
	 * TODO Necessary?
	 * 
	 * @param server
	 *            the GameServer to register.
	 */
	void registerGameServer(IGameServer server);

	/**
	 * Get the {@link IServerSidePlayer} corresponding to the given id.
	 * 
	 * @param iPlayerId
	 *            id of the player.
	 * @return the {@link IServerSidePlayer} corresponding to the given id.
	 */
	IServerSidePlayer<?> getServerSidePlayer(final int iPlayerId);

	/**
	 * Add a player to this client. Should only be called from an
	 * {@link IServerGameCreator};
	 * 
	 * @param player
	 *            the player to add.
	 */
	void addServerSidePlayer(IServerSidePlayer<?> player);

	/**
	 * Remove a player from this client. Should only be called from an
	 * {@link IServerGameCreator};
	 * 
	 * @param player
	 *            the player to remove.
	 */
	void removeServerSidePlayer(IServerSidePlayer<?> player);

	/**
	 * Check whether the client contain the specified player.
	 * 
	 * @param player
	 *            the player to look for.
	 * @return true if the client contain the specified player.
	 */
	boolean containServerSidePlayer(IServerSidePlayer<?> player);
}
