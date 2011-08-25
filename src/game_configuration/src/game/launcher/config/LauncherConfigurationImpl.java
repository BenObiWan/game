package game.launcher.config;

import javax.management.MBeanServer;

import common.config.AbstractConfigurationBranch;
import common.config.IConfiguration;
import common.config.InvalidConfigurationException;
import common.config.display.BooleanDisplayType;
import common.config.leaf.ConfigurationBoolean;

/**
 * An implementation of the {@link ILauncherConfiguration} interface.
 * 
 * @author benobiwan
 * 
 */
public final class LauncherConfigurationImpl extends
		AbstractConfigurationBranch implements ILauncherConfiguration
{
	/**
	 * Leaf configuring whether this application is a daemon server or has a
	 * GUI.
	 */
	private final ConfigurationBoolean _leafIsDaemon;

	/**
	 * Creates a new LauncherConfigurationImpl using default values for every
	 * element.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public LauncherConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer)
	{
		super(parent, LAUNCHER_CONFIGURATION_TAG, mBeanServer);
		_leafIsDaemon = new ConfigurationBoolean(this, IS_DAEMON_TAG,
				"Enable daemon mode.", "Enable daemon mode (no GUI).",
				"Invalid daemon/GUI app boolean", false,
				BooleanDisplayType.CHECKBOX, Boolean.FALSE);
		addLeaf(_leafIsDaemon);
	}

	/**
	 * Creates a new LauncherConfigurationImpl and sets the command line values.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param bCommandLineIsDaemon
	 *            the value specified on the command line for toggling between
	 *            daemon mode or GUI.
	 * @throws InvalidConfigurationException
	 *             one of the given value is invalid.
	 */
	public LauncherConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer, final Boolean bCommandLineIsDaemon)
			throws InvalidConfigurationException
	{
		super(parent, LAUNCHER_CONFIGURATION_TAG, mBeanServer);
		_leafIsDaemon = new ConfigurationBoolean(this, IS_DAEMON_TAG,
				"Enable server", "Enable server.",
				"Invalid server/not server boolean", false,
				BooleanDisplayType.CHECKBOX, Boolean.FALSE,
				bCommandLineIsDaemon);
		addLeaf(_leafIsDaemon);
	}

	/**
	 * Creates a new CoreConfigurationImpl and sets the command line and
	 * configuration values.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param bCommandLineIsDaemon
	 *            the value specified on the command line for toggling between
	 *            daemon mode or GUI.
	 * @param bConfigurationIsDaemon
	 *            the value specified in the saved configuration for toggling
	 *            between daemon mode or GUI.
	 * @throws InvalidConfigurationException
	 *             one of the given value is invalid.
	 */
	public LauncherConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer, final Boolean bCommandLineIsDaemon,
			final Boolean bConfigurationIsDaemon)
			throws InvalidConfigurationException
	{
		this(parent, mBeanServer, bCommandLineIsDaemon);
		_leafIsDaemon.setConfigurationValue(bConfigurationIsDaemon);
	}

	@Override
	public boolean isDaemon()
	{
		return _leafIsDaemon.getCurrentValue().booleanValue();
	}

	@Override
	public String getDescription()
	{
		return "Launcher Configuration";
	}
}
