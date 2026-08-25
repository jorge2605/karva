package pruebas;
import VentanaEmergente.Compras.ConfigCompras;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorCompras extends JTable {
    
    public String numEmpleado = "";
    public ConfigCompras config;
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
        Component componente = super.prepareRenderer(renderer, rowIndex, columnIndex);

        if (columnIndex == 0 && getValueAt(rowIndex, columnIndex) != null) {
            String valor = getValueAt(rowIndex, columnIndex).toString();
            switch (valor) {
                case "NUEVO":
                    componente.setBackground(Color.WHITE);
                    componente.setForeground(Color.BLACK);
                    break;
                case "LLEGO, INCOMPETO":
                    componente.setBackground(Color.yellow);
                    componente.setForeground(Color.BLACK);
                    break;
                case "COTIZANDO":
                    componente.setBackground(Color.decode("#ff0000"));
                    componente.setForeground(Color.BLACK);
                    break;
                case "APROBADO":
                        componente.setBackground(Color.green);
                        componente.setForeground(Color.BLACK);
                    break;
                case "RECHAZADO":
                    componente.setBackground(Color.decode("#ff0000"));
                    componente.setForeground(Color.BLACK);
                    break;
                case "COMPRADO":
                        componente.setBackground(Color.decode("#00a2ff"));
                        componente.setForeground(Color.WHITE);
                    break;
                case "RECIBIDO":
                    componente.setBackground(Color.decode("#4f9234"));
                    componente.setForeground(Color.BLACK);
                    break;
                case "ORDENES":
                    componente.setBackground(Color.white);
                    componente.setForeground(Color.BLACK);
                    break;
                case "VISTO":
                    componente.setBackground(Color.WHITE);
                    componente.setForeground(Color.BLACK);
                    componente.setFont(new Font("Roboto", Font.BOLD, 14));
                    break;
                default:
                    break;
            }

            // Cambiar el color del borde de la fila
        }
        return componente;
    }
}