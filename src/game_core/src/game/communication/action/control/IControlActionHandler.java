package game.communication.action.control;

import game.common.IGameClient;
import game.communication.action.IControlAction;
import game.communication.action.InconsistentActionTypeException;

/**
 * Handler of {@link IControlAction}.
 * 
 * @author benobiwan
 */
public interface IControlActionHandler
{
	/**
	 * Method handling all the control action.
	 * 
	 * @param client
	 *            the client from which the control action is coming.
	 * @param act
	 *            the action to handle.
	 * @throws InconsistentActionTypeException
	 *             the type field of the {@link IControlAction} and it's class
	 *             are inconsistent.
	 */
	void handleControlAction(final IGameClient client, final IControlAction act)
			throws InconsistentActionTypeException;

	/**
	 * Handle a {@link CreateGameCtrlAction}.
	 * 
	 * @param client
	 *            the client from which the control action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleCreateGameCtrlAction(final IGameClient client,
			final CreateGameCtrlAction act);

	/**
	 * Handle a {@link AskServerStateCtrlAction}.
	 * 
	 * @param client
	 *            the client from which the control action is coming.
	 * @param act
	 *            the action to handle.
	 */
	void handleAskServerStateCtrlActionAction(final IGameClient client,
			final AskServerStateCtrlAction act);
}
