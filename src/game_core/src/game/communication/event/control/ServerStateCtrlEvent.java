package game.communication.event.control;

import game.common.IGameInstanceDescription;
import game.communication.event.AbstractControlEvent;
import game.communication.event.ControlEventType;
import game.gameserver.IServerState;

import java.util.TreeSet;

/**
 * The event describing the state of the server to the client.
 * 
 * @author benobiwan
 * 
 */
public class ServerStateCtrlEvent extends AbstractControlEvent
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -5053702938884982811L;

	/**
	 * State of the server.
	 */
	private final IServerState _serverState;

	/**
	 * Set holding the list of {@link IGameInstanceDescription} to send to the
	 * client.
	 */
	private final TreeSet<IGameInstanceDescription> _setDescription;

	// TODO add the list of connected players

	// TODO add the rights of the player

	/**
	 * Creates a new ServerStateCtrlEvent.
	 * 
	 * @param serverState
	 *            state of the server.
	 * 
	 * @param setDescription
	 *            set holding the list of {@link IGameInstanceDescription} to
	 *            send to the client.
	 */
	public ServerStateCtrlEvent(final IServerState serverState,
			final TreeSet<IGameInstanceDescription> setDescription)
	{
		super(ControlEventType.SERVER_STATE);
		_serverState = serverState;
		_setDescription = setDescription;
	}

	/**
	 * Get the set holding the list of {@link IGameInstanceDescription} to send
	 * to the client.
	 * 
	 * @return the set holding the list of {@link IGameInstanceDescription} to
	 *         send to the client.
	 */
	public TreeSet<IGameInstanceDescription> getGameDescriptionList()
	{
		return _setDescription;
	}

	/**
	 * Get the state of the server.
	 * 
	 * @return the state of the server.;
	 */
	public IServerState getServerState()
	{
		return _serverState;
	}

}
