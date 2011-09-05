package game.network.ui;

import game.network.ConnectionList;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 * {@link JPanel} displaying the list of servers which are declared.
 * 
 * @author benobiwan
 * 
 */
public final class ServerListPanel extends JPanel
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -1219630136838705033L;

	/**
	 * The table model used to display the table.
	 */
	private final ServerListTableModel _tableModel;

	/**
	 * Creates a new ServerListInternalFrame.
	 * 
	 * @param connectionList
	 *            the list of all connections.
	 */
	public ServerListPanel(final ConnectionList connectionList)
	{
		super(new BorderLayout(5, 5));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		_tableModel = new ServerListTableModel(connectionList);
		final JTable tableServerList = new JTable(_tableModel);
		tableServerList.setAutoCreateRowSorter(true);
		add(new JScrollPane(tableServerList), BorderLayout.CENTER);
		add(createBottomPanel(), BorderLayout.PAGE_END);
	}

	/**
	 * Create the bottom panel of the game list panel.
	 * 
	 * @return the bottom panel of the game list panel.
	 */
	private JPanel createBottomPanel()
	{
		final JPanel bottomPanel = new JPanel(new GridLayout(1, 0, 5, 5));
		final JButton butSave = new JButton("Save");
		butSave.setToolTipText("Save values");
		// butSave.addActionListener(new SaveActionListener());
		bottomPanel.add(butSave);
		return bottomPanel;
	}
}
