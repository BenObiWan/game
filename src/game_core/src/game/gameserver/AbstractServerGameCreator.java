package game.gameserver;

import game.common.IGameClient;
import game.common.IGameInstanceDescription;
import game.common.IGameServer;
import game.common.IPlayerDescription;
import game.communication.action.GameCreationActionType;
import game.communication.action.GameCtrlActionType;
import game.communication.action.IGameAction;
import game.communication.action.IGameCreationAction;
import game.communication.action.IGameCtrlAction;
import game.communication.action.InconsistentActionTypeException;
import game.communication.action.gamecreation.SendGameConfigurationGameCrAction;
import game.communication.action.gamecreation.SendPlayerConfigurationGameCrAction;
import game.communication.action.gamecreation.StartGameCrAction;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation of the {@link IServerGameCreator}.
 * 
 * @author benobiwan
 * 
 * @param <PLAYER_CONF>
 *            the type of {@link IPlayerConfiguration} of this game.
 * @param <ACTION_TYPE>
 *            the type of {@link IGameAction} handled by the game.
 * @param <SERVER_GAME_TYPE>
 *            the type of the {@link IServerSideGame} to create.
 * @param <CONF_TYPE>
 *            the type of {@link IGameConfiguration} used to configure the game.
 * @param <PLAYER_TYPE>
 *            the type of {@link IServerSidePlayer} used in this game.
 */
public abstract class AbstractServerGameCreator<PLAYER_CONF extends IPlayerConfiguration, ACTION_TYPE extends IGameAction, CONF_TYPE extends IGameConfiguration<PLAYER_CONF>, PLAYER_TYPE extends IServerSidePlayer<PLAYER_CONF>, SERVER_GAME_TYPE extends IServerSideGame<ACTION_TYPE, CONF_TYPE, PLAYER_CONF, PLAYER_TYPE>>
		implements
		IServerGameCreator<PLAYER_CONF, ACTION_TYPE, CONF_TYPE, PLAYER_TYPE, SERVER_GAME_TYPE>
{
	/**
	 * Logger object.
	 */
	private static transient final Logger LOGGER = LoggerFactory
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
	 * The {@link IGameInstanceDescription} of this game.
	 */
	protected transient IGameInstanceDescription _gameDescription;

	@Override
	public void initialize(final IGameServer gameServer, final int iGameId,
			final IGameClient creatorClient, final int iCreatorPlayerId)
	{
		synchronized (_lock)
		{
			_gameServer = gameServer;
			_iGameId = iGameId;
			_creatorPlayer = createPlayer(creatorClient, iCreatorPlayerId);
			addPlayer(_creatorPlayer, creatorClient);
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
	public final IGameInstanceDescription getDescription()
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
	private void removePlayer(final IServerSidePlayer<?> player,
			final IGameClient client)
	{
		boolean bKeepClient = false;
		_playerList.remove(player);
		client.removeServerSidePlayer(player);
		for (final IServerSidePlayer<?> pList : _playerList)
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
		client.addServerSidePlayer(player);
	}

	@Override
	public void sendConfigurationUpdate()
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
				LOGGER.error(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void sendPlayerListUpdate()
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
				LOGGER.error(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void handleGameCreationAction(final IServerSidePlayer<?> player,
			final IGameCreationAction act)
			throws InconsistentActionTypeException
	{
		switch (act.getType())
		{
		case UPDATE_STATUS:
			if (act instanceof UpdateStatusCrAction)
			{
				handleUpdateStatusGameCrAction(player,
						(UpdateStatusCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCreationActionType.UPDATE_STATUS, act.getClass());
			}
			break;
		case START_GAME:
			if (act instanceof StartGameCrAction)
			{
				handleStartGameCrAction(player, (StartGameCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCreationActionType.START_GAME, act.getClass());
			}
			break;
		case SEND_PLAYER_CONF:
			if (act instanceof SendPlayerConfigurationGameCrAction)
			{
				handleSendPlayerConfigurationGameCrAction(player,
						(SendPlayerConfigurationGameCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCreationActionType.SEND_PLAYER_CONF, act.getClass());
			}
			break;
		case SEND_GAME_CONF:
			if (act instanceof SendGameConfigurationGameCrAction)
			{
				handleSendGameConfigurationGameCrAction(player,
						(SendGameConfigurationGameCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCreationActionType.SEND_GAME_CONF, act.getClass());
			}
			break;
		}
	}

	@Override
	public void handleGameCtrlAction(final IGameClient client,
			final IServerSidePlayer<?> player, final IGameCtrlAction act)
			throws InconsistentActionTypeException
	{
		// JOIN_GAME doesn't need to check for null player
		if (act.getType().equals(GameCtrlActionType.JOIN_GAME))
		{
			if (act instanceof JoinGameCrAction)
			{
				handleJoinGameCrAction(client, (JoinGameCrAction) act);
			}
			else
			{
				throw new InconsistentActionTypeException(
						GameCtrlActionType.JOIN_GAME, act.getClass());
			}
		}
		else
		{
			// TODO check if player is null
			switch (act.getType())
			{
			case LEAVE_GAME:
				if (act instanceof LeaveGameCrAction)
				{
					handleLeaveGameCrAction(player, (LeaveGameCrAction) act);
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
					handleKickPlayerGameCrAction(player,
							(KickPlayerCrAction) act);
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
					handleAddAIGameCrAction(player, (AddAICrAction) act);
				}
				else
				{
					throw new InconsistentActionTypeException(
							GameCtrlActionType.ADD_AI, act.getClass());
				}
				break;
			case JOIN_GAME:
				break;
			}
		}
	}

	@Override
	public void handleJoinGameCrAction(final IGameClient client,
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
				_gameDescription.setNumberOfPlayer(_playerList.size());
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
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void handleLeaveGameCrAction(final IServerSidePlayer<?> player,
			final LeaveGameCrAction act)
	{
		IEvent evt = null;
		final IGameClient client = player.getClient();

		synchronized (_lock)
		{
			removePlayer(player, client);
			if (player.equals(_creatorPlayer))
			{
				// TODO destroy the game
			}
			evt = new GameLeftCrEvent(_iGameId, act.getPlayerId());
		}
		try
		{
			client.handleEvent(getGameServer(), evt);
		}
		catch (final InconsistentEventTypeException e)
		{
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void handleUpdateStatusGameCrAction(
			final IServerSidePlayer<?> player, final UpdateStatusCrAction act)
	{
		synchronized (_lock)
		{
			player.setReady(act.isReady());
		}
		sendPlayerListUpdate();
	}

	@Override
	public void handleStartGameCrAction(final IServerSidePlayer<?> player,
			final StartGameCrAction act)
	{
		synchronized (_lock)
		{
			// check if the client is the creator
			if (_creatorPlayer.equals(player))
			{
				// set the creator player ready
				_creatorPlayer.setReady(true);
				// check if every player is ready
				boolean bReady = true;
				for (final PLAYER_TYPE playerList : _playerList)
				{
					bReady &= playerList.isReady();
				}
				if (bReady)
				{
					// TODO start game
				}
			}
		}
	}

	@Override
	public void handleKickPlayerGameCrAction(final IServerSidePlayer<?> player,
			final KickPlayerCrAction act)
	{
		// TODO handleKickPlayerGameCrAction
	}

	@Override
	public void handleAddAIGameCrAction(final IServerSidePlayer<?> player,
			final AddAICrAction act)
	{
		IEvent evt = null;
		final IGameClient client = player.getClient();
		synchronized (_lock)
		{
			if (isFull())
			{
				evt = new GameFullCrEvent(_iGameId, act.getAIId());
			}
			// TODO check if the client has the right to create an AI.
			else
			{
				final PLAYER_TYPE aiPlayer = createAI(client, act.getAIId(),
						act.getName());
				addPlayer(aiPlayer, client);
				_gameDescription.setNumberOfPlayer(_playerList.size());
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
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void handleSendPlayerConfigurationGameCrAction(
			final IServerSidePlayer<?> player,
			final SendPlayerConfigurationGameCrAction act)
	{
		synchronized (_lock)
		{
			player.setPlayerConfiguration(act.getPlayerConfiguration());
		}
		sendPlayerListUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleSendGameConfigurationGameCrAction(
			final IServerSidePlayer<?> player,
			final SendGameConfigurationGameCrAction act)
	{
		synchronized (_lock)
		{
			// check if the client is the creator
			if (_creatorPlayer.equals(player))
			{
				final IGameConfiguration<?> conf = act.getGameConfiguration();
				if (conf != null)
				{
					try
					{
						_conf = (CONF_TYPE) conf;
					}
					catch (final ClassCastException e)
					{
						// TODO class error
					}
				}
			}
		}
		sendConfigurationUpdate();
	}
}
