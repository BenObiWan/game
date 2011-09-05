package game.config;

import javax.management.MBeanServer;

import common.config.AbstractConfigurationBranch;
import common.config.IConfiguration;

/**
 * An implementation of the {@link IPlayerConfiguration} for game which don't
 * have any specific configuration for the player.
 * 
 * @author benobiwan
 * 
 */
public final class EmptyPlayerConfiguration extends AbstractConfigurationBranch
		implements IPlayerConfiguration
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 158877584999091516L;

	/**
	 * Creates a new EmptyPlayerConfiguration.
	 * 
	 * @param parent
	 *            the parent configuration.
	 * @param mBeanServer
	 *            the {@link MBeanServer} to use.
	 */
	public EmptyPlayerConfiguration(final IConfiguration parent,
			final MBeanServer mBeanServer)
	{
		super(parent, "Empty Player Configuration", mBeanServer);
	}

	@Override
	public String getDescription()
	{
		return "Empty Player Configuration";
	}
}
