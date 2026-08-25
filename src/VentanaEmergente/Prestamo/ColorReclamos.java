package VentanaEmergente.Prestamo;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class ColorReclamos extends JTable{
   @Override
        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex)
    {
        Component componente= super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        if(ColumnIndex == 5){
       
       
           
       try {
           
           
           String valor = (this.getValueAt(rowIndex, 5).toString());
           
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date d = new Date();
           String fe = sdf.format(d);
           
           Date d1 = sdf.parse(fe);
           Date d2 = sdf.parse(valor);
           
           long dif = d2.getTime() - d1.getTime();
           TimeUnit time = TimeUnit.DAYS;
           long resta = time.convert(dif, TimeUnit.MILLISECONDS);
           componente.setFont(new Font("Roboto",Font.BOLD,12));
           if(resta == 3){
               componente.setBackground(Color.decode("#ffda0c"));
               componente.setForeground(Color.black);
               
           }else if(resta == 2){
               componente.setBackground(Color.decode("#ffa200"));
               componente.setForeground(Color.black);
           }else if(resta == 1){
               componente.setBackground(Color.decode("#ff6600"));
               componente.setForeground(Color.black);
           }else if(resta <= 0){
               componente.setBackground(Color.decode("#9e0000"));
               componente.setForeground(Color.white);
           }else{
               componente.setBackground(Color.decode("#ffffff"));
               componente.setForeground(Color.black);
           }
           
       } catch (ParseException ex) {
           Logger.getLogger(ColorReclamos.class.getName()).log(Level.SEVERE, null, ex);
       }
       }else{
            componente.setBackground(null);
               componente.setForeground(Color.black);
        }
       
       return componente;
    }
}