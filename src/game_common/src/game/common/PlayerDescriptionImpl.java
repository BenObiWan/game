package game.common;

/**
 * Object used to describe a player in a game.
 * 
 * @author benobiwan
 * 
 */
public final class PlayerDescriptionImpl implements IPlayerDescription
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 4892513521987947975L;

	/**
	 * The name of the player.
	 */
	private final String _strPlayerName;

	/**
	 * Boolean telling whether the client is an AI or not.
	 */
	private final boolean _bIsAI;

	/**
	 * Lock protecting the isReady boolean.
	 */
	private final Object _lock = new Object();

	/**
	 * Boolean telling whether this player is ready or not.
	 */
	private boolean _bIsReady = false;

	/**
	 * Creates a new PlayerDescription.
	 * 
	 * @param strPlayerName
	 *            the name of the player.
	 * @param bIsAI
	 *            whether this player is an AI or not.
	 */
	public PlayerDescriptionImpl(final String strPlayerName, final boolean bIsAI)
	{
		_strPlayerName = strPlayerName;
		_bIsAI = bIsAI;
	}

	@Override
	public boolean isReady()
	{
		synchronized (_lock)
		{
			return _bIsReady;
		}
	}

	@Override
	public void setReady(final boolean bIsReady)
	{
		synchronized (_lock)
		{
			_bIsReady = bIsReady;
		}
	}

	@Override
	public String getPlayerName()
	{
		return _strPlayerName;
	}

	@Override
	public boolean isAI()
	{
		return _bIsAI;
	}

	@Override
	public int compareTo(final IPlayerDescription o)
	{
		return _strPlayerName.compareTo(o.getPlayerName());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (_bIsAI ? 1231 : 1237);
		result = prime * result
				+ ((_strPlayerName == null) ? 0 : _strPlayerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final PlayerDescriptionImpl other = (PlayerDescriptionImpl) obj;
		if (_bIsAI != other._bIsAI)
		{
			return false;
		}
		if (_strPlayerName == null)
		{
			if (other._strPlayerName != null)
			{
				return false;
			}
		}
		else if (!_strPlayerName.equals(other._strPlayerName))
		{
			return false;
		}
		return true;
	}
}
