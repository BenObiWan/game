package game.core.swing;

import game.common.IGameDescription;
import game.gameclient.IClientGameCreator;
import game.gameserver.IServerGameCreator;

import javax.swing.JInternalFrame;

/**
 * Interface used on the client side to initialize the graphical component of a
 * game.
 * 
 * @author benobiwan
 * 
 */
public interface IGameSwingLauncher extends Comparable<IGameSwingLauncher>
{
	/**
	 * Create an {@link IServerGameCreator} for this game.
	 * 
	 * @return the newly created {@link IServerGameCreator}.
	 */
	IServerGameCreator<?, ?, ?, ?, ?> createServerGameCreator();

	/**
	 * Create the main frame to play this game.
	 * 
	 * @param creator
	 *            the {@link IClientGameCreator} holding the parameters of the
	 *            game.
	 * @return the {@link IClientGameCreator} holding the parameters of the
	 *         game.
	 */
	JInternalFrame createGameUI(IClientGameCreator<?, ?, ?, ?, ?, ?> creator);

	/**
	 * Get the {@link IGameDescription} of this game.
	 * 
	 * @return the {@link IGameDescription} of this game.
	 */
	IGameDescription getGameListDescription();

	/**
	 * Get the {@link Class} of the {@link IClientGameCreator} associated with
	 * this game.
	 * 
	 * @return the {@link Class} of the {@link IClientGameCreator} associated
	 *         with this game.
	 */
	Class<? extends IClientGameCreator<?, ?, ?, ?, ?, ?>> getIClientGameCreatorClass();
}
