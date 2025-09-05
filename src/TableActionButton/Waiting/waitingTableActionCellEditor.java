package TableActionButton.Waiting;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author chala
 */
public class waitingTableActionCellEditor extends DefaultCellEditor {

    private JPanel panel;
         
    
    public waitingTableActionCellEditor(JPanel panel) {
        super(new JCheckBox());
        this.panel = panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        waitingPanelAction action = new waitingPanelAction(table, panel);
        action.setBackground(table.getSelectionBackground());

        return action;
    }

}
