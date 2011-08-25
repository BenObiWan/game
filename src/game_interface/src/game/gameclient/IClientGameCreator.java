package game.gameclient;

import game.communication.IGameClient;
import game.communication.IGameServer;
import game.communication.event.IGameCreationEvent;
import game.communication.event.IGameEvent;
import game.communication.event.InconsistentEventTypeException;
import game.config.IGameConfiguration;

/**
 * Object used on the client side to create a game when joined.
 * 
 * @author benobiwan
 * 
 * @param <CLIENT_GAME_TYPE>
 *            the type of game to create.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure the game.
 * @param <EVENT_TYPE>
 *            the type of {@link IGameEvent} handled by the game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IClientSidePlayer} playing this game.
 */
public interface IClientGameCreator<CONF_TYPE extends IGameConfiguration, EVENT_TYPE extends IGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, CONF_TYPE>, PLAYER_TYPE extends IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE>>
{
	/**
	 * Create a game of the specified type and the specified server.
	 * 
	 * @return the created game.
	 */
	CLIENT_GAME_TYPE createGame();

	/**
	 * Creates the player playing at this game.
	 * 
	 * @param iPlayerId
	 *            the id of the player to create.
	 * @return the newly created player.
	 */
	PLAYER_TYPE createPlayer(int iPlayerId);

	/**
	 * Check whether the local client is the creator of this game.
	 * 
	 * @return true if the local client is the creator of this game.
	 */
	boolean isCreator();

	/**
	 * Handle a {@link IGameCreationEvent}.
	 * 
	 * @param evt
	 *            the event to handle.
	 * @throws InconsistentEventTypeException
	 *             the type field of the {@link IGameCreationEvent} and it's
	 *             class are inconsistent.
	 */
	void handleGameCreationEvent(final IGameCreationEvent evt)
			throws InconsistentEventTypeException;

	/**
	 * Get the game server hosting this game.
	 * 
	 * @return the game server hosting this game.
	 */
	IGameServer getGameServer();

	/**
	 * Initialize this game. Should only be called on the client.
	 * 
	 * @param bCreator
	 *            true if the local client is the creator of this game.
	 * @param locGameClient
	 *            local game client.
	 * @param server
	 *            the server hosting this game.
	 * @param iGameId
	 *            the id of the new game.
	 */
	void initialize(boolean bCreator, IGameClient locGameClient,
			IGameServer server, int iGameId);

	/**
	 * Get the game id.
	 * 
	 * @return the game id.
	 */
	int getGameId();

	/**
	 * Set the {@link IGameConfiguration} of this game creator.
	 * 
	 * @param gameConfiguration
	 *            the {@link IGameConfiguration} of this game creator.
	 */
	void setConfiguration(final CONF_TYPE gameConfiguration);

	/**
	 * Get the {@link IGameConfiguration} of this game creator.
	 * 
	 * @return the {@link IGameConfiguration} of this game creator.
	 */
	public CONF_TYPE getConfiguration();
}
