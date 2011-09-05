package game.core.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 * {@link JPanel} displaying the list of games on the different servers.
 * 
 * @author benobiwan
 * 
 */
public final class GameListPanel extends JPanel
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -1219630136838705033L;

	/**
	 * The table model used to display the table.
	 */
	private final GameListTableModel _tableModel;

	/**
	 * Creates a new GameListInternalFrame.
	 */
	public GameListPanel()
	{
		super(new BorderLayout(5, 5));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		_tableModel = new GameListTableModel();
		final JTable tableGameList = new JTable(_tableModel);
		tableGameList.setAutoCreateRowSorter(true);
		add(new JScrollPane(tableGameList), BorderLayout.CENTER);
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
		final JButton butSave = new JButton("new game");
		butSave.setToolTipText("Create a new game");
		// butSave.addActionListener(new SaveActionListener());
		bottomPanel.add(butSave);
		return bottomPanel;
	}
}
