package game.communication.action.gamectrl;

import game.communication.action.AbstractGameCtrlAction;
import game.communication.action.GameCtrlActionType;
import game.config.IPlayerConfiguration;

/**
 * The action of adding a AI to the game.
 * 
 * @author benobiwan
 * 
 */
public final class AddAICrAction extends AbstractGameCtrlAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -36353906290460L;

	/**
	 * Id of the AI.
	 */
	private final int _iAIId;

	/**
	 * Name of the AI.
	 */
	private final String _strName;

	/**
	 * The configuration of the AI.
	 */
	private final IPlayerConfiguration _conf;

	/**
	 * Creates a new AddAICrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 * @param iAIId
	 *            the id of the AI joining the game.
	 * @param strName
	 *            the name of the AI.
	 * @param conf
	 *            the configuration of the AI.
	 */
	public AddAICrAction(final int iGameId, final int iAIId,
			final String strName, final IPlayerConfiguration conf)
	{
		super(iGameId, GameCtrlActionType.ADD_AI);
		_iAIId = iAIId;
		_strName = strName;
		_conf = conf;
	}

	/**
	 * Get the id of the AI joining the game.
	 * 
	 * @return the id of the AI joining the game.
	 */
	public int getAIId()
	{
		return _iAIId;
	}

	/**
	 * Get the name of the AI.
	 * 
	 * @return the name of the AI.
	 */
	public String getName()
	{
		return _strName;
	}

	/**
	 * Get the configuration of the AI.
	 * 
	 * @return the configuration of the AI.
	 */
	public IPlayerConfiguration getPlayerConfiguration()
	{
		return _conf;
	}
}
