package game.core.ui;

import game.common.IPlayerDescription;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel showing the description of a player.
 * 
 * @author benobiwan
 * 
 */
public final class PlayerDescriptionPanel extends JPanel
{
	/**
	 * serialVersionUID for Serialization.
	 */
	private static final long serialVersionUID = -1464627947791528432L;

	/**
	 * Description of the player.
	 */
	private final IPlayerDescription _description;

	/**
	 * Creates a new PlayerDescriptionPanel.
	 * 
	 * @param description
	 *            description of the player.
	 */
	public PlayerDescriptionPanel(final IPlayerDescription description)
	{
		super(new GridLayout(1, 0));
		_description = description;
		add(new JLabel(_description.getPlayerName()));
		final JCheckBox readyCheckBox = new JCheckBox();
		readyCheckBox.setSelected(description.isReady());
		readyCheckBox.setEnabled(false);
//		readyCheckBox.setDisabledSelectedIcon(CoreUI.VALID_ICON);
//		readyCheckBox.setDisabledIcon(CoreUI.INVALID_ICON);
		add(readyCheckBox);
	}
}
