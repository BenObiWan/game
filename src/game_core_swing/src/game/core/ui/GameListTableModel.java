package game.core.ui;

import javax.swing.table.AbstractTableModel;

/**
 * Table model for the game list.
 * 
 * @author benobiwan
 * 
 */
public final class GameListTableModel extends AbstractTableModel
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -5670750811021439741L;

	/**
	 * Name of the columns.
	 */
	private static final String[] COLUMN_NAMES = { "Server Name",
			"Creator player", "Game type", "Number of players" };

	/**
	 * creates a new GameListTableModel.
	 */
	public GameListTableModel()
	{

	}

	@Override
	public String getColumnName(final int col)
	{
		return COLUMN_NAMES[col];
	}

	@Override
	public int getColumnCount()
	{
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount()
	{
		return 0;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		switch (columnIndex)
		{
		case 0:// Server Name
		case 1:// Creator player
		case 2:// Game type
		case 3:// Number of players
		default:
			return super.getColumnClass(columnIndex);
		}
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex)
	{
		return String.class;
	}
}
