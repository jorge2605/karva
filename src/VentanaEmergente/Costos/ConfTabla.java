package VentanaEmergente.Costos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ConfTabla extends JTable{
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        Component componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        
        if(getColumnName(ColumnIndex).equals("ID")){
            String nombre;
            try{nombre = getValueAt(rowIndex, 1).toString();}catch(Exception e){nombre = "";}
            if(nombre.equals("")){
                componente.setForeground(Color.RED);
                componente.setFont(new Font("Lexend",Font.BOLD,12));
            }
        }else{
            componente.setForeground(Color.black);
            componente.setFont(new Font("Lexend",Font.PLAIN,10));
        }
        
        if(getColumnName(ColumnIndex).equals("Sueldo por mes")){
            double s1,s2,s3,s4,s5;
            try{s1 = Double.parseDouble(getValueAt(rowIndex, 8).toString());}catch(NumberFormatException e){s1 = 0;}
            try{s2 = Double.parseDouble(getValueAt(rowIndex, 9).toString());}catch(NumberFormatException e){s2 = 0;}
            try{s3 = Double.parseDouble(getValueAt(rowIndex, 10).toString());}catch(NumberFormatException e){s3 = 0;}
            try{s4 = Double.parseDouble(getValueAt(rowIndex, 11).toString());}catch(NumberFormatException e){s4 = 0;}
            try{s5 = Double.parseDouble(getValueAt(rowIndex, 12).toString());}catch(NumberFormatException e){s5 = 0;}
            
            double total = s1 +s2 + s3 + s4 + s5;
            DecimalFormat formato = new DecimalFormat("#,###.##");
            
            componente.setFont(new Font("Lexend",Font.BOLD,12));
            setValueAt(formato.format(total), rowIndex, ColumnIndex);
            revalidate();
            repaint();
        }
        
        if(getColumnName(ColumnIndex).equals("Hora al mes")){
            double s1,s2,s3,s4,s5;
            try{s1 = Double.parseDouble(getValueAt(rowIndex, 3).toString());}catch(NumberFormatException e){s1 = 0;}
            try{s2 = Double.parseDouble(getValueAt(rowIndex, 4).toString());}catch(NumberFormatException e){s2 = 0;}
            try{s3 = Double.parseDouble(getValueAt(rowIndex, 5).toString());}catch(NumberFormatException e){s3 = 0;}
            try{s4 = Double.parseDouble(getValueAt(rowIndex, 6).toString());}catch(NumberFormatException e){s4 = 0;}
            try{s5 = Double.parseDouble(getValueAt(rowIndex, 7).toString());}catch(NumberFormatException e){s5 = 0;}
            
            double total = s1 +s2 + s3 + s4 + s5;
            
            componente.setFont(new Font("Lexend",Font.BOLD,12));
            setValueAt(total, rowIndex, ColumnIndex);
            revalidate();
            repaint();
        }
        
        if(getColumnName(ColumnIndex).equals("MOD")){
            double s1,s2;
            try{s1 = Double.parseDouble(getValueAt(rowIndex, 13).toString().replace(",", ""));}catch(NumberFormatException e){s1 = 0;}
            try{s2 = Double.parseDouble(getValueAt(rowIndex, 14).toString());}catch(NumberFormatException e){s2 = 0;}
            
            double total;
            DecimalFormat formato = new DecimalFormat("#,###.##");
            
            try{total = s1 / s2;}catch(Exception e){total = 0; System.err.println(e);}
            
            componente.setFont(new Font("Lexend",Font.BOLD,12));
            setValueAt(formato.format(total), rowIndex, ColumnIndex);
            revalidate();
            repaint();
        }
        
        
        revalidate();
        repaint();
        return componente;
    }
}
