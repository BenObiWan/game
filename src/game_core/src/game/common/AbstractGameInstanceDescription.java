package game.common;

/**
 * Abstract implementation of the @{link IGameInstanceDescription} interface.
 * 
 * @author benobiwan
 * 
 */
public abstract class AbstractGameInstanceDescription implements
		IGameInstanceDescription
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 1857120763244522086L;

	/**
	 * Id of the game.
	 */
	protected final int _iGameId;

	/**
	 * Name of the game creator.
	 */
	protected final String _strCreatorName;

	/**
	 * Number of players of the game.
	 */
	private int _iNumberOfPlayer;

	/**
	 * Lock for the number of players.
	 */
	private final Object _lockNumberOfPlayer = new Object();

	/**
	 * Creates a new AbstractGameDescription.
	 * 
	 * @param iGameId
	 *            the id of the game.
	 * @param strCreatorName
	 *            the name of the game creator.
	 * @param iNumberOfPlayer
	 *            the current number of player of this game.
	 */
	protected AbstractGameInstanceDescription(final int iGameId,
			final String strCreatorName, final int iNumberOfPlayer)
	{
		_iGameId = iGameId;
		_strCreatorName = strCreatorName;
		synchronized (_lockNumberOfPlayer)
		{
			_iNumberOfPlayer = iNumberOfPlayer;
		}
	}

	@Override
	public final int compareTo(final IGameInstanceDescription o)
	{
		return _iGameId - o.getGameId();
	}

	@Override
	public final int getGameId()
	{
		return _iGameId;
	}

	@Override
	public final String getGameCreatorName()
	{
		return _strCreatorName;
	}

	@Override
	public final int getCurrentNumberOfPlayer()
	{
		synchronized (_lockNumberOfPlayer)
		{
			return _iNumberOfPlayer;
		}
	}

	@Override
	public void setNumberOfPlayer(final int iNumberOfPlayer)
	{
		synchronized (_lockNumberOfPlayer)
		{
			_iNumberOfPlayer = iNumberOfPlayer;
		}
	}
}
