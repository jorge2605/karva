package Modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TablaVerRequisiciones extends JTable {

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex) {
        Component componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);

        if (getColumnCount() > 5) {
            if (getValueAt(rowIndex, 5) != null) {
                if (getValueAt(rowIndex, 5).getClass().equals(String.class)) {
                    String valor = (this.getValueAt(rowIndex, 5).toString());
                    if (valor.equals("COTIZADO")) {
                        componente.setForeground(Color.black);
                        componente.setFont(new Font("Roboto", Font.BOLD, 12));
                    }
                }
            }
        }
        return componente;
    }
}
