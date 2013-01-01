package game.gameclient;

/**
 * Interface describing the graphical user interface on this game client.
 * 
 * @author benobiwan
 * 
 */
public interface ILocalClientUI
{
	/**
	 * Create and show a game creation window for the specified
	 * {@link IClientGameCreator}.
	 * 
	 * @param gameCreator
	 *            the {@link IClientGameCreator} of the specified game.
	 */
	void createGameCreationUI(IClientGameCreator<?, ?, ?, ?, ?, ?> gameCreator);

	/**
	 * Create and show the main game UI for the specified
	 * {@link IClientSidePlayer}.
	 * 
	 * @param player
	 *            the {@link IClientSidePlayer} of the specified game.
	 */
	void createGameUI(final IClientSidePlayer<?, ?, ?, ?, ?> player);
}
