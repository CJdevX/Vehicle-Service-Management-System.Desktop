package TableActionButton.Appointment;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author chala
 */
public class appointmentTableActionCellEditor extends DefaultCellEditor {
    
    private JPanel panel;
    
    public appointmentTableActionCellEditor(JPanel panel) {
        super(new JCheckBox());
        this.panel = panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        appointmentPanelAction action = new appointmentPanelAction(table, panel);
        action.setBackground(table.getSelectionBackground());
        
        return action;
    }
    
    
}
