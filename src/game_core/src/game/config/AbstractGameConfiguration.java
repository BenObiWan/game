package game.config;

import javax.management.MBeanServer;

import common.config.AbstractConfigurationNode;
import common.config.IConfiguration;

/**
 * An abstract implementation of {@link IGameConfiguration}.
 * 
 * @author benobiwan
 * 
 * @param <PLAYER_CONF>
 *            the type of player configuration for this game.
 */
public abstract class AbstractGameConfiguration<PLAYER_CONF extends IPlayerConfiguration>
		extends AbstractConfigurationNode implements
		IGameConfiguration<PLAYER_CONF>
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -6834555163953149910L;

	/**
	 * Creates a new {@link AbstractGameConfiguration}.
	 * 
	 * @param parent
	 *            the parent of this AbstractConfigurationNode.
	 * @param strName
	 *            the name of this AbstractConfigurationNode.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public AbstractGameConfiguration(final IConfiguration parent,
			final String strName, final MBeanServer mBeanServer)
	{
		super(parent, strName, mBeanServer);
	}
}
