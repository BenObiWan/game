package game.communication.action.gamecreation;

import game.communication.action.AbstractGameCreationAction;
import game.communication.action.GameCreationActionType;
import game.config.IPlayerConfiguration;

/**
 * The action of sending an {@link IPlayerConfiguration}.
 * 
 * @author benobiwan
 * 
 */
public final class SendPlayerConfigurationGameCrAction extends
		AbstractGameCreationAction
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -31350341747740460L;

	/**
	 * The {@link IPlayerConfiguration} to send with this message.
	 */
	private final IPlayerConfiguration _playerConf;

	/**
	 * Creates a new StartGameCrAction.
	 * 
	 * @param iGameId
	 *            the id of the game concerned by the action.
	 * @param iPlayerId
	 *            id of the player doing the action.
	 * @param playerConf
	 *            the {@link IPlayerConfiguration} to send with this message.
	 */
	public SendPlayerConfigurationGameCrAction(final int iGameId,
			final int iPlayerId, final IPlayerConfiguration playerConf)
	{
		super(iGameId, iPlayerId, GameCreationActionType.SEND_PLAYER_CONF);
		_playerConf = playerConf;
	}

	/**
	 * Get the {@link IPlayerConfiguration} to send with this message.
	 * 
	 * @return the {@link IPlayerConfiguration} to send with this message.
	 */
	public IPlayerConfiguration getPlayerConfiguration()
	{
		return _playerConf;
	}
}
