package Modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TablaHoras extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Llamar al método padre para obtener el componente predeterminado
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Personalizar el color de fondo de las filas pares e impares
            if (row % 5 == 0) {
                comp.setFont(new Font("Roboto",Font.BOLD, 16));
            }else{
                comp.setFont(new Font("Roboto",Font.PLAIN, 14));
            }

            // Personalizar el color de texto de las celdas seleccionadas
            if (isSelected) {
                comp.setForeground(Color.WHITE);
                comp.setBackground(new Color(0,102,255));
            } else {
                comp.setForeground(Color.BLACK);
                comp.setBackground(Color.WHITE);
            }

            return comp;
        }
    
    
}
