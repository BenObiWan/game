package game.network.config;

import java.net.InetSocketAddress;

import javax.management.MBeanServer;

import common.config.AbstractConfigurationListElement;
import common.config.IConfiguration;
import common.config.display.IntegerDisplayType;
import common.config.display.StringDisplayType;
import common.config.leaf.ConfigurationInteger;
import common.config.leaf.ConfigurationString;
import common.config.leaf.LockedConfigurationString;

/**
 * An implementation of the {@link INetworkClientConfigurationElement}
 * interface.
 * 
 * @author benobiwan
 * 
 */
public class NetworkClientConfigurationElementImpl extends
		AbstractConfigurationListElement<INetworkClientConfigurationElement>
		implements INetworkClientConfigurationElement
{
	/**
	 * Leaf configuring the server name.
	 */
	private final ConfigurationString _leafServerName;

	/**
	 * Leaf configuring the server remote address.
	 */
	private final ConfigurationString _leafRemoteAddress;

	/**
	 * Leaf configuring the server remote port.
	 */
	private final ConfigurationInteger _leafRemotePort;

	/**
	 * Leaf configuring the client name on this server.
	 */
	private final ConfigurationString _leafClientName;

	/**
	 * Leaf configuring the client auth on this server.
	 */
	private final ConfigurationString _leafClientAuth;

	/**
	 * Creates a new NetworkClientConfigurationElementImpl using default values
	 * for every element except the name.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param strName
	 *            the name of the server.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public NetworkClientConfigurationElementImpl(final IConfiguration parent,
			final String strName, final MBeanServer mBeanServer)
	{
		super(parent, strName, mBeanServer);
		_leafServerName = new LockedConfigurationString(this, SERVER_NAME_TAG,
				"Server name", "Server name.", "Invalid server name", false,
				StringDisplayType.TEXTFIELD, 0, strName);
		_leafRemoteAddress = new ConfigurationString(this, REMOTE_ADDRESS_TAG,
				"Remote address", "Server remote address.",
				"Invalid remote address", false, StringDisplayType.TEXTFIELD,
				0, "127.0.0.1");
		_leafRemotePort = new ConfigurationInteger(this, REMOTE_PORT_TAG,
				"RemotePort", "Remote port", "Remote server port.", false,
				IntegerDisplayType.SPINNER, Integer.valueOf(1024),
				Integer.valueOf(65535), Integer.valueOf(1664));
		_leafClientName = new ConfigurationString(this, CLIENT_NAME_TAG,
				"Client name", "Client name on this server.",
				"Invalid client name", false, StringDisplayType.TEXTFIELD, 40,
				"Anonymous");
		_leafClientAuth = new ConfigurationString(this, CLIENT_AUTH_TAG,
				"Client auth", "Client auth on this server.",
				"Invalid client auth", false, StringDisplayType.TEXTFIELD, 40,
				"");
		addLeaf(_leafServerName);
		addLeaf(_leafRemoteAddress);
		addLeaf(_leafRemotePort);
		addLeaf(_leafClientName);
		addLeaf(_leafClientAuth);
	}

	@Override
	public String getServerName()
	{
		return _leafServerName.getCurrentValue();
	}

	@Override
	public InetSocketAddress getRemoteAddress()
	{
		if (_leafRemoteAddress.getCurrentValue() == null)
		{
			return null;
		}
		return new InetSocketAddress(_leafRemoteAddress.getCurrentValue(),
				_leafRemotePort.getCurrentValue().intValue());
	}

	@Override
	public String getClientName()
	{
		return _leafClientName.getCurrentValue();
	}

	@Override
	public String getClientAuth()
	{
		return _leafClientAuth.getCurrentValue();
	}

	@Override
	public String getDescription()
	{
		return "Network Client Configuration Element";
	}

	@Override
	public int compareTo(final INetworkClientConfigurationElement o)
	{
		return _strName.compareTo(o.getName());
	}
}
