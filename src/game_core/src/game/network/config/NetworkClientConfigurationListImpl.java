package game.network.config;

import javax.management.MBeanServer;

import common.config.AbstractConfigurationList;
import common.config.IConfiguration;
import common.config.display.IntegerDisplayType;
import common.config.leaf.ConfigurationInteger;

/**
 * An implementation of the {@link INetworkClientConfigurationList} interface.
 * 
 * @author benobiwan
 * 
 */
public class NetworkClientConfigurationListImpl extends
		AbstractConfigurationList<INetworkClientConfigurationElement> implements
		INetworkClientConfigurationList
{
	/**
	 * Leaf configuring the number of connection retry.
	 */
	private final ConfigurationInteger _leafNumberOfConnectionRetry;

	/**
	 * Creates a new NetworkClientConfigurationListImpl using default values for
	 * every element.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public NetworkClientConfigurationListImpl(final IConfiguration parent,
			final MBeanServer mBeanServer)
	{
		super(parent, NETWORK_CLIENT_CONFIGURATION_LIST_TAG, mBeanServer);
		_leafNumberOfConnectionRetry = new ConfigurationInteger(this,
				NUMBER_OF_CONNNECTION_RETRY_TAG, "NumberOfConnectionRetry",
				"Number of connection retry", "Number of connection retry.",
				false, IntegerDisplayType.SPINNER, Integer.valueOf(0),
				Integer.valueOf(10), Integer.valueOf(3));
		addLeaf(_leafNumberOfConnectionRetry);
	}

	@Override
	public int getNumberOfConnectionRetry()
	{
		return _leafNumberOfConnectionRetry.getCurrentValue().intValue();
	}

	@Override
	public String getDescription()
	{
		return "Network Client Configuration List";
	}

	// TODO code of NetworkClientConfigurationListImpl
}
