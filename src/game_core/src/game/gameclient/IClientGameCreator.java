package game.gameclient;

import game.common.IGameServer;
import game.common.IPlayerDescription;
import game.communication.event.IUniCastGameEvent;
import game.communication.event.gamecreation.IGameCreationEventHandler;
import game.communication.event.gamectrl.IGameCtrlEventHandler;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

import java.util.Set;

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
 *            the type of {@link IUniCastGameEvent} handled by the game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IClientSidePlayer} playing this game.
 * @param <PLAYER_CONF>
 *            the type of player {@link IPlayerConfiguration}.
 * @param <CLIENT_CHANGE_LISTENER>
 *            the type of {@link IClientSidePlayerChangeListener} for this
 *            player.
 */
public interface IClientGameCreator<CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, EVENT_TYPE extends IUniCastGameEvent, CLIENT_GAME_TYPE extends IClientSideGame<EVENT_TYPE, PLAYER_CONF, CONF_TYPE>, PLAYER_CONF extends IPlayerConfiguration, PLAYER_TYPE extends IClientSidePlayer<CONF_TYPE, EVENT_TYPE, CLIENT_GAME_TYPE, PLAYER_CONF, CLIENT_CHANGE_LISTENER>, CLIENT_CHANGE_LISTENER extends IClientSidePlayerChangeListener>
		extends IGameCreationEventHandler, IGameCtrlEventHandler
{
	/**
	 * Create a game of the specified type and the specified server.
	 * 
	 * @return the created game.
	 */
	CLIENT_GAME_TYPE createGame();

	/**
	 * Check whether the local client is the creator of this game.
	 * 
	 * @return true if the local client is the creator of this game.
	 */
	boolean isCreator();

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
	 * @param iPlayerId
	 *            the local id of the player joining the game.
	 * @param bIsAI
	 *            true if the local client is an AI.
	 */
	void initialize(boolean bCreator, LocalGameClient locGameClient,
			IGameServer server, int iGameId, int iPlayerId, boolean bIsAI);

	/**
	 * Get the game id.
	 * 
	 * @return the game id.
	 */
	int getGameId();

	/**
	 * Get the if of the player playing this game locally.
	 * 
	 * @return the player id.
	 */
	int getPlayerId();

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

	/**
	 * Creates the local player playing at this game.
	 * 
	 * @param _locGameClient
	 *            the local game client.
	 * @param iPlayerId
	 *            the id of the player to create.
	 * @return the newly created player.
	 */
	PLAYER_TYPE createPlayer(LocalGameClient _locGameClient, int iPlayerId);

	/**
	 * Register a new {@link IGameCreatorChangeListener}.
	 * 
	 * @param o
	 *            the {@link IGameCreatorChangeListener} to register.
	 */
	void registerGameCreatorChangeListener(IGameCreatorChangeListener o);

	/**
	 * Creates a new {@link IPlayerConfiguration} for one of the player of this
	 * game.
	 * 
	 * @return the new {@link IPlayerConfiguration}.
	 */
	PLAYER_CONF createPlayerConfiguration();

	/**
	 * Creates a new {@link IGameConfiguration} for this game.
	 * 
	 * @return the new {@link IGameConfiguration}.
	 */
	CONF_TYPE createGameConfiguration();

	/**
	 * Set the list of player playing this game.
	 * 
	 * @param playerList
	 *            the new player list.
	 */
	void setClientSidePlayerList(Set<IPlayerDescription> playerList);

	/**
	 * Get the list of player playing this game.
	 * 
	 * @return the list of player playing this game.
	 */
	Set<IPlayerDescription> getClientSidePlayerList();

	/**
	 * Update the ready status of this player and sends it to the server.
	 * 
	 * @param bReadyStatus
	 *            the new ready status of this player.
	 */
	void updateReadyStatus(boolean bReadyStatus);

	/**
	 * Try launching the game.
	 * 
	 * @return true if the game has been launched.
	 */
	boolean tryLaunchGame();

	/**
	 * Check whether this game can be launched or not.
	 * 
	 * @return true if this game can be launched.
	 */
	boolean isGameReady();

	/**
	 * Exit the creation of this game. Cancels it if the player is creator.
	 */
	void leaveGameCreation();

	/**
	 * Check whether the player is an AI.
	 * 
	 * @return true if the player is an AI.
	 */
	boolean isAI();

	/**
	 * Add an AI to the game.
	 * 
	 * @param strAIName
	 *            the name of the AI.
	 */
	void addAI(String strAIName);
}
