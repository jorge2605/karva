package VO;

import Conexiones.Conexion;
import Controlador.maquinados.revisarPlanos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ActualizarPlanos {

    public static void main(String[] args) {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select Estado, Plano from planos where Estado = 'LIBERACION'";
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            revisarPlanos rev = new revisarPlanos();
            while (rs.next()) {
                String plano = (rs.getString("Plano"));
//                String bus = rev.buscar(plano, con);
//                bus = (bus.equals("TERMINADO")) ? "TERMINADO (CALIDAD)" : bus;
                rev.actualizarPlanos(con, plano, "CORTES");
//                System.out.println("Plano: " + plano + " a " + bus);
                cont++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
