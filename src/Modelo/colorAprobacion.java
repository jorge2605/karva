package Modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class colorAprobacion extends JTable {
   
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        
        Component componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        
        if(ColumnIndex < 6){
            componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
                if(getValueAt(rowIndex, 0).toString().equals("Calculos:")){
                    componente.setFont(new Font("Roboto",Font.BOLD,16));
                }
            return componente;
        }else{
            componente.setForeground(Color.black);
            return componente;
        }
        
    }
    
}