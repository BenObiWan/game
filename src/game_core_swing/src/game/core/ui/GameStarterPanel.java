package game.core.ui;

import game.communication.IGameListDescription;
import game.communication.IGameServer;
import game.gameclient.LocalGameClient;
import game.network.ConnectionList;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel for the game creation.
 * 
 * @author benobiwan
 * 
 */
public final class GameStarterPanel extends JPanel
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -7489677570890716090L;

	/**
	 * Combo box for choosing the server.
	 */
	private final JComboBox<IGameServer> _comboServer = new JComboBox<IGameServer>();

	/**
	 * Combo box for choosing the type of game.
	 */
	private final JComboBox<IGameListDescription> _comboGameType = new JComboBox<IGameListDescription>();

	/**
	 * List of all connections.
	 */
	private final ConnectionList _connectionList;

	/**
	 * The local game client.
	 */
	private final LocalGameClient _localGameClient;

	/**
	 * Creates a new GameCreationPanel.
	 * 
	 * @param localGameClient
	 *            the local game client.
	 * @param connectionList
	 *            list of all connections.
	 */
	public GameStarterPanel(final LocalGameClient localGameClient,
			final ConnectionList connectionList)
	{
		super(new BorderLayout(5, 5));
		setBorder(new EmptyBorder(5, 5, 5, 5));

		_connectionList = connectionList;
		_localGameClient = localGameClient;

		final JPanel panelCombo = new JPanel(new GridLayout(0, 2, 10, 5));

		panelCombo.add(new JLabel("server"));
		panelCombo.add(_comboServer);
		panelCombo.add(new JLabel("game type"));
		panelCombo.add(_comboGameType);

		_comboServer.addActionListener(new ServerChosenActionListener());

		final JButton buttonCreate = new JButton("Create game");
		buttonCreate.addActionListener(new CreateGameActionListener());

		final JPanel panelButton = new JPanel(new GridLayout(1, 0, 5, 5));
		panelButton.add(buttonCreate);

		add(panelCombo, BorderLayout.CENTER);
		add(panelButton, BorderLayout.PAGE_END);
		updateServerList();
		updateGameList();
	}

	/**
	 * Update the list of servers in the combo box.
	 */
	public void updateServerList()
	{
		_comboServer.removeAllItems();
		for (int i = 0; i < _connectionList.getNumberOfServers(); i++)
		{
			final IGameServer server = _connectionList.getServerAtIndex(i);
			if (server.isGameCreationAllowed())
			{
				_comboServer.addItem(server);
			}
		}
	}

	/**
	 * Update the list of games in the combo box.
	 */
	public void updateGameList()
	{
		_comboGameType.removeAllItems();
		final IGameServer server = _comboServer.getItemAt(_comboServer
				.getSelectedIndex());
		for (final IGameListDescription desc : server.getAvailableGames())
		{
			_comboGameType.addItem(desc);
		}
	}

	/**
	 * Call create game on the {@link LocalGameClient}.
	 */
	protected void createGame()
	{
		final IGameServer selectedServer = _comboServer.getItemAt(_comboServer
				.getSelectedIndex());
		final IGameListDescription selectedGame = _comboGameType
				.getItemAt(_comboGameType.getSelectedIndex());

		if (selectedServer != null && selectedGame != null)
		{
			final IGameServer server = selectedServer;
			final IGameListDescription gameDescription = selectedGame;
			_localGameClient.sendCreateGame(gameDescription, server);
		}
	}

	/**
	 * {@link ActionListener} for the Create game button.
	 * 
	 * @author benobiwan
	 * 
	 */
	private final class CreateGameActionListener implements ActionListener
	{
		/**
		 * Creates a new CreateGameActionListener.
		 */
		public CreateGameActionListener()
		{
			super();
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			createGame();
		}
	}

	/**
	 * {@link ActionListener} for the select server combo box.
	 * 
	 * @author benobiwan
	 * 
	 */
	private final class ServerChosenActionListener implements ActionListener
	{
		/**
		 * Creates a new ServerChosenActionListener.
		 */
		public ServerChosenActionListener()
		{
			super();
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			updateGameList();
		}
	}
}