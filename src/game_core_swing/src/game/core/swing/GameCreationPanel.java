package game.core.swing;

import game.gameclient.IClientGameCreator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import common.config.swing.ConfigurationPanel;

/**
 * Panel used to create a game.
 * 
 * @author benobiwan
 * 
 */
public final class GameCreationPanel extends JPanel implements Observer
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
	private final IClientGameCreator<?, ?, ?, ?, ?, ?> _gameCreator;

	/**
	 * Creates a new GameCreationPanel.
	 * 
	 * @param gameCreator
	 *            the {IClientGameCreator} to display.
	 * @param bCreator
	 *            whether the local client is the creator of this game or not.
	 */
	public GameCreationPanel(
			final IClientGameCreator<?, ?, ?, ?, ?, ?> gameCreator,
			final boolean bCreator)
	{
		super(new BorderLayout());
		_gameCreator = gameCreator;
		_confPanel = new ConfigurationPanel(_gameCreator.getConfiguration());
		_playerListPanel = new PlayerListPanel();
		_gameCreator.addObserver(this);

		String strCreate, strLeave;

		if (bCreator)
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
		final JButton buttonLeaveGame = new JButton(strLeave);
		final JPanel buttonPane = new JPanel(new GridLayout(1, 0, 10, 10));
		buttonPane.add(buttonLeaveGame);
		buttonPane.add(buttonCreateGame);
		add(_confPanel, BorderLayout.CENTER);
		add(_playerListPanel, BorderLayout.LINE_START);
		add(buttonPane, BorderLayout.PAGE_END);
	}

	@Override
	public void update(final Observable o, final Object arg)
	{
		if (_gameCreator.equals(o))
		{
			_playerListPanel.updatePlayerList(_gameCreator
					.getClientSidePlayerList());
		}
	}
}
