package VentanaEmergente.Costos;

import pruebas.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TablaPrincipalCostos extends JTable {
   
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        
        Component componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        
        if(ColumnIndex == 9 || ColumnIndex == 10){
            componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
            double ganancia = 0;
            try{
                ganancia = Double.parseDouble(getValueAt(rowIndex, ColumnIndex).toString().replace(",", ""));
            }catch(Exception e){}
            if(ganancia > 0){
                componente.setFont(new Font("Roboto",Font.BOLD,14));
                componente.setForeground(Color.green);
            } else {
                componente.setFont(new Font("Roboto",Font.BOLD,14));
                componente.setForeground(Color.red);
            }
            return componente;
        }else{
            componente.setForeground(Color.black);
            return componente;
        }
        
    }
    
}
