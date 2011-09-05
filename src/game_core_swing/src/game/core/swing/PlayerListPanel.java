package game.core.swing;

import game.common.IPlayerDescription;

import java.awt.GridLayout;
import java.util.SortedSet;

import javax.swing.JPanel;

/**
 * Panel used to display the list of players in the {@link GameCreationPanel}.
 * 
 * @author benobiwan
 * 
 */
public final class PlayerListPanel extends JPanel
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = 3227916860832967756L;

	/**
	 * Creates a new PlayerListPanel.
	 */
	public PlayerListPanel()
	{
		super(new GridLayout(0, 1));
	}

	/**
	 * Update the displayed player list.
	 * 
	 * @param playerList
	 *            the list of players.
	 */
	public void updatePlayerList(final SortedSet<IPlayerDescription> playerList)
	{

	}
}
