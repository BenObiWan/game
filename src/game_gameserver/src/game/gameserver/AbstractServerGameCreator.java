package game.gameserver;

import game.common.IGameDescription;
import game.common.IPlayerDescription;
import game.communication.IGameClient;
import game.communication.IGameServer;
import game.communication.action.GameCreationActionType;
import game.communication.action.GameCtrlActionType;
import game.communication.action.IGameAction;
import game.communication.action.IGameCreationAction;
import game.communication.action.IGameCtrlAction;
import game.communication.action.InconsistentActionTypeException;
import game.communication.action.gamecreation.UpdateStatusCrAction;
import game.communication.action.gamectrl.AddAICrAction;
import game.communication.action.gamectrl.JoinGameCrAction;
import game.communication.action.gamectrl.KickPlayerCrAction;
import game.communication.action.gamectrl.LeaveGameCrAction;
import game.communication.event.IEvent;
import game.communication.event.InconsistentEventTypeException;
import game.communication.event.control.GameJoinedCtrlEvent;
import game.communication.event.gamecreation.ConfigurationUpdateCrEvent;
import game.communication.event.gamectrl.GameFullCrEvent;
import game.communication.event.gamectrl.GameLeftCrEvent;
import game.communication.event.gamectrl.PlayerListUpdateCrEvent;
import game.config.IGameConfiguration;
import game.config.IPlayerConfiguration;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.Logger;

/**
 * Abstract implementation of the {@link IServerGameCreator}.
 * 
 * @author benobiwan
 * @param <PLAYER_CONF>
 * 
 * @param <ACTION_TYPE>
 *            the type of {@link IGameAction} handled by the game.
 * @param <SERVER_GAME_TYPE>
 *            the type of the {@link IServerSideGame} to create.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure the game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IServerSidePlayer} used in this game.
 */
public abstract class AbstractServerGameCreator<PLAYER_CONF extends IPlayerConfiguration, ACTION_TYPE extends IGameAction, CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, PLAYER_TYPE extends IServerSidePlayer<PLAYER_CONF>, SERVER_GAME_TYPE extends IServerSideGame<ACTION_TYPE, CONF_TYPE, PLAYER_TYPE>>
		implements
		IServerGameCreator<PLAYER_CONF, ACTION_TYPE, CONF_TYPE, PLAYER_TYPE, SERVER_GAME_TYPE>
{
	/**
	 * Logger object.
	 */
	private static transient final Logger LOGGER = Logger
			.getLogger(AbstractServerGameCreator.class);

	/**
	 * Lock to protect the access to the other parameters of the object.
	 */
	protected transient final Object _lock = new Object();

	/**
	 * List of players in this game.
	 */
	protected transient final ConcurrentSkipListSet<PLAYER_TYPE> _playerList = new ConcurrentSkipListSet<PLAYER_TYPE>();

	/**
	 * List of clients in this game.
	 */
	protected transient final ConcurrentSkipListSet<IGameClient> _clientList = new ConcurrentSkipListSet<IGameClient>();

	/**
	 * Id of the game.
	 */
	protected transient int _iGameId;

	/**
	 * The player who created the game.
	 */
	protected transient PLAYER_TYPE _creatorPlayer;

	/**
	 * Configuration of the game.
	 */
	protected transient CONF_TYPE _conf;

	/**
	 * {@link IGameServer} hosting the game.
	 */
	protected transient IGameServer _gameServer;

	/**
	 * The {@link IGameDescription} of this game.
	 */
	protected transient IGameDescription _gameDescription;

	@Override
	public void initialize(final IGameServer gameServer, final int iGameId,
			final IGameClient creatorClient, final int iCreatorPlayerId)
	{
		synchronized (_lock)
		{
			_gameServer = gameServer;
			_iGameId = iGameId;
			_creatorPlayer = createPlayer(creatorClient, iCreatorPlayerId);
			_playerList.add(_creatorPlayer);
			_clientList.add(creatorClient);
			_gameDescription = createGameDescription();
		}
	}

	@Override
	public final int getGameId()
	{
		synchronized (_lock)
		{
			return _iGameId;
		}
	}

	@Override
	public final PLAYER_TYPE getCreatorPlayer()
	{
		synchronized (_lock)
		{
			return _creatorPlayer;
		}
	}

	@Override
	public final CONF_TYPE getGameConfiguration()
	{
		synchronized (_lock)
		{
			return _conf;
		}
	}

	@Override
	public final IGameServer getGameServer()
	{
		synchronized (_lock)
		{
			return _gameServer;
		}
	}

	@Override
	public boolean isFull()
	{
		if (_playerList.size() >= _conf.getMaxNumberOfPlayers())
		{
			return true;
		}
		return false;
	}

	@Override
	public final IGameDescription getDescription()
	{
		synchronized (_lock)
		{
			return _gameDescription;
		}
	}

	/**
	 * Remove a player from the list of players in this game. Also remove the
	 * client if it is the last player of this particular client in this game.
	 * 
	 * @param player
	 *            the player to remove to this game.
	 * @param client
	 *            the client from which the player is coming.
	 */
	private void removePlayer(final IServerSidePlayer player,
			final IGameClient client)
	{
		boolean bKeepClient = false;
		_playerList.remove(player);
		client.removeServerSidePlayer(player);
		for (final IServerSidePlayer pList : _playerList)
		{
			bKeepClient |= client.containServerSidePlayer(pList);
		}
		if (!bKeepClient)
		{
			_clientList.remove(client);
		}
		_gameDescription.setNumberOfPlayer(_playerList.size());
	}

	/**
	 * Add a player to this game. Also add the client.
	 * 
	 * @param player
	 *            the player to add to this game.
	 * @param client
	 *            the client on which the player is created.
	 */
	private void addPlayer(final PLAYER_TYPE player, final IGameClient client)
	{
		client.addServerSidePlayer(player);
		_playerList.add(player);
		_clientList.add(client);
		_gameDescription.setNumberOfPlayer(_playerList.size());
	}

	/**
	 * Send a {@link ConfigurationUpdateCrEvent} to every player.
	 */
	private void sendConfigurationUpdate()
	{
		for (final PLAYER_TYPE player : _playerList)
		{
			final ConfigurationUpdateCrEvent event = new ConfigurationUpdateCrEvent(
					_iGameId, player.getId(), _conf);
			try
			{
				player.getClient().handleEvent(_gameServer, event);
			}
			catch (final InconsistentEventTypeException e)
			{
				LOGGER.error(e);
			}
		}
	}

	/**
	 * Send a {@link PlayerListUpdateCrEvent} to every player.
	 */
	private void sendPlayerListUpdate()
	{
		final SortedSet<IPlayerDescription> playerList = new TreeSet<IPlayerDescription>();
		for (final PLAYER_TYPE player : _playerList)
		{
			playerList.add(player.getDescription());
		}
		for (final PLAYER_TYPE player : _playerList)
		{
			final PlayerListUpdateCrEvent event = new PlayerListUpdateCrEvent(
					_iGameId, player.getId(), playerList);
			try
			{
				player.getClient().handleEvent(_gameServer, event);
			}
			catch (final InconsistentEventTypeException e)
			{
				LOGGER.error(e);
			}
		}
	}

	@Override
	public void handleGameCreationAction(final IGameClient client,
			final IGameCreationAction act)
			throws InconsistentActionTypeException
	{
		switch (act.getType())
		{

		case UPDATE_STATUS:
			if (act instanceof UpdateStatusCrAction)
			{
				handleUpdateStatusGameCrAction(client,
						(UpdateStatusCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCreationActionType.UPDATE_STATUS, act.getClass());
			}
			break;

		}
	}

	@Override
	public void handleGameCtrlAction(final IGameClient client,
			final IGameCtrlAction act) throws InconsistentActionTypeException
	{
		switch (act.getType())
		{
		case JOIN_GAME:
			if (act instanceof JoinGameCrAction)
			{
				handleJoinGameCrAction(client, (JoinGameCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCtrlActionType.JOIN_GAME, act.getClass());
			}
			break;
		case LEAVE_GAME:
			if (act instanceof LeaveGameCrAction)
			{
				handleLeaveGameCrAction(client, (LeaveGameCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCtrlActionType.LEAVE_GAME, act.getClass());
			}
			break;
		case KICK_PLAYER:
			if (act instanceof KickPlayerCrAction)
			{
				handleKickPlayerGameCrAction(client, (KickPlayerCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCtrlActionType.KICK_PLAYER, act.getClass());
			}
			break;
		case ADD_AI:
			if (act instanceof AddAICrAction)
			{
				handleAddAIGameCrAction(client, (AddAICrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCtrlActionType.ADD_AI, act.getClass());
			}
		}
	}

	/**
	 * Handle a {@link IGameCreationAction}.
	 * 
	 * @param client
	 *            the client from which the game creation action is coming.
	 * @param act
	 *            the action to handle.
	 */
	private void handleJoinGameCrAction(final IGameClient client,
			final JoinGameCrAction act)
	{
		IEvent evt;
		synchronized (_lock)
		{
			if (isFull())
			{
				evt = new GameFullCrEvent(_iGameId, act.getPlayerId());
			}
			else
			{
				final PLAYER_TYPE player = createPlayer(client,
						act.getPlayerId());
				addPlayer(player, client);
				evt = new GameJoinedCtrlEvent(_iGameId, act.getPlayerId(),
						getClientGameCreator());
			}
		}
		sendPlayerListUpdate();
		try
		{
			client.handleEvent(getGameServer(), evt);
		}
		catch (final InconsistentEventTypeException e)
		{
			LOGGER.error(e);
		}
	}

	/**
	 * Handle a {@link LeaveGameCrAction}.
	 * 
	 * @param client
	 *            the client from which the leave game action is coming.
	 * @param act
	 *            the action to handle.
	 */
	private void handleLeaveGameCrAction(final IGameClient client,
			final LeaveGameCrAction act)
	{
		IEvent evt = null;
		synchronized (_lock)
		{
			final IServerSidePlayer player = client.getServerSidePlayer(act
					.getPlayerId());
			if (player != null)
			{
				removePlayer(player, client);
				if (player.equals(_creatorPlayer))
				{
					// TODO destroy the game
				}
				evt = new GameLeftCrEvent(_iGameId, act.getPlayerId());
			}
		}
		try
		{
			if (evt != null)
			{
				client.handleEvent(getGameServer(), evt);
			}
		}
		catch (final InconsistentEventTypeException e)
		{
			LOGGER.error(e);
		}
	}

	/**
	 * Handle a {@link UpdateStatusCrAction}.
	 * 
	 * @param client
	 *            the client from which the update status action is coming.
	 * @param act
	 *            the action to handle.
	 */
	private void handleUpdateStatusGameCrAction(final IGameClient client,
			final UpdateStatusCrAction act)
	{
		synchronized (_lock)
		{
			for (final PLAYER_TYPE player : _playerList)
			{
				if (client.containServerSidePlayer(player))
				{
					player.setReady(act.isReady());
				}
			}
		}
		sendPlayerListUpdate();
	}

	/**
	 * Handle a {@link KickPlayerCrAction}.
	 * 
	 * @param client
	 *            the client from which the kick player action is coming.
	 * @param act
	 *            the action to handle.
	 */
	private void handleKickPlayerGameCrAction(final IGameClient client,
			final KickPlayerCrAction act)
	{

	}

	/**
	 * Handle a {@link AddAICrAction}.
	 * 
	 * @param client
	 *            the client from which the add AI action is coming.
	 * @param act
	 *            the action to handle.
	 */
	private void handleAddAIGameCrAction(final IGameClient client,
			final AddAICrAction act)
	{
		IEvent evt = null;
		synchronized (_lock)
		{
			if (isFull())
			{
				evt = new GameFullCrEvent(_iGameId, act.getAIId());
			}
			// TODO check if the client has the right to create an AI.
			else
			{
				final PLAYER_TYPE player = createAI(client, act.getAIId(),
						act.getName());
				addPlayer(player, client);
				evt = new GameJoinedCtrlEvent(_iGameId, act.getAIId(),
						getClientGameCreator());
			}
		}
		try
		{
			client.handleEvent(getGameServer(), evt);
		}
		catch (final InconsistentEventTypeException e)
		{
			LOGGER.error(e);
		}
	}
}
