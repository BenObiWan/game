package game.launcher.config;

import javax.management.MBeanServer;

import common.config.AbstractXMLFileConfigurationLoader;

/**
 * {@link ILauncherConfigurationLoader} that loads the
 * {@link ILauncherConfiguration} from an XML file.
 * 
 * @author benobiwan
 * 
 */
public final class LauncherXMLFileConfigurationLoader extends
		AbstractXMLFileConfigurationLoader implements
		ILauncherConfigurationLoader
{
	/**
	 * Full prefix for this configuration loader.
	 */
	private static final String PREFIX = BASE_CONF_PREFIX + CONF_NODE_SEP
			+ LAUNCHER_CONF_PREFIX + CONF_NODE_SEP;

	/**
	 * The launcher configuration.
	 */
	private final ILauncherConfiguration _launcherConfig;

	/**
	 * Creates a new LauncherXMLFileConfigurationLoader.
	 * 
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public LauncherXMLFileConfigurationLoader(final MBeanServer mBeanServer)
	{
		super(mBeanServer);
		_launcherConfig = new LauncherConfigurationImpl(null, _mBeanServer);
	}

	@Override
	public ILauncherConfiguration getLauncherConfiguration()
	{
		return _launcherConfig;
	}

	@Override
	public String getFullCommandLinePrefix()
	{
		return PREFIX;
	}
}
