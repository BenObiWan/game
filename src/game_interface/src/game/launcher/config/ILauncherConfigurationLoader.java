package game.launcher.config;

import common.config.IConfigurationLoader;

/**
 * Interface describing a loader for the {@link ILauncherConfiguration}.
 * 
 * @author benobiwan
 * 
 */
public interface ILauncherConfigurationLoader extends IConfigurationLoader
{
	/**
	 * Prefix for the launcher configuration.
	 */
	String LAUNCHER_CONF_PREFIX = "launcher";

	/**
	 * Get the {@link ILauncherConfiguration}.
	 * 
	 * @return the {@link ILauncherConfiguration}.
	 */
	ILauncherConfiguration getLauncherConfiguration();
}
