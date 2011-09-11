package game.gameserver;

import game.common.IGameClient;
import game.common.IGameInstanceDescription;
import game.common.IGameServer;
import game.communication.action.IGameAction;
import game.communication.action.IGameCreationAction;
import game.communication.action.IGameCtrlAction;
import game.communication.action.InconsistentActionTypeException;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;
import game.gameclient.IClientGameCreator;

/**
 * Object used on the server side to create a game at the request of a client.
 * 
 * @author benobiwan
 * 
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration} of this game.
 * @param <ACTION_TYPE>
 *            the type of {@link IGameAction} handled by the game.
 * @param <SERVER_GAME_TYPE>
 *            the type of the {@link IServerSideGame} to create.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure the game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IServerSidePlayer} used in this game.
 */
public interface IServerGameCreator<PLAYER_CONF extends IPlayerConfiguration, ACTION_TYPE extends IGameAction, CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, PLAYER_TYPE extends IServerSidePlayer<PLAYER_CONF>, SERVER_GAME_TYPE extends IServerSideGame<ACTION_TYPE, CONF_TYPE, PLAYER_CONF, PLAYER_TYPE>>
{
	/**
	 * Create a game of the specified type.
	 * 
	 * @return the created game.
	 */
	SERVER_GAME_TYPE createGame();

	/**
	 * Get the local game server.
	 * 
	 * @return the local game server.
	 */
	IGameServer getGameServer();

	/**
	 * Handle a {@link IGameCreationAction}.
	 * 
	 * @param client
	 *            the client from which the game creation action is coming.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             the type field of the {@link IGameCreationAction} and it's
	 *             class are inconsistent.
	 */
	void handleGameCreationAction(final IGameClient client,
			final IGameCreationAction act)
			throws InconsistentActionTypeException;

	/**
	 * Handle a {@link IGameCtrlAction}.
	 * 
	 * @param client
	 *            the client from which the game control action is coming.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             the type field of the {@link IGameCtrlAction} and it's class
	 *             are inconsistent.
	 */
	void handleGameCtrlAction(final IGameClient client,
			final IGameCtrlAction act) throws InconsistentActionTypeException;

	/**
	 * Get the id of the game which will be created by this
	 * {@link IServerGameCreator}.
	 * 
	 * @return the id of the game to create.
	 */
	int getGameId();

	/**
	 * Get the {@link IClientGameCreator} associated with this
	 * {@link IServerGameCreator}.
	 * 
	 * @return the {@link IClientGameCreator} associated with this
	 *         {@link IServerGameCreator}.
	 */
	IClientGameCreator<?, ?, ?, ?, ?, ?> getClientGameCreator();

	/**
	 * Get the creator of this game.
	 * 
	 * @return the creator of this game.
	 */
	PLAYER_TYPE getCreatorPlayer();

	/**
	 * Get the configuration of this game.
	 * 
	 * @return the configuration of this game.
	 */
	CONF_TYPE getGameConfiguration();

	/**
	 * Check whether the game has reached its maximum number of players.
	 * 
	 * @return true if the game has reached its maximum number of players.
	 */
	boolean isFull();

	/**
	 * Get the description of this game.
	 * 
	 * @return the description of this game.
	 */
	IGameInstanceDescription getDescription();

	/**
	 * Create a {@link IGameInstanceDescription} for this game.
	 * 
	 * @return the newly created {@link IGameInstanceDescription} for this game.
	 */
	IGameInstanceDescription createGameDescription();

	/**
	 * Create a new {@link IServerSidePlayer} playing in this game.
	 * 
	 * @param hostingClient
	 *            the client hosting the new player.
	 * @param iPlayerId
	 *            the id of the player.
	 * @return the newly created {@link IServerSidePlayer}.
	 */
	PLAYER_TYPE createPlayer(IGameClient hostingClient, int iPlayerId);

	/**
	 * Create a new {@link IServerSidePlayer} playing in this game.
	 * 
	 * @param hostingClient
	 *            the client hosting the new player.
	 * @param iPlayerId
	 *            the id of the player.
	 * @param strPlayerName
	 *            the name of the player.
	 * @return the newly created {@link IServerSidePlayer}.
	 */
	PLAYER_TYPE createAI(IGameClient hostingClient, int iPlayerId,
			String strPlayerName);

	/**
	 * Initialize the game. Should only be called on the server.
	 * 
	 * @param gameServer
	 *            the game server on which the game is created.
	 * @param iGameId
	 *            the id of the game which will be created by this
	 *            {@link IServerGameCreator}.
	 * @param creatorClient
	 *            the client who created the game.
	 * @param iCreatorPlayerId
	 *            the id of the player who created the game.
	 */
	void initialize(IGameServer gameServer, int iGameId,
			IGameClient creatorClient, int iCreatorPlayerId);
}