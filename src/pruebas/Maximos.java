package pruebas;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Maximos extends JTable implements MouseListener{
    Component componente;
    TableCellRenderer renderer;
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex){
        componente = super.prepareRenderer(renderer, rowIndex, columnIndex);
        this.renderer = renderer;
        DefaultTableModel miModelo = (DefaultTableModel) this.getModel();
        this.addMouseListener(this);
        if((getValueAt(rowIndex,2)) != null){
        if(getValueAt(rowIndex,2).getClass().equals(String.class)){
            
            int maximo;
            int minimo; 
            String min;
            String max;
            
            if((getValueAt(rowIndex, 4)) == null){
                min = "0";
            }else{
                min = this.getValueAt(rowIndex, 4).toString();
            }
            
            if((getValueAt(rowIndex, 3)) == null){
                max = "1000";
            }else{
                max = this.getValueAt(rowIndex, 3).toString();
            }
            minimo = Integer.parseInt(min);
            maximo = Integer.parseInt(max);
            double cantidad;
            try{cantidad = Double.parseDouble(this.getValueAt(rowIndex, 2).toString());}catch(Exception e){cantidad = 0;}
            
            if(cantidad > minimo && cantidad < maximo){
                componente.setBackground(null);
                componente.setForeground(null);
            }else if(cantidad > maximo){
                componente.setBackground(new Color(230,70,0));
                componente.setForeground(Color.white);
            }else if(cantidad < minimo){
                componente.setBackground(Color.red);
                componente.setForeground(Color.white);
            }else{
                componente.setBackground(null);
                componente.setForeground(null);
                
            }
        }
        }
        
        return componente;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        int fila = this.getSelectedRow();
        componente = super.prepareRenderer(renderer, fila, 2);
        componente.setBackground(Color.green);
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
