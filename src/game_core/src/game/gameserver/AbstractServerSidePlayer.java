package game.gameserver;

import game.common.IGameClient;
import game.common.IPlayerDescription;
import game.common.PlayerDescriptionImpl;
import game.config.IPlayerConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation of the {@link IServerSidePlayer} interface.
 * Description of a player on the server side.
 * 
 * @author benobiwan
 * 
 * @param <PLAYER_CONF>
 *            the type of player configuration.
 */
public abstract class AbstractServerSidePlayer<PLAYER_CONF extends IPlayerConfiguration>
		implements IServerSidePlayer<PLAYER_CONF>
{
	/**
	 * Logger object.
	 */
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AbstractServerSidePlayer.class);

	/**
	 * The hosting client.
	 */
	protected final IGameClient _hostingClient;

	/**
	 * Id of the player.
	 */
	protected final int _iPlayerId;

	/**
	 * The game creator this player is joining.
	 */
	protected final IServerGameCreator<?, ?, ?, ?, ?> _serverGameCreator;

	/**
	 * The description of the player.
	 */
	protected final IPlayerDescription _playerDescription;

	/**
	 * The game where this player is playing.
	 */
	protected IServerSideGame<?, ?, ?, ?> _serverSideGame;

	/**
	 * Configuration of the player.
	 */
	protected PLAYER_CONF _playerConf;

	/**
	 * Lock used to protect the {@link IPlayerConfiguration} and the
	 * {@link IServerSideGame}.
	 */
	protected final Object _lock = new Object();

	/**
	 * Creates a new AbstractServerSidePlayer.
	 * 
	 * @param iPlayerId
	 *            the id of the player.
	 * @param hostingClient
	 *            the hosting client.
	 * @param strName
	 *            the name of the player.
	 * @param bIsAI
	 *            whether this player is an AI or not.
	 * @param serverGameCreator
	 *            the {@link IServerGameCreator} which created this game.
	 */
	protected AbstractServerSidePlayer(final int iPlayerId,
			final IGameClient hostingClient, final String strName,
			final boolean bIsAI,
			final IServerGameCreator<?, ?, ?, ?, ?> serverGameCreator)
	{
		_iPlayerId = iPlayerId;
		_hostingClient = hostingClient;
		_serverGameCreator = serverGameCreator;
		_playerDescription = new PlayerDescriptionImpl(strName, bIsAI);
	}

	@Override
	public IGameClient getClient()
	{
		return _hostingClient;
	}

	@Override
	public boolean isReady()
	{
		// TODO rework the handling of player configuration.
		return /* isPlayerConfigurationSet() && */_playerDescription.isReady();
	}

	@Override
	public void setReady(final boolean bReady)
	{
		_playerDescription.setReady(bReady);
	}

	@Override
	public String getName()
	{
		return _playerDescription.getPlayerName();
	}

	@Override
	public int getId()
	{
		return _iPlayerId;
	}

	@Override
	public int compareTo(final IServerSidePlayer<PLAYER_CONF> o)
	{
		int iComp = _hostingClient.compareTo(o.getClient());
		if (iComp == 0)
		{
			iComp += _iPlayerId;
			iComp -= o.getId();
		}
		return iComp;
	}

	@Override
	public IServerSideGame<?, ?, ?, ?> getGame()
	{
		synchronized (_lock)
		{
			return _serverSideGame;
		}
	}

	@Override
	public IServerGameCreator<?, ?, ?, ?, ?> getGameCreator()
	{
		return _serverGameCreator;
	}

	@Override
	public IPlayerDescription getDescription()
	{
		return _playerDescription;
	}

	@Override
	public boolean isAI()
	{
		return _playerDescription.isAI();
	}

	@Override
	public PLAYER_CONF getPlayerConfiguration()
	{
		synchronized (_lock)
		{
			return _playerConf;
		}
	}

	@Override
	public boolean isPlayerConfigurationSet()
	{
		return _playerConf != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPlayerConfiguration(final IPlayerConfiguration conf)
	{
		try
		{
			synchronized (_lock)
			{
				_playerConf = (PLAYER_CONF) conf;
			}
		}
		catch (final ClassCastException e)
		{
			LOGGER.error("Error casting " + conf.getClass() + " into "
					+ _playerConf.getClass());
		}
	}
}
