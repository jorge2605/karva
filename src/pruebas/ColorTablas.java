package pruebas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ColorTablas extends JTable {
   
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        Component componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        
       
        
        if(rowIndex%2 == 0)
        {
        componente.setBackground(Color.decode("#239AE9"));
        componente.setForeground(Color.black);
        }
        
        return componente;
    
    }
}    

