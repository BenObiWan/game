package game.launcher.config;

import common.config.IConfigurationBranch;

/**
 * Configuration for the launcher of the program.
 * 
 * @author benobiwan
 * 
 */
public interface ILauncherConfiguration extends IConfigurationBranch
{
	/**
	 * Tag of this configuration node.
	 */
	String LAUNCHER_CONFIGURATION_TAG = "launcher";

	/**
	 * Tag for the boolean toggling whether this application is a daemon server
	 * or has a GUI.
	 */
	String IS_DAEMON_TAG = "IsServer";

	/**
	 * Get whether this application is a daemon server or has a GUI.
	 * 
	 * @return true if this application is a daemon server
	 */
	boolean isDaemon();
}
