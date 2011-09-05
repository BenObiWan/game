package game.core.swing;

import game.communication.IGameListDescription;
import game.communication.IGameServer;
import game.communication.IGameSwingLauncher;
import game.gameclient.IClientGameCreator;
import game.gameclient.LocalGameClient;
import game.network.ConnectionList;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

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
	private final JComboBox<IGameSwingLauncher> _comboGameType = new JComboBox<IGameSwingLauncher>();

	/**
	 * List of all connections.
	 */
	private final ConnectionList _connectionList;

	/**
	 * The local game client.
	 */
	private final LocalGameClient _localGameClient;

	/**
	 * Map of all registered {@link IGameSwingLauncher} using the relevant
	 * {@link IGameListDescription} has a key.
	 */
	private final ConcurrentMap<IGameListDescription, IGameSwingLauncher> _launcherMapByDesc = new ConcurrentSkipListMap<IGameListDescription, IGameSwingLauncher>();

	/**
	 * Map of all registered {@link IGameSwingLauncher} using the relevant
	 * {@link IClientGameCreator} class has a key.
	 */
	private final ConcurrentMap<String, IGameSwingLauncher> _launcherMapByCreatorClass = new ConcurrentSkipListMap<String, IGameSwingLauncher>();

	/**
	 * Creates a new GameCreationPanel.
	 * 
	 * @param localGameClient
	 *            the local game client.
	 * @param connectionList
	 *            list of all connections.
	 * @param gameLauncherSet
	 *            set of all loaded {@link IGameSwingLauncher}.
	 */
	public GameStarterPanel(final LocalGameClient localGameClient,
			final ConnectionList connectionList,
			final Set<IGameSwingLauncher> gameLauncherSet)
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

		for (final IGameSwingLauncher gameLauncher : gameLauncherSet)
		{
			_launcherMapByDesc.put(gameLauncher.getGameListDescription(),
					gameLauncher);
			_launcherMapByCreatorClass.put(gameLauncher
					.getIClientGameCreatorClass().getCanonicalName(),
					gameLauncher);
		}

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
			final IGameSwingLauncher gameLauncher = _launcherMapByDesc
					.get(desc);
			if (gameLauncher != null)
			{
				_comboGameType.addItem(gameLauncher);
			}
		}
	}

	/**
	 * Call create game on the {@link LocalGameClient}.
	 */
	protected void createGame()
	{
		final IGameServer selectedServer = _comboServer.getItemAt(_comboServer
				.getSelectedIndex());
		final IGameSwingLauncher selectedGameLauncher = _comboGameType
				.getItemAt(_comboGameType.getSelectedIndex());

		if (selectedServer != null && selectedGameLauncher != null)
		{
			_localGameClient.sendCreateGame(
					selectedGameLauncher.createServerGameCreator(),
					selectedServer);
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