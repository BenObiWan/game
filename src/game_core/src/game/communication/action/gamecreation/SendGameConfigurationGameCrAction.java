package game.communication.action.gamecreation;

import game.communication.action.AbstractGameCreationAction;
import game.communication.action.GameCreationActionType;
import game.config.IGameConfiguration;

/**
 * The action of starting a game.
 * 
 * @author benobiwan
 * 
 */
public final class SendGameConfigurationGameCrAction extends
		AbstractGameCreationAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -3135148597290460L;

	/**
	 * The {@link IGameConfiguration} to send with this message.
	 */
	private final IGameConfiguration<?> _gameConf;

	/**
	 * Creates a new StartGameCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 * @param gameConf
	 *            the {@link IGameConfiguration} to send with this message.
	 */
	public SendGameConfigurationGameCrAction(final int iGameId,
			final IGameConfiguration<?> gameConf)
	{
		super(iGameId, GameCreationActionType.SEND_GAME_CONF);
		_gameConf = gameConf;
	}

	/**
	 * Get the {@link IGameConfiguration} to send with this message.
	 * 
	 * @return the {@link IGameConfiguration} to send with this message.
	 */
	public IGameConfiguration<?> getGameConfiguration()
	{
		return _gameConf;
	}
}
