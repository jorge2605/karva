package pruebas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ColorRojo extends JTable {
   
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        
        Component componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        
        if(ColumnIndex == 0){
            componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
                if(getValueAt(rowIndex, 0).getClass().equals(String.class)){

                    String valor = (this.getValueAt(rowIndex, 0).toString());
                    int a = Integer.parseInt(valor);
                    if(a <= 30)
                    {
                    componente.setBackground(Color.decode("#1D7FF5"));
                    componente.setForeground(Color.white);
                    componente.setFont(new Font("Roboto",Font.BOLD,12));

                    }
                    else if(a > 30 && a <= 60){
                    componente.setBackground(Color.decode("#FAED19"));
                    componente.setForeground(Color.white);
                    componente.setFont(new Font("Roboto",Font.BOLD,12));
                    } 
                    else if(a > 60 && a <= 100){
                    componente.setBackground(Color.decode("#F05F00"));
                    componente.setForeground(Color.white);
                    componente.setFont(new Font("Roboto",Font.BOLD,12));
                    }
                }
            return componente;
        }else{
            componente.setForeground(Color.black);
            return componente;
        }
        
    }
    
}
