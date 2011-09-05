package game.network.config;

import javax.management.MBeanServer;

import common.config.AbstractConfigurationBranch;
import common.config.IConfiguration;
import common.config.InvalidConfigurationException;
import common.config.display.EnumDisplayType;
import common.config.display.IntegerDisplayType;
import common.config.leaf.ConfigurationEnum;
import common.config.leaf.ConfigurationInteger;

/**
 * An implementation of the {@link INetworkConfiguration} interface.
 * 
 * @author benobiwan
 * 
 */
public class NetworkConfigurationImpl extends AbstractConfigurationBranch
		implements INetworkConfiguration
{
	/**
	 * Leaf configuring the period between KeepAlive requests.
	 */
	private final ConfigurationInteger _leafKeepAliveRequestInterval;

	/**
	 * Leaf configuring the KeepAlive request timeout before closing the
	 * connection.
	 */
	private final ConfigurationInteger _leafKeepAliveRequestTimeout;

	/**
	 * Leaf configuring the registration type available to the clients.
	 */
	private final ConfigurationEnum<RegistrationType> _leafRegistrationType;

	/**
	 * Creates a new NetworkConfigurationImpl using default values for every
	 * elements.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public NetworkConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer)
	{
		super(parent, NETWORK_CONFIGURATION_TAG, mBeanServer);
		_leafKeepAliveRequestInterval = new ConfigurationInteger(this,
				KEEP_ALIVE_REQUEST_INTERVAL_TAG, "KeepAlive request interval",
				"Period between KeepAlive requests.",
				"Invalid Period between KeepAlive requests", true,
				IntegerDisplayType.SPINNER, Integer.valueOf(30),
				Integer.valueOf(240), Integer.valueOf(60));
		_leafKeepAliveRequestTimeout = new ConfigurationInteger(this,
				KEEP_ALIVE_REQUEST_TIMEOUT_TAG, "KeepAlive request timeout",
				"KeepAlive request timeout before closing the connection.",
				"Invalid KeepAlive request timeout", true,
				IntegerDisplayType.SPINNER, Integer.valueOf(10),
				Integer.valueOf(120), Integer.valueOf(30));
		_leafRegistrationType = new ConfigurationEnum<RegistrationType>(this,
				REGISTRATION_TYPE_TAG, "Registration type",
				"Registration type available to the clients.",
				"Invalid Registration type", true, EnumDisplayType.COMBOBOX,
				RegistrationType.NONE);
		addLeaf(_leafKeepAliveRequestInterval);
		addLeaf(_leafKeepAliveRequestTimeout);
		addLeaf(_leafRegistrationType);
	}

	/**
	 * Creates a new NetworkConfigurationImpl and sets the command line values.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param iCommandLineKeepAliveRequestInterval
	 *            the value specified on the command line for the period between
	 *            KeepAlive requests.
	 * @param iCommandLineKeepAliveRequestTimeout
	 *            the value specified on the command line for the KeepAlive
	 *            request timeout before closing the connection.
	 * @param eCommandLineRegistrationType
	 *            the value specified on the command line for the registration
	 *            type available to the clients.
	 * @throws InvalidConfigurationException
	 *             one of the given value is invalid.
	 */
	public NetworkConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer,
			final Integer iCommandLineKeepAliveRequestInterval,
			final Integer iCommandLineKeepAliveRequestTimeout,
			final RegistrationType eCommandLineRegistrationType)
			throws InvalidConfigurationException
	{
		super(parent, NETWORK_CONFIGURATION_TAG, mBeanServer);
		_leafKeepAliveRequestInterval = new ConfigurationInteger(this,
				KEEP_ALIVE_REQUEST_INTERVAL_TAG, "KeepAlive request interval",
				"Period beetween KeepAlive requests.",
				"Invalid Period beetween KeepAlive requests", true,
				IntegerDisplayType.SPINNER, Integer.valueOf(30),
				Integer.valueOf(240), Integer.valueOf(60),
				iCommandLineKeepAliveRequestInterval);
		_leafKeepAliveRequestTimeout = new ConfigurationInteger(this,
				KEEP_ALIVE_REQUEST_TIMEOUT_TAG, "KeepAlive request timeout",
				"KeepAlive request timeout before closing the connection.",
				"Invalid KeepAlive request timeout", true,
				IntegerDisplayType.SPINNER, Integer.valueOf(10),
				Integer.valueOf(120), Integer.valueOf(30),
				iCommandLineKeepAliveRequestTimeout);
		_leafRegistrationType = new ConfigurationEnum<RegistrationType>(this,
				REGISTRATION_TYPE_TAG, "Registration type",
				"Registration type available to the clients.",
				"Invalid Registration type", true, EnumDisplayType.COMBOBOX,
				RegistrationType.NONE, eCommandLineRegistrationType);
		addLeaf(_leafKeepAliveRequestInterval);
		addLeaf(_leafKeepAliveRequestTimeout);
		addLeaf(_leafRegistrationType);
	}

	/**
	 * Creates a new NetworkConfigurationImpl and sets the command line and
	 * configuration values.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 * @param iCommandLineKeepAliveRequestInterval
	 *            the value specified on the command line for the period between
	 *            KeepAlive requests.
	 * @param iCommandLineKeepAliveRequestTimeout
	 *            the value specified on the command line for the KeepAlive
	 *            request timeout before closing the connection.
	 * @param eCommandLineRegistrationType
	 *            the value specified on the command line for the registration
	 *            type available to the clients.
	 * @param iConfigurationKeepAliveRequestInterval
	 *            the value specified in the configuration file for the period
	 *            between KeepAlive requests.
	 * @param iConfigurationKeepAliveRequestTimeout
	 *            the value specified in the configuration file for the
	 *            KeepAlive request timeout before closing the connection.
	 * @param eConfigurationRegistrationType
	 *            the value specified in the configuration file for the
	 *            registration type available to the clients.
	 * @throws InvalidConfigurationException
	 *             one of the given value is invalid.
	 */
	public NetworkConfigurationImpl(final IConfiguration parent,
			final MBeanServer mBeanServer,
			final Integer iCommandLineKeepAliveRequestInterval,
			final Integer iCommandLineKeepAliveRequestTimeout,
			final RegistrationType eCommandLineRegistrationType,
			final Integer iConfigurationKeepAliveRequestInterval,
			final Integer iConfigurationKeepAliveRequestTimeout,
			final RegistrationType eConfigurationRegistrationType)
			throws InvalidConfigurationException
	{
		this(parent, mBeanServer, iCommandLineKeepAliveRequestInterval,
				iCommandLineKeepAliveRequestTimeout,
				eCommandLineRegistrationType);
		_leafKeepAliveRequestInterval
				.setConfigurationValue(iConfigurationKeepAliveRequestInterval);
		_leafKeepAliveRequestTimeout
				.setConfigurationValue(iConfigurationKeepAliveRequestTimeout);
		_leafRegistrationType
				.setConfigurationValue(eConfigurationRegistrationType);
	}

	@Override
	public int getKeepAliveRequestTimeout()
	{
		return _leafKeepAliveRequestTimeout.getCurrentValue().intValue();
	}

	@Override
	public int getKeepAliveRequestInterval()
	{
		return _leafKeepAliveRequestInterval.getCurrentValue().intValue();
	}

	@Override
	public RegistrationType getRegistrationType()
	{
		return _leafRegistrationType.getCurrentValue();
	}

	@Override
	public String getDescription()
	{
		return "Network Configuration";
	}
}
