package game.launcher;

import game.common.IGameDescription;
import game.core.ApplicationCore;
import game.core.swing.CoreUI;
import game.core.swing.IGameSwingLauncher;
import game.launcher.config.ILauncherConfiguration;
import game.launcher.config.ILauncherConfigurationLoader;
import game.launcher.config.LauncherPropertyConfigurationLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.config.InvalidConfigurationException;

/**
 * Launcher class for the game application.
 * 
 * Loads the configuration, then, if it's specified on the configuration loads
 * the GUI or act in server mode.
 * 
 * @author benobiwan
 * 
 */
public final class Launcher
{
	/**
	 * Logger object.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Launcher.class);

	/**
	 * Option used to change the configuration directory.
	 */
	public static final String OPTION_CONF = "conf";

	/**
	 * Option used to display the help message.
	 */
	public static final String OPTION_HELP = "h";

	/**
	 * Option used to pass the application in server mode.
	 */
	public static final String OPTION_SERVER = "s";

	/**
	 * List of all the options that can be read on the command line. Not
	 * including option directly passed to the JVM.
	 */
	private final Options _optionsList = new Options();

	/**
	 * Parser used to parse the options given on the command line.
	 */
	private final CommandLineParser _parser = new PosixParser();

	/**
	 * Help formatter used to display the help message.
	 */
	private final HelpFormatter _formatter = new HelpFormatter();

	/**
	 * Object describing which options have been read on the command line.
	 */
	private CommandLine _commandLine;

	/**
	 * Configuration directory that will be used.
	 */
	private String _strConfigurationPath;

	/**
	 * The {@link ILauncherConfiguration} of this program.
	 */
	private ILauncherConfiguration _launcherConf;

	/**
	 * Set of {@link IGameDescription} loaded.
	 */
	private final Set<IGameDescription> _gameDescriptionSet = new TreeSet<>();

	/**
	 * Set of {@link IGameSwingLauncher} loaded.
	 */
	private final Set<IGameSwingLauncher> _gameLauncherSet = new TreeSet<>();

	/**
	 * Creates a new Launcher.
	 */
	public Launcher()
	{
		_optionsList.addOption(new Option(OPTION_CONF, true,
				"Configuration directory."));
		_optionsList
				.addOption(new Option(OPTION_SERVER, false, "server mode."));
	}

	/**
	 * Read the command line and load the configuration.
	 * 
	 * @param args
	 *            the command line arguments.
	 * @throws ParseException
	 *             if an error in encountered during the parsing or if the help
	 *             is asked for.
	 * @throws InvalidConfigurationException
	 */
	public void load(final String[] args) throws ParseException,
			InvalidConfigurationException
	{
		parse(args);
		loadLoggingConfiguration();
		loadConfiguration();
		readGameDescFiles();
	}

	/**
	 * Parses command line arguments.
	 * 
	 * @param args
	 *            the command line arguments.
	 * @throws ParseException
	 *             if an error in encountered during the parsing or if the help
	 *             is asked for.
	 */
	private void parse(final String[] args) throws ParseException
	{
		_commandLine = _parser.parse(_optionsList, args);
		if (_commandLine.hasOption(OPTION_HELP))
		{
			throw new ParseException("Help message asked for.");
		}
		if (_commandLine.hasOption(OPTION_CONF))
		{
			_strConfigurationPath = _commandLine.getOptionValue(OPTION_CONF);
		}
		else
		{
			_strConfigurationPath = System.getProperty("user.home") + "/.game/";
		}
		if (_commandLine.hasOption(OPTION_SERVER))
		{
			System.getProperties().setProperty(
					"conf.launcher.launcher.IsServer", "true");
		}
	}

	/**
	 * Load the logging configuration.
	 */
	private void loadLoggingConfiguration()
	{
		// PatternLayout layout = new PatternLayout(
		// "%d [%t] %-5p (%F:%L) %c - %m%n");
		// WriterAppender app = new WriterAppender(layout,
		// m_logWindow.getOutputStream());
		final ConsoleAppender app = new ConsoleAppender(new PatternLayout(
				"%d{ISO8601} [%t] %-5p %C:%L %x - %m%n"));

		BasicConfigurator.configure(app);
	}

	/**
	 * Load the {@link ILauncherConfiguration} of this program.
	 * 
	 * @throws InvalidConfigurationException
	 *             one of the configuration value is invalid.
	 */
	private void loadConfiguration() throws InvalidConfigurationException
	{
		final File confDir = new File(_strConfigurationPath);
		if (!(confDir.exists() && confDir.isDirectory()))
		{
			LOGGER.warn("Configuration directory " + _strConfigurationPath
					+ " doesn't exists.");
		}
		final ILauncherConfigurationLoader _launcherConfLoader = new LauncherPropertyConfigurationLoader(
				ManagementFactory.getPlatformMBeanServer());
		_launcherConf = _launcherConfLoader.getLauncherConfiguration();
	}

	/**
	 * Read the game descriptions files to extract the registered
	 * {@link IGameDescription} and {@link IGameSwingLauncher}.
	 */
	private void readGameDescFiles()
	{
		final File descDir = new File(_strConfigurationPath + "/desc/");
		for (final File descFile : descDir.listFiles())
		{
			try
			{
				if (descFile.isFile() && descFile.canRead())
				{
					final Properties prop = new Properties();
					final FileReader reader = new FileReader(descFile);
					prop.load(reader);

					final String strGameDesc = prop.getProperty("GameDesc");
					if (strGameDesc != null)
					{
						final Object objGameDesc = Class.forName(strGameDesc)
								.newInstance();
						if (objGameDesc instanceof IGameDescription)
						{
							_gameDescriptionSet
									.add((IGameDescription) objGameDesc);
						}
					}
					final String strSwingLauncher = prop
							.getProperty("SwingLauncher");
					if (strSwingLauncher != null)
					{
						final Object objSwingLauncher = Class.forName(
								strSwingLauncher).newInstance();
						if (objSwingLauncher instanceof IGameSwingLauncher)
						{
							_gameLauncherSet
									.add((IGameSwingLauncher) objSwingLauncher);
						}
					}
					reader.close();
				}
			}
			catch (final IOException e)
			{
				LOGGER.error("Error loading game description file.", e);
			}
			catch (final ClassNotFoundException e)
			{
				LOGGER.error("Error loading game description file.", e);
			}
			catch (final InstantiationException e)
			{
				LOGGER.error("Error loading game description file.", e);
			}
			catch (final IllegalAccessException e)
			{
				LOGGER.error("Error loading game description file.", e);
			}
		}
	}

	/**
	 * Print the help of this application.
	 */
	public void printHelp()
	{
		_formatter.printHelp("game", _optionsList);
	}

	/**
	 * Start the application.
	 * 
	 * @throws IOException
	 *             an IO error occurred during the activation of the network
	 *             server.
	 */
	public void start() throws IOException
	{
		final ApplicationCore appCore = new ApplicationCore(_gameDescriptionSet);

		if (_launcherConf.isDaemon())
		{
			appCore.activateNetworkServer();
		}
		else
		{
			CoreUI.setLookAndFeel();
			final LauncherFrame frame = new LauncherFrame(_launcherConf,
					appCore, _gameLauncherSet);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}

	/**
	 * @param args
	 *            the arguments given to the program.
	 * @throws IOException
	 *             an IO error occurred during the activation of the network
	 *             server.
	 * @throws InvalidConfigurationException
	 *             one of the configuration value is invalid.
	 */
	public static void main(final String[] args) throws IOException,
			InvalidConfigurationException
	{
		final Launcher appMain = new Launcher();
		try
		{
			appMain.load(args);
			appMain.start();
		}
		catch (final ParseException e)
		{
			appMain.printHelp();
		}
	}
}
