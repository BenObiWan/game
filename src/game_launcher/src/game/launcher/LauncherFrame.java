package game.launcher;

import game.core.ApplicationCore;
import game.core.swing.CoreUI;
import game.core.swing.IGameSwingLauncher;
import game.launcher.config.ILauncherConfiguration;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

/**
 * Frame used to launch the application. Displays 3 buttons: local game, host
 * game and join game. Pressing any of those buttons close the frame and create
 * a new {@link CoreUI} frame showing internal frames appropriate to the pressed
 * button.
 * 
 * @author benobiwan
 * 
 */
public final class LauncherFrame extends JFrame implements ActionListener
{
	/**
	 * Action command for the local game button.
	 */
	private static final String LOCAL_GAME_ACTION_COMMAND = "local_game";

	/**
	 * Action command for the host game button.
	 */
	private static final String HOST_GAME_ACTION_COMMAND = "host_game";

	/**
	 * Action command for the join game button.
	 */
	private static final String JOIN_GAME_ACTION_COMMAND = "join_game";

	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = Logger.getLogger(LauncherFrame.class);

	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 7850068285755022675L;

	/**
	 * The {@link ILauncherConfiguration} of this program.
	 */
	@SuppressWarnings("unused")
	private final ILauncherConfiguration _launcherConf;

	/**
	 * The application core.
	 */
	private final ApplicationCore _appCore;

	/**
	 * The core UI.
	 */
	private final CoreUI _coreUI;

	/**
	 * Creates a new LauncherFrame.
	 * 
	 * @param launcherConf
	 *            the {@link ILauncherConfiguration} of this program.
	 * @param appCore
	 *            the application core.
	 * @param gameLauncherSet
	 *            set of all loaded {@link IGameSwingLauncher}.
	 * @throws HeadlessException
	 *             Thrown when code that is dependent on a keyboard, display, or
	 *             mouse is called in an environment that does not support a
	 *             keyboard, display, or mouse.
	 */
	public LauncherFrame(final ILauncherConfiguration launcherConf,
			final ApplicationCore appCore,
			final Set<IGameSwingLauncher> gameLauncherSet)
			throws HeadlessException
	{
		super("Launcher");
		_launcherConf = launcherConf;
		_appCore = appCore;
		_coreUI = new CoreUI(_appCore, gameLauncherSet);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel contPane = new JPanel(new GridLayout(3, 1, 15, 15));
		final JButton jbLocal = new JButton("Local Game");
		jbLocal.setActionCommand(LOCAL_GAME_ACTION_COMMAND);
		jbLocal.addActionListener(this);
		contPane.add(jbLocal);
		final JButton jbHost = new JButton("Host Game");
		jbHost.setActionCommand(HOST_GAME_ACTION_COMMAND);
		jbHost.addActionListener(this);
		contPane.add(jbHost);
		final JButton jbJoin = new JButton("Join Game");
		jbJoin.setActionCommand(JOIN_GAME_ACTION_COMMAND);
		jbJoin.addActionListener(this);
		contPane.add(jbJoin);
		final Border bor = new CompoundBorder(new EmptyBorder(15, 25, 15, 25),
				new CompoundBorder(new BevelBorder(BevelBorder.RAISED),
						new EmptyBorder(15, 25, 15, 25)));
		contPane.setBorder(bor);

		setContentPane(contPane);
	}

	@Override
	public void actionPerformed(final ActionEvent ae)
	{
		_coreUI.setVisible(true);
		setVisible(false);
		if (LOCAL_GAME_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			_coreUI.showCreateGameWindow();
		}
		else if (HOST_GAME_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			try
			{
				_coreUI.showCreateGameWindow();
				_appCore.activateNetworkServer();
			}
			catch (final IOException ex)
			{
				LOGGER.error(ex);
			}
		}
		else if (JOIN_GAME_ACTION_COMMAND.equals(ae.getActionCommand()))
		{
			_coreUI.showServerListWindow();
		}
		else
		{
			LOGGER.error("Action command " + ae.getActionCommand()
					+ " unknown.");
		}
	}
}
