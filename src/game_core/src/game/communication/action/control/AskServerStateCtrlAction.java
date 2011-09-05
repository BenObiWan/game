package game.communication.action.control;

import game.communication.action.AbstractControlAction;
import game.communication.action.ControlActionType;

/**
 * Action asking for the server state.
 * 
 * @author benobiwan
 * 
 */
public final class AskServerStateCtrlAction extends AbstractControlAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 765506248022705036L;

	/**
	 * boolean telling whether we want to list all games or only those which can
	 * be joined.
	 */
	private final boolean _bOnlyJoinableGames;

	/**
	 * Creates a new AskServerStateCtrlAction.
	 * 
	 * @param bOnlyJoinableGames
	 *            boolean telling whether we want to list all games or only
	 *            those which can be joined.
	 */
	public AskServerStateCtrlAction(final boolean bOnlyJoinableGames)
	{
		super(ControlActionType.ASK_SERVER_STATE);
		_bOnlyJoinableGames = bOnlyJoinableGames;
	}

	/**
	 * Creates a new AskServerStateCtrlAction listing only the joinable games.
	 */
	public AskServerStateCtrlAction()
	{
		this(true);
	}

	/**
	 * Check whether we want to list all games or only those which can be
	 * joined.
	 * 
	 * @return true if we want to list only the joinable games.
	 */
	public boolean isOnlyJoinableGames()
	{
		return _bOnlyJoinableGames;
	}

}
