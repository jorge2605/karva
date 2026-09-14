package VentanaEmergente.Cotizacion;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class AutoCompleteCellEditor extends DefaultCellEditor {

    private final JTextField textField;
    private TextAutoCompleter completer;

    private void agregarNP(String descripcion, int fila, JTable tab) {
        String sql = "select * from items_cotizacion where codigo like '" + descripcion + "'";
        try (Connection conn = new Conexion().getConnection(); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                tab.setValueAt(rs.getString("precio"), fila, 4);
                tab.setValueAt(rs.getString("descripcion"), fila, 2);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public AutoCompleteCellEditor(ArrayList datos) {
        super(new JTextField());
        textField = (JTextField) getComponent();
        completer = new TextAutoCompleter(textField, datos);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int col) {
        textField.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {

                if (table != null) {
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    agregarNP(table.getValueAt(row, 1).toString(), row, table);
                    if (row == model.getRowCount() - 1) {
                        model.addRow(new Object[model.getColumnCount()]);
                    }
                }
            });
        });
        textField.setText(value != null ? value.toString() : "");
        return textField;
    }
}
