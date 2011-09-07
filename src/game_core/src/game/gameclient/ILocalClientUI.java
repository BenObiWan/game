package game.gameclient;

public interface ILocalClientUI
{
	/**
	 * Create and show a game creation window for the specified
	 * {@link IClientGameCreator}.
	 * 
	 * @param gameCreator
	 *            the {@link IClientGameCreator} of the specified game.
	 * @param bCreator
	 *            whether the local client is the creator of this game or not.
	 */
	void createGameCreationUI(IClientGameCreator<?, ?, ?, ?, ?, ?> gameCreator,
			boolean bCreator);

	/**
	 * Create and show the main game UI for the specified
	 * {@link IClientGameCreator}.
	 * 
	 * @param gameCreator
	 *            the {@link IClientGameCreator} of the specified game.
	 */
	void createGameUI(IClientGameCreator<?, ?, ?, ?, ?, ?> gameCreator);
}
