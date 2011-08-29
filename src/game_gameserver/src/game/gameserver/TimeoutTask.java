package game.gameserver;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

/**
 * {@link TimerTask} used to control if the timeout on the turn of the current
 * player has been reached.
 * 
 * @author benobiwan
 * 
 */
public final class TimeoutTask extends TimerTask
{
	/**
	 * Executor service to execute the timeout tasks.
	 */
	private final ExecutorService _timeOutExecutor;

	/**
	 * Delay before executing this timeout task.
	 */
	private final int _iDelay;

	/**
	 * Game to which this timeout task is related.
	 */
	protected final IServerSideGame<?, ?, ?, ?> _game;

	/**
	 * The acting player.
	 */
	protected final IServerSidePlayer<?> _player;

	/**
	 * 
	 * @param timeOutExecutor
	 *            executor service to execute the timeout tasks.
	 * @param iDelay
	 *            delay before executing this timeout task.
	 * @param game
	 *            game to which this timeout task is related.
	 * @param player
	 *            the acting player.
	 */
	public TimeoutTask(final ExecutorService timeOutExecutor, final int iDelay,
			final IServerSideGame<?, ?, ?, ?> game,
			final IServerSidePlayer<?> player)
	{
		_iDelay = iDelay;
		_game = game;
		_timeOutExecutor = timeOutExecutor;
		_player = player;
	}

	/**
	 * Get the delay before executing this timeout task.
	 * 
	 * @return the delay before executing this timeout task.
	 */
	public int getDelay()
	{
		return _iDelay;
	}

	@Override
	public void run()
	{
		_timeOutExecutor.execute(new Runnable()
		{
			@Override
			public void run()
			{
				_game.timeoutReached();
			}
		});
	}
}
