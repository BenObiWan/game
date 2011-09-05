package game.gameserver;

/**
 * Implementation of the {@link IServerState} interface.
 * 
 * @author benobiwan
 * 
 */
public final class ServerStateImpl implements IServerState
{
	/**
	 * The current number of clients on the server.
	 */
	private final int _iNbClients;

	/**
	 * The maximum number of clients on the server.
	 */
	private final int _iMaxNbClients;

	/**
	 * The current number of games on the server.
	 */
	private final int _iNbGames;

	/**
	 * The maximum number of games on the server.
	 */
	private final int _iMaxNbGames;

	/**
	 * creates a new ServerStateImpl.
	 * 
	 * @param iNbClients
	 *            the current number of clients on the server.
	 * @param iMaxNbClients
	 *            the maximum number of clients on the server.
	 * @param iNbGames
	 *            the current number of games on the server.
	 * @param iMaxNbGames
	 *            the maximum number of games on the server.
	 */
	public ServerStateImpl(final int iNbClients, final int iMaxNbClients,
			final int iNbGames, final int iMaxNbGames)
	{
		super();
		_iNbClients = iNbClients;
		_iMaxNbClients = iMaxNbClients;
		_iNbGames = iNbGames;
		_iMaxNbGames = iMaxNbGames;
	}

	@Override
	public int getNbClients()
	{
		return _iNbClients;
	}

	@Override
	public int getMaxNbClients()
	{
		return _iMaxNbClients;
	}

	@Override
	public String getNbCLientsString()
	{
		return "" + getNbClients() + "/" + getMaxNbClients();
	}

	@Override
	public int getNbGames()
	{
		return _iNbGames;
	}

	@Override
	public int getMaxNbGames()
	{
		return _iMaxNbGames;
	}

	@Override
	public String getNbGamesString()
	{
		return "" + getNbGames() + "/" + getMaxNbGames();
	}

}
