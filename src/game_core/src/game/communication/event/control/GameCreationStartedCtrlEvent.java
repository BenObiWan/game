package game.communication.event.control;

import game.communication.event.AbstractControlEvent;
import game.communication.event.ControlEventType;
import game.gameclient.IClientGameCreator;

/**
 * The event signaling that the game creation has started.
 * 
 * @author benobiwan
 * 
 */
public final class GameCreationStartedCtrlEvent extends AbstractControlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8611883659495207914L;

	/**
	 * The game creator to use on the client.
	 */
	private final IClientGameCreator<?, ?, ?, ?, ?, ?> _clientCreator;

	/**
	 * The id of created game.
	 */
	private final int _iGameId;

	/**
	 * The player id the client gave to the server when it asked to join.
	 */
	private final int _iPlayerId;

	/**
	 * creates a new GameCreationStartedCrEvent.
	 * 
	 * @param iGameId
	 *            the id of created game.
	 * @param iPlayerId
	 *            the player id the client gave to the server when it asked to
	 *            join.
	 * @param clientCreator
	 *            the game creator to use.
	 */
	public GameCreationStartedCtrlEvent(final int iGameId, final int iPlayerId,
			final IClientGameCreator<?, ?, ?, ?, ?, ?> clientCreator)
	{
		super(ControlEventType.GAME_CREATION_STARTED);
		_clientCreator = clientCreator;
		_iGameId = iGameId;
		_iPlayerId = iPlayerId;
	}

	/**
	 * Get the game creator to use on the client.
	 * 
	 * @return the game creator to use on the client.
	 */
	public IClientGameCreator<?, ?, ?, ?, ?, ?> getClientGameCreator()
	{
		return _clientCreator;
	}

	/**
	 * Get the id of the game the client joined.
	 * 
	 * @return the id of the game the client joined.
	 */
	public int getGameId()
	{
		return _iGameId;
	}

	/**
	 * Get the player id the client gave to the server when it asked to join.
	 * 
	 * @return the player id the client gave to the server when it asked to
	 *         join.
	 */
	public int getPlayerId()
	{
		return _iPlayerId;
	}
}
