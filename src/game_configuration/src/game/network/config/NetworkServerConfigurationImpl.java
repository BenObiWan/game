package game.network.config;

import javax.management.MBeanServer;

import common.config.AbstractConfigurationBranch;
import common.config.IConfiguration;
import common.config.InvalidConfigurationException;
import common.config.display.IntegerDisplayType;
import common.config.display.LongDisplayType;
import common.config.leaf.ConfigurationInteger;
import common.config.leaf.ConfigurationLong;

/**
 * An implementation of the {@link INetworkServerConfiguration} interface.
 * 
 * @author benobiwan
 * 
 */
public class NetworkServerConfigurationImpl extends AbstractConfigurationBranch
		implements INetworkServerConfiguration
{
	/**
	 * Leaf configuring the server listen port.
	 */
	private final ConfigurationInteger _leafListenPort;

	/**
	 * Leaf configuring the maximum number of client.
	 */
	private final ConfigurationInteger _leafMaxNumberOfClient;

	/**
	 * Leaf configuring the connection timeout.
	 */
	private final ConfigurationInteger _leafConnectionTimeOut;

	/**
	 * Leaf configuring the allowed interval between connections from the same
	 * client.
	 */
	private final ConfigurationLong _leafAllowedInterval;

	/**
	 * Creates a new NetworkServerConfigurationImpl using default values for
	 * every elements.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public NetworkServerConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer)
	{
		super(parent, NETWORK_SERVER_CONFIGURATION_TAG, mBeanServer);
		_leafListenPort = new ConfigurationInteger(this, LISTEN_PORT_TAG,
				"Listen port", "Server listen port.", "Invalid listen port",
				false, IntegerDisplayType.SPINNER, Integer.valueOf(1024),
				Integer.valueOf(65535), Integer.valueOf(1664));
		_leafMaxNumberOfClient = new ConfigurationInteger(this,
				NUMBER_OF_CLIENT_TAG, "Max clients",
				"Maximum number of clients, 0 is infinite.",
				"Invalid maximum number of clients", true,
				IntegerDisplayType.TEXTFIELD, Integer.valueOf(0), null,
				Integer.valueOf(0));
		_leafConnectionTimeOut = new ConfigurationInteger(this,
				CLIENT_CONNECTION_TIMEOUT_TAG, "Client connection timeout",
				"Client connection timeout.",
				"Invalid client connection timeout", true,
				IntegerDisplayType.SPINNER, Integer.valueOf(0),
				Integer.valueOf(600), Integer.valueOf(120));
		_leafAllowedInterval = new ConfigurationLong(
				this,
				ALLOWED_INTERVAL_TAG,
				"Allowed interval",
				"Allowed interval between connections from the same client in milliseconds.",
				"Invalid allowed interval", true, LongDisplayType.SPINNER, Long
						.valueOf(100), Long.valueOf(10000), Long.valueOf(1000));
		addLeaf(_leafListenPort);
		addLeaf(_leafMaxNumberOfClient);
		addLeaf(_leafConnectionTimeOut);
		addLeaf(_leafAllowedInterval);
	}

	/**
	 * Creates a new NetworkServerConfigurationImpl and sets the command line
	 * values.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param iCommandLineListenPort
	 *            the value specified on the command line for the listen port.
	 * @param iCommandLineMaxNumberOfClient
	 *            the value specified on the command line for the maximum number
	 *            of client.
	 * @param iCommandLineConnectionTimeOut
	 *            the value specified on the command line for the client
	 *            connection time out.
	 * @param lCommandLineAllowedInterval
	 *            the value specified on the command line for the allowed
	 *            interval between connections from the same client.
	 * @throws InvalidConfigurationException
	 *             one of the given value is invalid.
	 */
	public NetworkServerConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer,
			final Integer iCommandLineListenPort,
			final Integer iCommandLineMaxNumberOfClient,
			final Integer iCommandLineConnectionTimeOut,
			final Long lCommandLineAllowedInterval)
			throws InvalidConfigurationException
	{
		super(parent, NETWORK_SERVER_CONFIGURATION_TAG, mBeanServer);
		_leafListenPort = new ConfigurationInteger(this, LISTEN_PORT_TAG,
				"Listen port", "Server listen port.", "Invalid listen port",
				false, IntegerDisplayType.SPINNER, Integer.valueOf(1024),
				Integer.valueOf(65535), Integer.valueOf(1664),
				iCommandLineListenPort);
		_leafMaxNumberOfClient = new ConfigurationInteger(this,
				NUMBER_OF_CLIENT_TAG, "Max clients",
				"Maximum number of clients, 0 is infinite.",
				"Invalid maximum number of clients", true,
				IntegerDisplayType.TEXTFIELD, Integer.valueOf(0), null,
				Integer.valueOf(0), iCommandLineMaxNumberOfClient);
		_leafConnectionTimeOut = new ConfigurationInteger(this,
				CLIENT_CONNECTION_TIMEOUT_TAG, "Client connection timeout",
				"Client connection timeout.",
				"Invalid client connection timeout", true,
				IntegerDisplayType.SPINNER, Integer.valueOf(0),
				Integer.valueOf(600), Integer.valueOf(120),
				iCommandLineConnectionTimeOut);
		_leafAllowedInterval = new ConfigurationLong(
				this,
				ALLOWED_INTERVAL_TAG,
				"Allowed interval",
				"Allowed interval beetween connections from the same client in milliseconds.",
				"Invalid allowed interval", true, LongDisplayType.SPINNER, Long
						.valueOf(100), Long.valueOf(10000), Long.valueOf(1000),
				lCommandLineAllowedInterval);
		addLeaf(_leafListenPort);
		addLeaf(_leafMaxNumberOfClient);
		addLeaf(_leafConnectionTimeOut);
		addLeaf(_leafAllowedInterval);
	}

	/**
	 * Creates a new NetworkServerConfigurationImpl and sets the command line
	 * and configuration values.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param iCommandLineListenPort
	 *            the value specified on the command line for the listen port.
	 * @param iCommandLineMaxNumberOfClient
	 *            the value specified on the command line for the maximum number
	 *            of client.
	 * @param iCommandLineConnectionTimeOut
	 *            the value specified on the command line for the client
	 *            connection time out.
	 * @param lCommandLineAllowedInterval
	 *            the value specified on the command line for the allowed
	 *            interval between connections from the same client.
	 * @param iConfigurationListenPort
	 *            the value specified in the configuration file for the listen
	 *            port.
	 * @param iConfigurationMaxNumberOfClient
	 *            the value specified in the configuration file for the maximum
	 *            number of client.
	 * @param iConfigurationConnectionTimeOut
	 *            the value specified in the configuration file for the client
	 *            connection time out.
	 * @param lConfigurationAllowedInterval
	 *            the value specified in the configuration file for the allowed
	 *            interval between connections from the same client.
	 * @throws InvalidConfigurationException
	 *             one of the given value is invalid.
	 */
	public NetworkServerConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer,
			final Integer iCommandLineListenPort,
			final Integer iCommandLineMaxNumberOfClient,
			final Integer iCommandLineConnectionTimeOut,
			final Long lCommandLineAllowedInterval,
			final Integer iConfigurationListenPort,
			final Integer iConfigurationMaxNumberOfClient,
			final Integer iConfigurationConnectionTimeOut,
			final Long lConfigurationAllowedInterval)
			throws InvalidConfigurationException
	{
		this(parent, mBeanServer, iCommandLineListenPort,
				iCommandLineMaxNumberOfClient, iCommandLineConnectionTimeOut,
				lCommandLineAllowedInterval);
		_leafListenPort.setConfigurationValue(iConfigurationListenPort);
		_leafMaxNumberOfClient
				.setConfigurationValue(iConfigurationMaxNumberOfClient);
		_leafConnectionTimeOut
				.setConfigurationValue(iConfigurationConnectionTimeOut);
		_leafAllowedInterval
				.setConfigurationValue(lConfigurationAllowedInterval);
	}

	@Override
	public int getListenPort()
	{
		return _leafListenPort.getCurrentValue().intValue();
	}

	@Override
	public int getNumberOfClient()
	{
		return _leafMaxNumberOfClient.getCurrentValue().intValue();
	}

	@Override
	public long getAllowedInterval()
	{
		return _leafAllowedInterval.getCurrentValue().longValue();
	}

	@Override
	public int getClientConnectionTimeout()
	{
		return _leafConnectionTimeOut.getCurrentValue().intValue();
	}

	@Override
	public String getDescription()
	{
		return "Network Server Configuration";
	}
}
