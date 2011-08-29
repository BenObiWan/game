package game.core.ui;

import game.gameclient.AbstractClientGameCreator;

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
	 * The {@link AbstractClientGameCreator} of this game.
	 */
	private final AbstractClientGameCreator<?, ?, ?, ?, ?> _gameCreator;

	/**
	 * Creates a new GameCreationPanel.
	 * 
	 * @param gameCreator
	 *            the {AbstractClientGameCreator} to display.
	 */
	public GameCreationPanel(
			final AbstractClientGameCreator<?, ?, ?, ?, ?> gameCreator)
	{
		super(new BorderLayout());
		_gameCreator = gameCreator;
		_confPanel = new ConfigurationPanel(_gameCreator.getConfiguration());
		_playerListPanel = new PlayerListPanel();
		_gameCreator.addObserver(this);
		final JButton buttonCreateGame = new JButton("Create Game");
		final JButton buttonCancelGame = new JButton("Cancel Game");
		final JPanel buttonPane = new JPanel(new GridLayout(1, 0, 10, 10));
		buttonPane.add(buttonCancelGame);
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

		}
	}
}
