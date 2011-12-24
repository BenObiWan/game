package game.communication.action.gamectrl;

import game.common.IGameClient;
import game.communication.action.IGameCreationAction;
import game.communication.action.IGameCtrlAction;
import game.communication.action.InconsistentActionTypeException;
import game.gameserver.IServerSidePlayer;

/**
 * Handler of {@link IGameCtrlAction}.
 * 
 * @author benobiwan
 */
public interface IGameCtrlActionHandler
{
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
	 * Handle a {@link AddAICrAction}.
	 * 
	 * @param client
	 *            the client from which the add AI action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleAddAIGameCrAction(final IGameClient client,
			final AddAICrAction act);

	/**
	 * Handle a {@link IGameCreationAction}.
	 * 
	 * @param client
	 *            the client from which the game creation action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleJoinGameCrAction(final IGameClient client,
			final JoinGameCrAction act);

	/**
	 * Handle a {@link KickPlayerCrAction}.
	 * 
	 * @param client
	 *            the client from which the kick player action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleKickPlayerGameCrAction(final IGameClient client,
			final KickPlayerCrAction act);

	/**
	 * Handle a {@link LeaveGameCrAction}.
	 * 
	 * @param client
	 *            the client from which the leave game action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleLeaveGameCrAction(final IGameClient client,
			final LeaveGameCrAction act);
}
