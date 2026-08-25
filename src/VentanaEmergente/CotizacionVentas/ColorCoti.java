package VentanaEmergente.CotizacionVentas;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorCoti extends JTable {
    
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
    Component componente = super.prepareRenderer(renderer, rowIndex, columnIndex);

    boolean isSelected = isRowSelected(rowIndex);

    if (columnIndex == 6 && getValueAt(rowIndex, columnIndex) != null) {
        String valor = getValueAt(rowIndex, columnIndex).toString();
        Color col = new Color(0,95,230);
        Color bl = Color.white;
        componente.setForeground(bl);
        componente.setFont(new Font("Arial", Font.BOLD, 12));
        switch (valor) {
            case "Borrador":
                componente.setBackground(isSelected ? col : Color.decode("#9F00FF"));
                break;
            case "Enviado":
                componente.setBackground(isSelected ? col : Color.decode("#475FFF"));
                break;
            case "Vendido":
                componente.setBackground(isSelected ? col : Color.decode("#20FF49"));
                break;
            case "Cancelado":
                componente.setBackground(isSelected ? col : Color.decode("#FF4C4C"));
                break;
            default:
                if (!isSelected) {
                    componente.setBackground(getBackground());
                    componente.setForeground(getForeground());
                }
                break;
        }
    } else {
        if (!isSelected) {
            componente.setBackground(getBackground());
            componente.setForeground(getForeground());
        }
    }

    return componente;
}
}