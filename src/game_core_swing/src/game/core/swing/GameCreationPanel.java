package game.core.swing;

import game.common.IPlayerDescription;
import game.config.IGameConfiguration;
import game.gameclient.IClientGameCreator;
import game.gameclient.IGameCreatorChangeListener;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.google.common.eventbus.Subscribe;
import common.config.swing.ConfigurationPanel;

/**
 * Panel used to create a game.
 * 
 * @author benobiwan
 * 
 */
public final class GameCreationPanel extends JPanel implements
		IGameCreatorChangeListener
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -8995089599592601506L;

	/**
	 * The {@link ConfigurationPanel} used to display the configuration of the
	 * game.
	 */
	private final ConfigurationPanel _confPanel;

	/**
	 * The {@link PlayerListPanel} used to display the list of players in the
	 * game.
	 */
	private final PlayerListPanel _playerListPanel;

	/**
	 * The {@link IClientGameCreator} of this game.
	 */
	protected final IClientGameCreator<?, ?, ?, ?, ?, ?> _gameCreator;

	/**
	 * Boolean showing whether the local client is the creator of this game or
	 * not.
	 */
	protected final boolean _bCreator;

	/**
	 * Creates a new GameCreationPanel.
	 * 
	 * @param gameCreator
	 *            the {IClientGameCreator} to display.
	 */
	public GameCreationPanel(
			final IClientGameCreator<?, ?, ?, ?, ?, ?> gameCreator)
	{
		super(new BorderLayout());
		_gameCreator = gameCreator;
		_confPanel = new ConfigurationPanel(_gameCreator.getConfiguration(),
				false);
		_playerListPanel = new PlayerListPanel();
		_bCreator = _gameCreator.isCreator();
		String strCreate, strLeave;

		if (_bCreator)
		{
			strCreate = "Start Game";
			strLeave = "Cancel Game";
		}
		else
		{
			strCreate = "Ready";
			strLeave = "Leave Game";
		}

		final JButton buttonCreateGame = new JButton(strCreate);
		buttonCreateGame
				.addActionListener(new ReadyOrStartGameActionListener());
		final JButton buttonLeaveGame = new JButton(strLeave);
		buttonLeaveGame
				.addActionListener(new LeaveOrCancelGameActionListener());
		final JPanel buttonPane = new JPanel(new GridLayout(1, 0, 10, 10));
		buttonPane.add(buttonLeaveGame);
		buttonPane.add(buttonCreateGame);
		add(_confPanel, BorderLayout.CENTER);
		add(_playerListPanel, BorderLayout.LINE_START);
		add(buttonPane, BorderLayout.PAGE_END);
		_gameCreator.registerGameCreatorChangeListener(this);
	}

	@Override
	@Subscribe
	public void setConfiguration(final IGameConfiguration<?> gameConfiguration)
	{
		_confPanel.setConfiguration(gameConfiguration);
	}

	@Override
	@Subscribe
	public void setClientSidePlayerList(final Set<IPlayerDescription> playerList)
	{
		_playerListPanel.updatePlayerList(playerList);
	}

	/**
	 * {@link ActionListener} for the ready/start game {@link JButton}.
	 * 
	 * @author benobiwan
	 * 
	 */
	private final class ReadyOrStartGameActionListener implements
			ActionListener
	{
		/**
		 * Creates a new StartGameActionListener.
		 */
		public ReadyOrStartGameActionListener()
		{
			super();
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			if (_bCreator)
			{
				// Start Game
			}
			else
			{
				_gameCreator.updateReadyStatus(true);
			}
		}
	}

	/**
	 * {@link ActionListener} for the leave/cancel game {@link JButton}.
	 * 
	 * @author benobiwan
	 * 
	 */
	private final class LeaveOrCancelGameActionListener implements
			ActionListener
	{
		/**
		 * Creates a new CancelGameActionListener.
		 */
		public LeaveOrCancelGameActionListener()
		{
			super();
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			if (_bCreator)
			{
				// Cancel Game
			}
			else
			{
				// Leave Game
			}
		}
	}
}
