package game.gameserver;

import game.common.IGameDescription;
import game.communication.action.ICommonGameAction;
import game.communication.action.IGameAction;
import game.communication.action.IGameCtrlAction;
import game.communication.action.InconsistentActionTypeException;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

/**
 * An interface representing the game on the server side.
 * 
 * @author benobiwan
 * 
 * @param <ACTION_TYPE>
 *            the type of {@link IGameAction} handled by this game.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure this
 *            game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IServerSidePlayer} used in this game.
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration}.
 */
public interface IServerSideGame<ACTION_TYPE extends IGameAction, CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, PLAYER_CONF extends IPlayerConfiguration, PLAYER_TYPE extends IServerSidePlayer<PLAYER_CONF>>
		extends Comparable<IServerSideGame<?, ?, ?, ?>>
{
	/**
	 * Get the id of this game.
	 * 
	 * @return the id of this game.
	 */
	int getGameId();

	/**
	 * Handle an action.
	 * 
	 * @param player
	 *            the player doing the action.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             if the action type field and the class of the action object
	 *             are inconsistent.
	 */
	void handleGameAction(final IServerSidePlayer<?> player,
			final IGameAction act) throws InconsistentActionTypeException;

	/**
	 * Handle an {@link ICommonGameAction}.
	 * 
	 * @param player
	 *            the player doing the action.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             if the action type field and the class of the action object
	 *             are inconsistent.
	 */
	void handleCommonGameAction(final IServerSidePlayer<?> player,
			final ICommonGameAction act) throws InconsistentActionTypeException;

	/**
	 * Handle an {@link IGameCtrlAction}.
	 * 
	 * @param player
	 *            the player doing the action.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             the type field of the {@link IGameCtrlAction} and it's class
	 *             are inconsistent.
	 */
	void handleGameCtrlAction(final IServerSidePlayer<?> player,
			final IGameCtrlAction act) throws InconsistentActionTypeException;

	/**
	 * Get the description of this game.
	 * 
	 * @return the description of this game.
	 */
	IGameDescription getDescription();

	/**
	 * Check whether this game can be joined or not.
	 * 
	 * @return true if this game can be joined.
	 */
	boolean isJoinable();

	/**
	 * Function called when the timeout on the turn of the current player has
	 * been reached.
	 */
	void timeoutReached();

	/**
	 * Get the configuration of this game.
	 * 
	 * @return the configuration of this game.
	 */
	CONF_TYPE getGameConfiguration();

	/**
	 * Check whether this game contain this player.
	 * 
	 * @param player
	 *            the player to check.
	 * @return true if the specified player is playing this game.
	 */
	boolean isInThisGame(final IServerSidePlayer<?> player);
}
