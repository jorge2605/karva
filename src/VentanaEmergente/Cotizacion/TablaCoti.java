package VentanaEmergente.Cotizacion;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TablaCoti extends JTable {

    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
        Component componente = super.prepareRenderer(renderer, rowIndex, columnIndex);

        boolean isSelected = isRowSelected(rowIndex);
        Color col = new Color(51, 153, 255);
        componente.setBackground(isSelected ? col : getBackground());
        if (rowIndex % 2 == 0) {
            componente.setBackground(isSelected ? col : Color.decode("#F2F2F2"));
        } else {
            if (!isSelected) {
                componente.setBackground(getBackground());
                componente.setForeground(getForeground());
            }
        }

        return componente;
    }
}
