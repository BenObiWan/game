package game.communication.action.gamecreation;

import game.communication.action.IGameCreationAction;
import game.communication.action.InconsistentActionTypeException;
import game.gameserver.IServerSidePlayer;

/**
 * Handler of {@link IGameCreationAction}.
 * 
 * @author benobiwan
 */
public interface IGameCreationActionHandler
{
	/**
	 * Handle a {@link IGameCreationAction}.
	 * 
	 * @param player
	 *            the player from which the game creation action is coming.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             the type field of the {@link IGameCreationAction} and it's
	 *             class are inconsistent.
	 */
	void handleGameCreationAction(final IServerSidePlayer<?> player,
			final IGameCreationAction act)
			throws InconsistentActionTypeException;

	/**
	 * Handle a {@link SendGameConfigurationGameCrAction}.
	 * 
	 * @param player
	 *            the player from which the action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleSendGameConfigurationGameCrAction(
			final IServerSidePlayer<?> player,
			final SendGameConfigurationGameCrAction act);

	/**
	 * Handle a {@link SendPlayerConfigurationGameCrAction}.
	 * 
	 * @param player
	 *            the player from which the action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleSendPlayerConfigurationGameCrAction(
			final IServerSidePlayer<?> player,
			final SendPlayerConfigurationGameCrAction act);

	/**
	 * Handle a {@link StartGameCrAction}.
	 * 
	 * @param player
	 *            the player which want to start the game.
	 * @param act
	 *            the action to handle.
	 */
	void handleStartGameCrAction(final IServerSidePlayer<?> player,
			final StartGameCrAction act);

	/**
	 * Handle a {@link UpdateStatusCrAction}.
	 * 
	 * @param player
	 *            the player from which the update status action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleUpdateStatusGameCrAction(final IServerSidePlayer<?> player,
			final UpdateStatusCrAction act);
}
