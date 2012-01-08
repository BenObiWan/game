package game.core.swing;

import game.common.IPlayerDescription;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Logger object.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlayerListPanel.class);

	/**
	 * Panel which is actually displaying the list of players.
	 */
	private final JPanel _listPanel = new JPanel(new GridLayout(0, 1));

	/**
	 * Creates a new PlayerListPanel.
	 */
	public PlayerListPanel()
	{
		super(new BorderLayout());
		final JScrollPane scrollPaneList = new JScrollPane(_listPanel);
		add(scrollPaneList, BorderLayout.CENTER);
	}

	/**
	 * Update the displayed player list.
	 * 
	 * @param playerList
	 *            the list of players.
	 */
	public void updatePlayerList(final Set<IPlayerDescription> playerList)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("updatePlayerList");
		}
		_listPanel.removeAll();
		for (final IPlayerDescription desc : playerList)
		{
			_listPanel.add(new PlayerDescriptionPanel(desc));
		}
	}
}
