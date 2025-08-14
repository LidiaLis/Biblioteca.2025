package aplicacao;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author fabiomacz
 *
 */
public class CustomRenderer extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (value != null && value instanceof Float) {
                String formattedString = String.format("R$ %.2f", value);
                this.setText(formattedString);
                this.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return c;
    }
}