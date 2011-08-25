package game.launcher.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.management.MBeanServer;

import common.config.AbstractPropertyConfigurationLoader;
import common.config.InvalidConfigurationException;

/**
 * {@link ILauncherConfigurationLoader} that loads the
 * {@link ILauncherConfiguration} from a Property file.
 * 
 * @author benobiwan
 * 
 */
public final class LauncherPropertyConfigurationLoader extends
		AbstractPropertyConfigurationLoader implements
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
	 * Full tag for the is daemon configuration option.
	 */
	private final String _strIsDeamonFullTag = PREFIX
			+ ILauncherConfiguration.LAUNCHER_CONFIGURATION_TAG + CONF_NODE_SEP
			+ ILauncherConfiguration.IS_DAEMON_TAG;

	/**
	 * Creates a new LauncherPropertyConfigurationLoader without reading from a
	 * property file.
	 * 
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @throws InvalidConfigurationException
	 *             one of the configuration value is invalid.
	 */
	public LauncherPropertyConfigurationLoader(final MBeanServer mBeanServer)
			throws InvalidConfigurationException
	{
		super(mBeanServer);
		_launcherConfig = new LauncherConfigurationImpl(null, _mBeanServer,
				readBooleanFromProperty(_commandLineConf, _strIsDeamonFullTag));
	}

	/**
	 * Creates a new LauncherPropertyConfigurationLoader reading from an
	 * {@link InputStream}.
	 * 
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param inStream
	 *            the {@link InputStream} from which to read.
	 * @param bXML
	 *            true if the input file is an XML property file.
	 * @throws IOException
	 *             if an error occurred when reading from the input stream.
	 * @throws InvalidConfigurationException
	 *             one of the configuration value is invalid.
	 */
	public LauncherPropertyConfigurationLoader(final MBeanServer mBeanServer,
			final InputStream inStream, final boolean bXML) throws IOException,
			InvalidConfigurationException
	{
		super(mBeanServer, inStream, bXML);
		_launcherConfig = new LauncherConfigurationImpl(null, _mBeanServer,
				readBooleanFromProperty(_commandLineConf, _strIsDeamonFullTag),
				readBooleanFromProperty(_fileConf, _strIsDeamonFullTag));
	}

	/**
	 * Creates a new {@link LauncherPropertyConfigurationLoader} reading from a
	 * {@link Reader}.
	 * 
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param reader
	 *            the {@link Reader} from which to read.
	 * @throws IOException
	 *             if an error occurred when reading from the input stream.
	 * @throws InvalidConfigurationException
	 *             one of the configuration value is invalid.
	 */
	public LauncherPropertyConfigurationLoader(final MBeanServer mBeanServer,
			final Reader reader) throws IOException,
			InvalidConfigurationException
	{
		super(mBeanServer, reader);
		_launcherConfig = new LauncherConfigurationImpl(null, _mBeanServer,
				readBooleanFromProperty(_commandLineConf, _strIsDeamonFullTag),
				readBooleanFromProperty(_fileConf, _strIsDeamonFullTag));
	}

	@Override
	public String getFullCommandLinePrefix()
	{
		return PREFIX;
	}

	@Override
	public ILauncherConfiguration getLauncherConfiguration()
	{
		return _launcherConfig;
	}
}
