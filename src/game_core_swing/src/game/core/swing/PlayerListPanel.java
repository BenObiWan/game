package game.core.swing;

import game.common.IPlayerDescription;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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
		final JButton buttonAddAI = new JButton("Add AI");
		buttonAddAI.addActionListener(new AddAIActionListener());
		final JPanel panelButton = new JPanel(new BorderLayout());
		panelButton.add(buttonAddAI, BorderLayout.CENTER);
		panelButton.setBorder(new EmptyBorder(5, 15, 5, 15));
		add(panelButton, BorderLayout.PAGE_END);
	}

	/**
	 * Update the displayed player list.
	 * 
	 * @param playerList
	 *            the list of players.
	 */
	public void updatePlayerList(final Set<IPlayerDescription> playerList)
	{
		_listPanel.removeAll();
		for (final IPlayerDescription desc : playerList)
		{
			_listPanel.add(new PlayerDescriptionPanel(desc));
		}
	}

	/**
	 * {@link ActionListener} for the Add AI {@link JButton}.
	 * 
	 * @author benobiwan
	 * 
	 */
	private final class AddAIActionListener implements ActionListener
	{
		/**
		 * Creates a new AddAIActionListener.
		 */
		public AddAIActionListener()
		{
			super();
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			// TODO add an AI
		}
	}
}
