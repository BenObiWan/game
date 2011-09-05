package game.network.config;

import javax.management.MBeanServer;

import common.config.AbstractXMLFileConfigurationLoader;

/**
 * {@link INetworkConfigurationLoader} that loads the
 * {@link INetworkConfiguration}, {@link INetworkServerConfiguration} and
 * {@link INetworkClientConfigurationList} from an XML file.
 * 
 * @author benobiwan
 * 
 */
public final class NetworkXMLFileConfigurationLoader extends
		AbstractXMLFileConfigurationLoader implements
		INetworkConfigurationLoader
{
	/**
	 * Full prefix for this configuration loader.
	 */
	private static final String PREFIX = BASE_CONF_PREFIX + CONF_NODE_SEP
			+ NETWORK_CONF_PREFIX + CONF_NODE_SEP;

	/**
	 * The common network configuration.
	 */
	private final INetworkConfiguration _networkConfig;

	/**
	 * The network server configuration.
	 */
	private final INetworkServerConfiguration _networkServerConfig;

	/**
	 * The list of network client configuration.
	 */
	private final INetworkClientConfigurationList _networkClientConfig;

	/**
	 * Creates a new NetworkXMLFileConfigurationLoader.
	 * 
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public NetworkXMLFileConfigurationLoader(final MBeanServer mBeanServer)
	{
		super(mBeanServer);
		_networkConfig = new NetworkConfigurationImpl(null, _mBeanServer);
		_networkServerConfig = new NetworkServerConfigurationImpl(null,
				_mBeanServer);
		_networkClientConfig = new NetworkClientConfigurationListImpl(null,
				_mBeanServer);
		// TODO pour test
		final NetworkClientConfigurationElementImpl locConf = new NetworkClientConfigurationElementImpl(
				_networkClientConfig, "localhost", _mBeanServer);

		_networkClientConfig.addElement(locConf);
	}

	@Override
	public String getFullCommandLinePrefix()
	{
		return PREFIX;
	}

	@Override
	public INetworkConfiguration getNetworkConfiguration()
	{
		return _networkConfig;
	}

	@Override
	public INetworkServerConfiguration getNetworkServerConfiguration()
	{
		return _networkServerConfig;
	}

	@Override
	public INetworkClientConfigurationList getNetworkINetworkClientConfigurationList()
	{
		return _networkClientConfig;
	}
}
