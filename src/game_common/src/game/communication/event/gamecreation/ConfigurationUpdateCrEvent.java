package game.communication.event.gamecreation;

import game.communication.event.AbstractGameCreationEvent;
import game.communication.event.GameCreationEventType;
import game.config.IGameConfiguration;

/**
 * The event describing an update in the configuration of the game in creation.
 * 
 * @author benobiwan
 * 
 */
public final class ConfigurationUpdateCrEvent extends AbstractGameCreationEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883659495207914L;

	/**
	 * The updated {@link IGameConfiguration}.
	 */
	private final IGameConfiguration _gameConfiguration;

	/**
	 * creates a new ConfigurationUpdateCrEvent.
	 * 
	 * @param iGameId
	 *            id of the game concerned by the event.
	 * @param iPlayerId
	 *            the id of the player which created the game.
	 * @param gameConfiguration
	 *            the updated {@link IGameConfiguration}.
	 */
	public ConfigurationUpdateCrEvent(final int iGameId, final int iPlayerId,
			final IGameConfiguration gameConfiguration)
	{
		super(iGameId, iPlayerId, GameCreationEventType.CONFIGURATION_UPDATE);
		_gameConfiguration = gameConfiguration;
	}

	/**
	 * Get the updated {@link IGameConfiguration}.
	 * 
	 * @return the updated {@link IGameConfiguration}.
	 */
	public IGameConfiguration getGameConfiguration()
	{
		return _gameConfiguration;
	}
}
