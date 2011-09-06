package game.core.swing;

import game.core.ApplicationCore;
import game.network.config.INetworkServerConfiguration;
import game.network.config.NetworkXMLFileConfigurationLoader;
import game.network.swing.ServerListPanel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.config.swing.ConfigurationPanel;
import common.logging.swing.LogInternalFrame;

/**
 * Core UI of the game.
 * 
 * @author benobiwan
 * 
 */
public final class CoreUI extends JFrame implements ActionListener
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 598006827838376134L;

	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CoreUI.class);

	/**
	 * Valid icon.
	 */
	// public static final ImageIcon VALID_ICON = new ImageIcon(
	// "/home/benobiwan/eclipse_workspace/game_core_gui/src/icons/valid.png");

	/**
	 * Invalid icon.
	 */
	// public static final ImageIcon INVALID_ICON = new ImageIcon(
	// "/home/benobiwan/eclipse_workspace/game_core_gui/src/icons/invalid.png");

	/**
	 * Locked icon.
	 */
	// public static final ImageIcon LOCKED_ICON = new ImageIcon(
	// "/home/benobiwan/eclipse_workspace/game_core_gui/src/icons/locked.png");

	/**
	 * Unlocked icon.
	 */
	// public static final ImageIcon UNLOCKED_ICON = new ImageIcon(
	// "/home/benobiwan/eclipse_workspace/game_core_gui/src/icons/unlocked.png");

	/**
	 * Action command for the show log command.
	 */
	private static final String SHOW_LOG_ACTION_COMMAND = "show_log";

	/**
	 * Action command for the quit command.
	 */
	private static final String QUIT_ACTION_COMMAND = "quit";

	/**
	 * Action command for the log test command.
	 */
	private static final String LOG_TEST_ACTION_COMMAND = "logtest";

	/**
	 * Action command for the test configuration command.
	 */
	private static final String TEST_CONF_ACTION_COMMAND = "testconf";

	/**
	 * Action command for the test server list command.
	 */
	private static final String TEST_SERVER_LIST_ACTION_COMMAND = "testserverlist";

	/**
	 * Action command for the test game list command.
	 */
	private static final String TEST_GAME_LIST_ACTION_COMMAND = "testgamelist";

	/**
	 * Action command for the test game creation command.
	 */
	private static final String TEST_GAME_CREATION_ACTION_COMMAND = "testgamecreation";

	/**
	 * The desktop pane of the application.
	 */
	private final JDesktopPane _desktop;

	/**
	 * The log window.
	 */
	private LogInternalFrame _logWindow;

	/**
	 * The create game window.
	 */
	private final JInternalFrame _createGameWindow = new JInternalFrame(
			"Game creation", true, true, true, true);

	/**
	 * The server list window.
	 */
	private final JInternalFrame _serverListWindow = new JInternalFrame(
			"Server list", true, true, true, true);

	/**
	 * The application core.
	 */
	private final ApplicationCore _appCore;

	/**
	 * Creates a new CoreUI.
	 * 
	 * @param appCore
	 *            the application core.
	 * @param gameLauncherSet
	 *            set of all loaded {@link IGameSwingLauncher}.
	 */
	public CoreUI(final ApplicationCore appCore,
			final Set<IGameSwingLauncher> gameLauncherSet)
	{
		super("Core UI");
		_appCore = appCore;

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener()
		{

			@Override
			public void windowClosing(final WindowEvent e)
			{
				stop();
			}

			@Override
			public void windowClosed(final WindowEvent e)
			{
				// TODO do this better
				System.exit(0);
			}

			@Override
			public void windowOpened(final WindowEvent e)
			{
				// nothing to do
			}

			@Override
			public void windowIconified(final WindowEvent e)
			{
				// nothing to do
			}

			@Override
			public void windowDeiconified(final WindowEvent e)
			{
				// nothing to do
			}

			@Override
			public void windowActivated(final WindowEvent e)
			{
				// nothing to do
			}

			@Override
			public void windowDeactivated(final WindowEvent e)
			{
				// nothing to do
			}
		});

		// Make the big window be indented 50 pixels from each edge
		// of the screen.
		final int inset = 50;
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height
				- inset * 2);

		// Set up the GUI.
		_desktop = new JDesktopPane();
		setContentPane(_desktop);
		setJMenuBar(createMenuBar());

		try
		{
			_logWindow = new LogInternalFrame(new ScheduledThreadPoolExecutor(
					10));
			_logWindow.setSize(1000, 300);
		}
		catch (final IOException e)
		{
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		_createGameWindow.setContentPane(new GameStarterPanel(_appCore
				.getLocalGameClient(), _appCore.getNetworkMain()
				.getConnectionList(), gameLauncherSet));
		_createGameWindow.pack();

		_serverListWindow.setContentPane(new ServerListPanel(_appCore
				.getNetworkMain().getConnectionList()));
		_serverListWindow.pack();

		confLog4j();
		// createConfigWindow();
		_logWindow.start();
		// createConfigExplorer();
		showLogWindow();
		// createStartupPanel();
		// createSessionListPanel();
		// Make dragging a little faster but perhaps uglier.
		// desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	}

	/**
	 * Creates a log4j configuration.
	 */
	private void confLog4j()
	{
		final PatternLayout layout = new PatternLayout(
				"%d [%t] %-5p (%F:%L) %c - %m%n");
		final WriterAppender app = new WriterAppender(layout,
				_logWindow.getOutputStream());
		BasicConfigurator.configure(app);
	}

	/**
	 * Create the menu bar.
	 * 
	 * @return the menu bar for this application.
	 */
	private JMenuBar createMenuBar()
	{
		final JMenuBar menuBar = new JMenuBar();

		final JMenu testMenu = new JMenu("Test");
		testMenu.setMnemonic(KeyEvent.VK_T);
		menuBar.add(testMenu);

		JMenuItem menuItem = new JMenuItem("test conf");
		menuItem.setActionCommand(TEST_CONF_ACTION_COMMAND);
		menuItem.addActionListener(this);
		testMenu.add(menuItem);

		menuItem = new JMenuItem("Test server list");
		menuItem.setActionCommand(TEST_SERVER_LIST_ACTION_COMMAND);
		menuItem.addActionListener(this);
		testMenu.add(menuItem);

		menuItem = new JMenuItem("Test game list");
		menuItem.setActionCommand(TEST_GAME_LIST_ACTION_COMMAND);
		menuItem.addActionListener(this);
		testMenu.add(menuItem);

		menuItem = new JMenuItem("Test game creation");
		menuItem.setActionCommand(TEST_GAME_CREATION_ACTION_COMMAND);
		menuItem.addActionListener(this);
		testMenu.add(menuItem);

		menuItem = new JMenuItem("Quit");
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand(QUIT_ACTION_COMMAND);
		menuItem.addActionListener(this);
		testMenu.add(menuItem);

		final JMenu logMenu = new JMenu("Logging");
		logMenu.setMnemonic(KeyEvent.VK_L);
		menuBar.add(logMenu);

		menuItem = new JMenuItem("Show window");
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand(SHOW_LOG_ACTION_COMMAND);
		menuItem.addActionListener(this);
		logMenu.add(menuItem);

		menuItem = new JMenuItem("Test log");
		menuItem.setMnemonic(KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand(LOG_TEST_ACTION_COMMAND);
		menuItem.addActionListener(this);
		logMenu.add(menuItem);

		return menuBar;
	}

	// private void createSessionListPanel()
	// {
	// final JInternalFrame sessionListFrame = new JInternalFrame(
	// "Session list", true, true, true, true);
	// final SessionListPanel sessListPanel = new SessionListPanel(
	// _configLoader.getCoreConfiguration(), _launcher
	// .getNetworkMain().getSessionList());
	// sessionListFrame.setContentPane(sessListPanel);
	// // configFrame.setSize(500, 500);
	// sessionListFrame.pack();
	// sessionListFrame.setVisible(true);
	// _desktop.add(sessionListFrame);
	// }

	// private void createConfigExplorer()
	// {
	// final JInternalFrame configFrame = new JInternalFrame(
	// "Configuration Panel", true, true, true, true);
	// configFrame
	// .setContentPane(new ConfigurationExplorerPanel(_configLoader));
	// configFrame.pack();
	// configFrame.setSize(550, 250);
	// configFrame.setVisible(true);
	// try
	// {
	// configFrame.setSelected(true);
	// }
	// catch (final java.beans.PropertyVetoException e)
	// {
	// // TODO
	// }
	// _desktop.add(configFrame);
	// }

	@Override
	public void actionPerformed(final ActionEvent ae)
	{
		if (SHOW_LOG_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			showLogWindow();
		}
		else if (QUIT_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			stop();
		}
		else if (LOG_TEST_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			LOGGER.info("Action command Test");
		}
		else if (TEST_CONF_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			final NetworkXMLFileConfigurationLoader confLoader = new NetworkXMLFileConfigurationLoader(
					ManagementFactory.getPlatformMBeanServer());
			final INetworkServerConfiguration srvConf = confLoader
					.getNetworkServerConfiguration();
			final ConfigurationPanel confPane = new ConfigurationPanel(srvConf);
			final JInternalFrame intFrame = new JInternalFrame("conf", true,
					true, true, true);
			intFrame.setContentPane(confPane);
			intFrame.pack();
			showInternalFrame(intFrame, true);
		}
		else if (TEST_SERVER_LIST_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			showServerListWindow();
		}
		else if (TEST_GAME_LIST_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			final GameListPanel gamePanel = new GameListPanel();
			final JInternalFrame intFrame = new JInternalFrame("Game list",
					true, true, true, true);
			intFrame.setContentPane(gamePanel);
			intFrame.pack();
			showInternalFrame(intFrame, true);
		}
		else if (TEST_GAME_CREATION_ACTION_COMMAND
				.equals(ae.getActionCommand()))
		{
			showCreateGameWindow();
		}

		else
		{
			LOGGER.error("Action command " + ae.getActionCommand()
					+ " unknown.");
		}
	}

	/**
	 * Stop the application.
	 */
	public void stop()
	{
		final int iAnswer = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to exit?", "Exit",
				JOptionPane.YES_NO_OPTION);
		switch (iAnswer)
		{
		case JOptionPane.YES_OPTION:
			_appCore.stop();
			dispose();
			break;
		case JOptionPane.NO_OPTION:
		case JOptionPane.CLOSED_OPTION:
		default:
			break;
		}
	}

	/**
	 * Show the log window.
	 */
	public void showLogWindow()
	{
		showInternalFrame(_logWindow, false);
	}

	/**
	 * Show the server list window.
	 */
	public void showServerListWindow()
	{
		showInternalFrame(_serverListWindow, true);
	}

	/**
	 * Show the create game window.
	 */
	public void showCreateGameWindow()
	{
		showInternalFrame(_createGameWindow, true);
	}

	/**
	 * Show an internal frame. Resize and move it to fit in the main window.
	 * 
	 * @param frame
	 *            the frame to show.
	 * @param bSelected
	 *            whether the frame should be selected when it's showed.
	 */
	private void showInternalFrame(final JInternalFrame frame,
			final boolean bSelected)
	{
		if (!frame.isVisible())
		{
			frame.setSize(Math.min(getWidth(), frame.getWidth()),
					Math.min(getHeight(), frame.getHeight()));
			final Point location = frame.getLocation();
			if (location.x < 0)
			{
				location.x = 0;
			}
			if (location.y < 0)
			{
				location.y = 0;
			}
			frame.setLocation(location);
			frame.setVisible(true);
			_desktop.add(frame);
			try
			{
				frame.setSelected(bSelected);
			}
			catch (final PropertyVetoException e)
			{
				LOGGER.error(e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * Set the look and feel of the program.
	 */
	public static void setLookAndFeel()
	{
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager
			// .getCrossPlatformLookAndFeelClassName());
		}
		catch (final UnsupportedLookAndFeelException e)
		{
			// handle exception
		}
		catch (final ClassNotFoundException e)
		{
			// handle exception
		}
		catch (final InstantiationException e)
		{
			// handle exception
		}
		catch (final IllegalAccessException e)
		{
			// handle exception
		}
	}
}
