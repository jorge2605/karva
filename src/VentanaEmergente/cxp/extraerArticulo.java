package VentanaEmergente.cxp;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Stack;
import javax.swing.JOptionPane;

public class extraerArticulo {
    
    public Stack<articulos> articulos;
    public totales total;
    Connection con;
    
    public void agregarATabla(String id){
        int pos = id.indexOf(':');
        String dat[] = new String[10];
        dat[0] = id.substring(0, pos);
        dat[1] = id.substring(pos + 1, id.length());
        try{
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where Id like '" + dat[0] + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                dat[2] = rs.getString("Codigo");
                dat[3] = rs.getString("Descripcion");
                dat[4] = rs.getString("OC");
                dat[5] = rs.getString("Proveedor");
                dat[6] = rs.getString("Precio");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR EN EXTRAER ARTICULOS: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        double precio, cantidad, tot;
        try{precio = Double.parseDouble(dat[6]);}catch(Exception e){precio = 0;}
        try{cantidad = Double.parseDouble(dat[1]);}catch(Exception e){cantidad = 0;}
        try{tot = precio * cantidad;}catch(Exception e){tot = 0;}
        articulos art = new articulos(dat[2],precio,cantidad,tot,dat[5]);
        articulos.push(art);
    }
    
    public final totales decoder(String code){
        Stack<Integer> pila = new Stack<>();
        int pos = code.indexOf(',');
        while(pos != -1){
            pila.push(pos);
            pos = code.indexOf(',', pos + 1);
        }
        
        if(!pila.isEmpty()){
            int inicio = 1;
            for (int i = 0; i < pila.size(); i++) {
                String id = code.substring(inicio, pila.get(i));
                agregarATabla(id);
                inicio = pila.get(i)+1;
            }
            String id = code.substring(inicio, code.length() - 1);
            agregarATabla(id);
        }else{
            String id = code.substring(1, code.length() - 1);
            agregarATabla(id);
        }
        if(!articulos.isEmpty()){
            double subtotal = 0;
            for (int i = 0; i < articulos.size(); i++) {
                double precio = articulos.get(i).getPrecio();
                double cant = articulos.get(i).getCantidad();
                subtotal += (precio * cant);
            }
            double isr = 0;
            double iva = 0;
            try{
                Statement st = con.createStatement();
                String sql = "select * from registroprov_compras where Nombre like '" + articulos.get(0).getProveedor() + "'";
                ResultSet rs = st.executeQuery(sql);
                String is = "";
                String iv = "";
                while(rs.next()){
                    is = rs.getString("Isr");
                    iv = rs.getString("Iva");
                }
                try{isr = Double.parseDouble(is);}catch(Exception e){isr = 0;}
                try{iva = Double.parseDouble(iv);}catch(Exception e){iva = 0;}
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "ERROR al ver isr/iva: " + e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
            isr = (subtotal * isr) / 100;
            iva = (subtotal * iva) / 100;
            
            double tot = subtotal + iva -isr;
            
            DecimalFormat formato = new DecimalFormat("#,###.##");
            
            this.total = new totales(isr,iva,subtotal,tot,articulos);
            return this.total;
        }
        return null;
    }
    
    public extraerArticulo(String code, Connection con){
        articulos = new Stack<>();
        this.con = con;
        decoder(code);
    }
}
