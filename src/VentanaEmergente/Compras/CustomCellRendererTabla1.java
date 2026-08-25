package VentanaEmergente.Compras;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRendererTabla1 extends DefaultTableCellRenderer{
    private final Border selectedBorder = BorderFactory.createLineBorder(new Color(153,0,0), 3);
    private final Border emptyBorder = null;
    @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Si la celda está seleccionada, resaltamos más el fondo
            if(row == table.getSelectedRow() && column == table.getSelectedColumn()){
                if (isSelected) {
                    setBorder(selectedBorder);
                }
            } else {
                    setBorder(emptyBorder); // Restaurar el borde predeterminado
                }

            return component;
        }
    
}
