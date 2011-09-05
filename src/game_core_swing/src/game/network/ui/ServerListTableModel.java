package game.network.ui;

import game.communication.IGameServer;
import game.network.ConnectionList;

import java.net.SocketAddress;

import javax.swing.table.AbstractTableModel;

/**
 * Table model for the server list.
 * 
 * @author benobiwan
 * 
 */
public final class ServerListTableModel extends AbstractTableModel
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -5670750811021439741L;

	/**
	 * Name of the columns.
	 */
	private static final String[] COLUMN_NAMES = { "Server Name", "Address",
			"Name on the server", "Connection", "Nbr Clients", "Nbr Games" };

	/**
	 * List of all connections.
	 */
	public final ConnectionList _connectionList;

	/**
	 * creates a new ServerListTableModel.
	 * 
	 * @param connectionList
	 *            list of all connections.
	 */
	public ServerListTableModel(final ConnectionList connectionList)
	{
		_connectionList = connectionList;
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
		return _connectionList.getNumberOfServers();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		final IGameServer server = _connectionList.getServerAtIndex(rowIndex);
		switch (columnIndex)
		{
		case 0:// Server Name
			return server.getName();
		case 1:// Address
			return server.getRemoteAddress();
		case 2:// Name on the server
			return server.getClientName();
		case 3:// Connection
			if (server.isConnected())
			{
				return "Connected";
			}
			else if (server.isConnecting())
			{
				return "Connecting";
			}
			return "Disconnected";
		case 4:// Nbr Client
			if (server.isConnected())
			{
				return server.getServerState().getNbCLientsString();
			}
			return "?/?";
		case 5:// Nbr Games
			if (server.isConnected())
			{
				return server.getServerState().getNbGamesString();
			}
			return "?/?";
		default:
			return super.getColumnClass(columnIndex);
		}
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex)
	{
		switch (columnIndex)
		{
		case 0:
			return String.class;
		case 1:
			return SocketAddress.class;
		case 2:
			return String.class;
		case 3:
			// TODO something with 3 states
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;
		default:
			return super.getColumnClass(columnIndex);
		}
	}
}
