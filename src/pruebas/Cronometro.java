package pruebas;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.apache.poi.sl.usermodel.Background;

public final class Cronometro extends javax.swing.JInternalFrame {

    String numEmpleado;
    public Color lun[];
    public Color mar[];
    public Color mie[];
    public Color jue[];
    public Color vie[];
    public Color sab[];
    public Color dom[];
    TextAutoCompleter ac1[];
    TextAutoCompleter ac2[];
    TextAutoCompleter ac3[];
    TextAutoCompleter ac4[];
    TextAutoCompleter ac5[];
    TextAutoCompleter ac6[];
    TextAutoCompleter ac7[];
    JTextField txt[][];
    Color color[][];
    
    Stack<String> proyectos;
    
    int fila;
    int col;
    
    public void addAuto(){
        ac1 = new TextAutoCompleter[25];
        ac2 = new TextAutoCompleter[25];
        ac3 = new TextAutoCompleter[25];
        ac4 = new TextAutoCompleter[25];
        ac5 = new TextAutoCompleter[25];
        ac6 = new TextAutoCompleter[25];
        ac7 = new TextAutoCompleter[25];
        proyectos = new Stack<>();
        verProyecto();
        autoCompletarProyecto(ac1[1], Lunes1);
        autoCompletarProyecto(ac1[2], Lunes2);
        autoCompletarProyecto(ac1[3], Lunes3);
        autoCompletarProyecto(ac1[4], Lunes4);
        autoCompletarProyecto(ac1[5], Lunes5);
        autoCompletarProyecto(ac1[6], Lunes6);
        autoCompletarProyecto(ac1[7], Lunes7);
        autoCompletarProyecto(ac1[8], Lunes8);
        autoCompletarProyecto(ac1[9], Lunes9);
        autoCompletarProyecto(ac1[10], Lunes10);
        autoCompletarProyecto(ac1[11], Lunes11);
        autoCompletarProyecto(ac1[12], Lunes12);
        autoCompletarProyecto(ac1[13], Lunes13);
        autoCompletarProyecto(ac1[14], Lunes14);
        autoCompletarProyecto(ac1[15], Lunes15);
        autoCompletarProyecto(ac1[16], Lunes16);
        autoCompletarProyecto(ac1[17], Lunes17);
        autoCompletarProyecto(ac1[18], Lunes18);
        autoCompletarProyecto(ac1[19], Lunes19);
        autoCompletarProyecto(ac1[20], Lunes20);
        autoCompletarProyecto(ac1[21], Lunes21);
        autoCompletarProyecto(ac1[22], Lunes22);
        autoCompletarProyecto(ac1[23], Lunes23);
        autoCompletarProyecto(ac1[24], Lunes24);
        
        autoCompletarProyecto(ac2[1], martes1);
        autoCompletarProyecto(ac2[2], martes2);
        autoCompletarProyecto(ac2[3], martes3);
        autoCompletarProyecto(ac2[4], martes4);
        autoCompletarProyecto(ac2[5], martes5);
        autoCompletarProyecto(ac2[6], martes6);
        autoCompletarProyecto(ac2[7], martes7);
        autoCompletarProyecto(ac2[8], martes8);
        autoCompletarProyecto(ac2[9], martes9);
        autoCompletarProyecto(ac2[10], martes10);
        autoCompletarProyecto(ac2[11], martes11);
        autoCompletarProyecto(ac2[12], martes12);
        autoCompletarProyecto(ac2[13], martes13);
        autoCompletarProyecto(ac2[14], martes14);
        autoCompletarProyecto(ac2[15], martes15);
        autoCompletarProyecto(ac2[16], martes16);
        autoCompletarProyecto(ac2[17], martes17);
        autoCompletarProyecto(ac2[18], martes18);
        autoCompletarProyecto(ac2[19], martes19);
        autoCompletarProyecto(ac2[20], martes20);
        autoCompletarProyecto(ac2[21], martes21);
        autoCompletarProyecto(ac2[22], martes22);
        autoCompletarProyecto(ac2[23], martes23);
        autoCompletarProyecto(ac2[24], martes24);
        
        autoCompletarProyecto(ac3[1], miercoles1);
        autoCompletarProyecto(ac3[2], miercoles2);
        autoCompletarProyecto(ac3[3], miercoles3);
        autoCompletarProyecto(ac3[4], miercoles4);
        autoCompletarProyecto(ac3[5], miercoles5);
        autoCompletarProyecto(ac3[6], miercoles6);
        autoCompletarProyecto(ac3[7], miercoles7);
        autoCompletarProyecto(ac3[8], miercoles8);
        autoCompletarProyecto(ac3[9], miercoles9);
        autoCompletarProyecto(ac3[10], miercoles10);
        autoCompletarProyecto(ac3[11], miercoles11);
        autoCompletarProyecto(ac3[12], miercoles12);
        autoCompletarProyecto(ac3[13], miercoles13);
        autoCompletarProyecto(ac3[14], miercoles14);
        autoCompletarProyecto(ac3[15], miercoles15);
        autoCompletarProyecto(ac3[16], miercoles16);
        autoCompletarProyecto(ac3[17], miercoles17);
        autoCompletarProyecto(ac3[18], miercoles18);
        autoCompletarProyecto(ac3[19], miercoles19);
        autoCompletarProyecto(ac3[20], miercoles20);
        autoCompletarProyecto(ac3[21], miercoles21);
        autoCompletarProyecto(ac3[22], miercoles22);
        autoCompletarProyecto(ac3[23], miercoles23);
        autoCompletarProyecto(ac3[24], miercoles24);
        
        autoCompletarProyecto(ac4[1], jueves1);
        autoCompletarProyecto(ac4[2], jueves2);
        autoCompletarProyecto(ac4[3], jueves3);
        autoCompletarProyecto(ac4[4], jueves4);
        autoCompletarProyecto(ac4[5], jueves5);
        autoCompletarProyecto(ac4[6], jueves6);
        autoCompletarProyecto(ac4[7], jueves7);
        autoCompletarProyecto(ac4[8], jueves8);
        autoCompletarProyecto(ac4[9], jueves9);
        autoCompletarProyecto(ac4[10], jueves10);
        autoCompletarProyecto(ac4[11], jueves11);
        autoCompletarProyecto(ac4[12], jueves12);
        autoCompletarProyecto(ac4[13], jueves13);
        autoCompletarProyecto(ac4[14], jueves14);
        autoCompletarProyecto(ac4[15], jueves15);
        autoCompletarProyecto(ac4[16], jueves16);
        autoCompletarProyecto(ac4[17], jueves17);
        autoCompletarProyecto(ac4[18], jueves18);
        autoCompletarProyecto(ac4[19], jueves19);
        autoCompletarProyecto(ac4[20], jueves20);
        autoCompletarProyecto(ac4[21], jueves21);
        autoCompletarProyecto(ac4[22], jueves22);
        autoCompletarProyecto(ac4[23], jueves23);
        autoCompletarProyecto(ac4[24], jueves24);
        
        autoCompletarProyecto(ac5[1], viernes1);
        autoCompletarProyecto(ac5[2], viernes2);
        autoCompletarProyecto(ac5[3], viernes3);
        autoCompletarProyecto(ac5[4], viernes4);
        autoCompletarProyecto(ac5[5], viernes5);
        autoCompletarProyecto(ac5[6], viernes6);
        autoCompletarProyecto(ac5[7], viernes7);
        autoCompletarProyecto(ac5[8], viernes8);
        autoCompletarProyecto(ac5[9], viernes9);
        autoCompletarProyecto(ac5[10], viernes10);
        autoCompletarProyecto(ac5[11], viernes11);
        autoCompletarProyecto(ac5[12], viernes12);
        autoCompletarProyecto(ac5[13], viernes13);
        autoCompletarProyecto(ac5[14], viernes14);
        autoCompletarProyecto(ac5[15], viernes15);
        autoCompletarProyecto(ac5[16], viernes16);
        autoCompletarProyecto(ac5[17], viernes17);
        autoCompletarProyecto(ac5[18], viernes18);
        autoCompletarProyecto(ac5[19], viernes19);
        autoCompletarProyecto(ac5[20], viernes20);
        autoCompletarProyecto(ac5[21], viernes21);
        autoCompletarProyecto(ac5[22], viernes22);
        autoCompletarProyecto(ac5[23], viernes23);
        autoCompletarProyecto(ac5[24], viernes24);
        
        autoCompletarProyecto(ac6[1], sabado1);
        autoCompletarProyecto(ac6[2], sabado2);
        autoCompletarProyecto(ac6[3], sabado3);
        autoCompletarProyecto(ac6[4], sabado4);
        autoCompletarProyecto(ac6[5], sabado5);
        autoCompletarProyecto(ac6[6], sabado6);
        autoCompletarProyecto(ac6[7], sabado7);
        autoCompletarProyecto(ac6[8], sabado8);
        autoCompletarProyecto(ac6[9], sabado9);
        autoCompletarProyecto(ac6[10], sabado10);
        autoCompletarProyecto(ac6[11], sabado11);
        autoCompletarProyecto(ac6[12], sabado12);
        autoCompletarProyecto(ac6[13], sabado13);
        autoCompletarProyecto(ac6[14], sabado14);
        autoCompletarProyecto(ac6[15], sabado15);
        autoCompletarProyecto(ac6[16], sabado16);
        autoCompletarProyecto(ac6[17], sabado17);
        autoCompletarProyecto(ac6[18], sabado18);
        autoCompletarProyecto(ac6[19], sabado19);
        autoCompletarProyecto(ac6[20], sabado20);
        autoCompletarProyecto(ac6[21], sabado21);
        autoCompletarProyecto(ac6[22], sabado22);
        autoCompletarProyecto(ac6[23], sabado23);
        autoCompletarProyecto(ac6[24], sabado24);
        
        autoCompletarProyecto(ac7[1], domingo1);
        autoCompletarProyecto(ac7[2], domingo2);
        autoCompletarProyecto(ac7[3], domingo3);
        autoCompletarProyecto(ac7[4], domingo4);
        autoCompletarProyecto(ac7[5], domingo5);
        autoCompletarProyecto(ac7[6], domingo6);
        autoCompletarProyecto(ac7[7], domingo7);
        autoCompletarProyecto(ac7[8], domingo8);
        autoCompletarProyecto(ac7[9], domingo9);
        autoCompletarProyecto(ac7[10], domingo10);
        autoCompletarProyecto(ac7[11], domingo11);
        autoCompletarProyecto(ac7[12], domingo12);
        autoCompletarProyecto(ac7[13], domingo13);
        autoCompletarProyecto(ac7[14], domingo14);
        autoCompletarProyecto(ac7[15], domingo15);
        autoCompletarProyecto(ac7[16], domingo16);
        autoCompletarProyecto(ac7[17], domingo17);
        autoCompletarProyecto(ac7[18], domingo18);
        autoCompletarProyecto(ac7[19], domingo19);
        autoCompletarProyecto(ac7[20], domingo20);
        autoCompletarProyecto(ac7[21], domingo21);
        autoCompletarProyecto(ac7[22], domingo22);
        autoCompletarProyecto(ac7[23], domingo23);
        autoCompletarProyecto(ac7[24], domingo24);
    }
    
    public void verProyecto(){
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
                proyectos.push(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void autoCompletarProyecto(TextAutoCompleter ac, JTextField txt) {
        ac = new TextAutoCompleter(txt);
        for (int i = 0; i < proyectos.size(); i++) {
            ac.addItem(proyectos.get(i));
        }
    }
    
    public void insertarSemanas(){
        cmbSemanas.removeAllItems();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek( Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek( 4);
        int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        for (int i = numberWeekOfYear; i >= 1; i--) {
            cmbSemanas.addItem(String.valueOf(i));
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        lblYear.setText(sdf.format(d));
    }
    
    public void setFecha(){
        int numeroSemana = Integer.parseInt(cmbSemanas.getSelectedItem().toString());
        int anio = Integer.parseInt(lblYear.getText());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, anio);
        calendar.set(Calendar.WEEK_OF_YEAR, numeroSemana);

        int primerDiaSemana = Calendar.MONDAY; // Puedes cambiarlo al día que necesites
        calendar.set(Calendar.DAY_OF_WEEK, primerDiaSemana);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            switch(i){
                case 0:
                    lunes.setText(sdf.format(calendar.getTime()));
                    break;
                case 1:
                    martes.setText(sdf.format(calendar.getTime()));
                    break;
                case 2:
                    miercoles.setText(sdf.format(calendar.getTime()));
                    break;
                case 3:
                    jueves.setText(sdf.format(calendar.getTime()));
                    break;
                case 4:
                    viernes.setText(sdf.format(calendar.getTime()));
                    break;
                case 5:
                    sabado.setText(sdf.format(calendar.getTime()));
                    break;
                case 6:
                    domingo.setText(sdf.format(calendar.getTime()));
                    break;
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }
    
    public void setBlanco(){
        Lunes1.setBackground(Color.white);
        Lunes2.setBackground(Color.white);
        Lunes3.setBackground(Color.white);
        Lunes4.setBackground(Color.white);
        Lunes5.setBackground(Color.white);
        Lunes6.setBackground(Color.white);
        Lunes7.setBackground(Color.white);
        Lunes8.setBackground(Color.white);
        Lunes9.setBackground(Color.white);
        Lunes10.setBackground(Color.white);
        Lunes11.setBackground(Color.white);
        Lunes12.setBackground(Color.white);
        Lunes13.setBackground(Color.white);
        Lunes14.setBackground(Color.white);
        Lunes15.setBackground(Color.white);
        Lunes16.setBackground(Color.white);
        Lunes17.setBackground(Color.white);
        Lunes18.setBackground(Color.white);
        Lunes19.setBackground(Color.white);
        Lunes20.setBackground(Color.white);
        Lunes21.setBackground(Color.white);
        Lunes22.setBackground(Color.white);
        Lunes23.setBackground(Color.white);
        Lunes24.setBackground(Color.white);
        
        martes1.setBackground(Color.white);
        martes2.setBackground(Color.white);
        martes3.setBackground(Color.white);
        martes4.setBackground(Color.white);
        martes5.setBackground(Color.white);
        martes6.setBackground(Color.white);
        martes7.setBackground(Color.white);
        martes8.setBackground(Color.white);
        martes9.setBackground(Color.white);
        martes10.setBackground(Color.white);
        martes11.setBackground(Color.white);
        martes12.setBackground(Color.white);
        martes13.setBackground(Color.white);
        martes14.setBackground(Color.white);
        martes15.setBackground(Color.white);
        martes16.setBackground(Color.white);
        martes17.setBackground(Color.white);
        martes18.setBackground(Color.white);
        martes19.setBackground(Color.white);
        martes20.setBackground(Color.white);
        martes21.setBackground(Color.white);
        martes22.setBackground(Color.white);
        martes23.setBackground(Color.white);
        martes24.setBackground(Color.white);
        
        miercoles1.setBackground(Color.white);
        miercoles2.setBackground(Color.white);
        miercoles3.setBackground(Color.white);
        miercoles4.setBackground(Color.white);
        miercoles5.setBackground(Color.white);
        miercoles6.setBackground(Color.white);
        miercoles7.setBackground(Color.white);
        miercoles8.setBackground(Color.white);
        miercoles9.setBackground(Color.white);
        miercoles10.setBackground(Color.white);
        miercoles11.setBackground(Color.white);
        miercoles12.setBackground(Color.white);
        miercoles13.setBackground(Color.white);
        miercoles14.setBackground(Color.white);
        miercoles15.setBackground(Color.white);
        miercoles16.setBackground(Color.white);
        miercoles17.setBackground(Color.white);
        miercoles18.setBackground(Color.white);
        miercoles19.setBackground(Color.white);
        miercoles20.setBackground(Color.white);
        miercoles21.setBackground(Color.white);
        miercoles22.setBackground(Color.white);
        miercoles23.setBackground(Color.white);
        miercoles24.setBackground(Color.white);
        
        jueves1.setBackground(Color.white);
        jueves2.setBackground(Color.white);
        jueves3.setBackground(Color.white);
        jueves4.setBackground(Color.white);
        jueves5.setBackground(Color.white);
        jueves6.setBackground(Color.white);
        jueves7.setBackground(Color.white);
        jueves8.setBackground(Color.white);
        jueves9.setBackground(Color.white);
        jueves10.setBackground(Color.white);
        jueves11.setBackground(Color.white);
        jueves12.setBackground(Color.white);
        jueves13.setBackground(Color.white);
        jueves14.setBackground(Color.white);
        jueves15.setBackground(Color.white);
        jueves16.setBackground(Color.white);
        jueves17.setBackground(Color.white);
        jueves18.setBackground(Color.white);
        jueves19.setBackground(Color.white);
        jueves20.setBackground(Color.white);
        jueves21.setBackground(Color.white);
        jueves22.setBackground(Color.white);
        jueves23.setBackground(Color.white);
        jueves24.setBackground(Color.white);
        
        viernes1.setBackground(Color.white);
        viernes2.setBackground(Color.white);
        viernes3.setBackground(Color.white);
        viernes4.setBackground(Color.white);
        viernes5.setBackground(Color.white);
        viernes6.setBackground(Color.white);
        viernes7.setBackground(Color.white);
        viernes8.setBackground(Color.white);
        viernes9.setBackground(Color.white);
        viernes10.setBackground(Color.white);
        viernes11.setBackground(Color.white);
        viernes12.setBackground(Color.white);
        viernes13.setBackground(Color.white);
        viernes14.setBackground(Color.white);
        viernes15.setBackground(Color.white);
        viernes16.setBackground(Color.white);
        viernes17.setBackground(Color.white);
        viernes18.setBackground(Color.white);
        viernes19.setBackground(Color.white);
        viernes20.setBackground(Color.white);
        viernes21.setBackground(Color.white);
        viernes22.setBackground(Color.white);
        viernes23.setBackground(Color.white);
        viernes24.setBackground(Color.white);
        
        sabado1.setBackground(Color.white);
        sabado2.setBackground(Color.white);
        sabado3.setBackground(Color.white);
        sabado4.setBackground(Color.white);
        sabado5.setBackground(Color.white);
        sabado6.setBackground(Color.white);
        sabado7.setBackground(Color.white);
        sabado8.setBackground(Color.white);
        sabado9.setBackground(Color.white);
        sabado10.setBackground(Color.white);
        sabado11.setBackground(Color.white);
        sabado12.setBackground(Color.white);
        sabado13.setBackground(Color.white);
        sabado14.setBackground(Color.white);
        sabado15.setBackground(Color.white);
        sabado16.setBackground(Color.white);
        sabado17.setBackground(Color.white);
        sabado18.setBackground(Color.white);
        sabado19.setBackground(Color.white);
        sabado20.setBackground(Color.white);
        sabado21.setBackground(Color.white);
        sabado22.setBackground(Color.white);
        sabado23.setBackground(Color.white);
        sabado24.setBackground(Color.white);
        
        domingo1.setBackground(Color.white);
        domingo2.setBackground(Color.white);
        domingo3.setBackground(Color.white);
        domingo4.setBackground(Color.white);
        domingo5.setBackground(Color.white);
        domingo6.setBackground(Color.white);
        domingo7.setBackground(Color.white);
        domingo8.setBackground(Color.white);
        domingo9.setBackground(Color.white);
        domingo10.setBackground(Color.white);
        domingo11.setBackground(Color.white);
        domingo12.setBackground(Color.white);
        domingo13.setBackground(Color.white);
        domingo14.setBackground(Color.white);
        domingo15.setBackground(Color.white);
        domingo16.setBackground(Color.white);
        domingo17.setBackground(Color.white);
        domingo18.setBackground(Color.white);
        domingo19.setBackground(Color.white);
        domingo20.setBackground(Color.white);
        domingo21.setBackground(Color.white);
        domingo22.setBackground(Color.white);
        domingo23.setBackground(Color.white);
        domingo24.setBackground(Color.white);
    }
    
    public void getColor(){
        lun = new Color[25];
        mar = new Color[25];
        mie = new Color[25];
        jue = new Color[25];
        vie = new Color[25];
        sab = new Color[25];
        dom = new Color[25];
        color = new Color[9][25];
        color[1][1] = lun[1] = Lunes1.getBackground();
        color[1][2] = lun[2] = Lunes2.getBackground();
        color[1][3] = lun[3] = Lunes3.getBackground();
        color[1][4] = lun[4] = Lunes4.getBackground();
        color[1][5] = lun[5] = Lunes5.getBackground();
        color[1][6] = lun[6] = Lunes6.getBackground();
        color[1][7] = lun[7] = Lunes7.getBackground();
        color[1][8] = lun[8] = Lunes8.getBackground();
        color[1][9] = lun[9] = Lunes9.getBackground();
        color[1][10] = lun[10] = Lunes10.getBackground();
        color[1][11] = lun[11] = Lunes11.getBackground();
        color[1][12] = lun[12] = Lunes12.getBackground();
        color[1][13] = lun[13] = Lunes13.getBackground();
        color[1][14] = lun[14] = Lunes14.getBackground();
        color[1][15] = lun[15] = Lunes15.getBackground();
        color[1][16] = lun[16] = Lunes16.getBackground();
        color[1][17] = lun[17] = Lunes17.getBackground();
        color[1][18] = lun[18] = Lunes18.getBackground();
        color[1][19] = lun[19] = Lunes19.getBackground();
        color[1][20] = lun[20] = Lunes20.getBackground();
        color[1][21] = lun[21] = Lunes21.getBackground();
        color[1][22] = lun[22] = Lunes22.getBackground();
        color[1][23] = lun[23] = Lunes23.getBackground();
        color[1][24] = lun[24] = Lunes24.getBackground();
        
        color[2][1] = mar[1] = martes1.getBackground();
        color[2][2] = mar[2] = martes2.getBackground();
        color[2][3] = mar[3] = martes3.getBackground();
        color[2][4] = mar[4] = martes4.getBackground();
        color[2][5] = mar[5] = martes5.getBackground();
        color[2][6] = mar[6] = martes6.getBackground();
        color[2][7] = mar[7] = martes7.getBackground();
        color[2][8] = mar[8] = martes8.getBackground();
        color[2][9] = mar[9] = martes9.getBackground();
        color[2][10] = mar[10] = martes10.getBackground();
        color[2][11] = mar[11] = martes11.getBackground();
        color[2][12] = mar[12] = martes12.getBackground();
        color[2][13] = mar[13] = martes13.getBackground();
        color[2][14] = mar[14] = martes14.getBackground();
        color[2][15] = mar[15] = martes15.getBackground();
        color[2][16] = mar[16] = martes16.getBackground();
        color[2][17] = mar[17] = martes17.getBackground();
        color[2][18] = mar[18] = martes18.getBackground();
        color[2][19] = mar[19] = martes19.getBackground();
        color[2][20] = mar[20] = martes20.getBackground();
        color[2][21] = mar[21] = martes21.getBackground();
        color[2][22] = mar[22] = martes22.getBackground();
        color[2][23] = mar[23] = martes23.getBackground();
        color[2][24] = mar[24] = martes24.getBackground();
        
        color[3][1] = mie[1] = miercoles1.getBackground();
        color[3][2] = mie[2] = miercoles2.getBackground();
        color[3][3] = mie[3] = miercoles3.getBackground();
        color[3][4] = mie[4] = miercoles4.getBackground();
        color[3][5] = mie[5] = miercoles5.getBackground();
        color[3][6] = mie[6] = miercoles6.getBackground();
        color[3][7] = mie[7] = miercoles7.getBackground();
        color[3][8] = mie[8] = miercoles8.getBackground();
        color[3][9] = mie[9] = miercoles9.getBackground();
        color[3][10] = mie[10] = miercoles10.getBackground();
        color[3][11] = mie[11] = miercoles11.getBackground();
        color[3][12] = mie[12] = miercoles12.getBackground();
        color[3][13] = mie[13] = miercoles13.getBackground();
        color[3][14] = mie[14] = miercoles14.getBackground();
        color[3][15] = mie[15] = miercoles15.getBackground();
        color[3][16] = mie[16] = miercoles16.getBackground();
        color[3][17] = mie[17] = miercoles17.getBackground();
        color[3][18] = mie[18] = miercoles18.getBackground();
        color[3][19] = mie[19] = miercoles19.getBackground();
        color[3][20] = mie[20] = miercoles20.getBackground();
        color[3][21] = mie[21] = miercoles21.getBackground();
        color[3][22] = mie[22] = miercoles22.getBackground();
        color[3][23] = mie[23] = miercoles23.getBackground();
        color[3][24] = mie[24] = miercoles24.getBackground();
        
        color[4][1] = jue[1] = jueves1.getBackground();
        color[4][2] = jue[2] = jueves2.getBackground();
        color[4][3] = jue[3] = jueves3.getBackground();
        color[4][4] = jue[4] = jueves4.getBackground();
        color[4][5] = jue[5] = jueves5.getBackground();
        color[4][6] = jue[6] = jueves6.getBackground();
        color[4][7] = jue[7] = jueves7.getBackground();
        color[4][8] = jue[8] = jueves8.getBackground();
        color[4][9] = jue[9] = jueves9.getBackground();
        color[4][10] = jue[10] = jueves10.getBackground();
        color[4][11] = jue[11] = jueves11.getBackground();
        color[4][12] = jue[12] = jueves12.getBackground();
        color[4][13] = jue[13] = jueves13.getBackground();
        color[4][14] = jue[14] = jueves14.getBackground();
        color[4][15] = jue[15] = jueves15.getBackground();
        color[4][16] = jue[16] = jueves16.getBackground();
        color[4][17] = jue[17] = jueves17.getBackground();
        color[4][18] = jue[18] = jueves18.getBackground();
        color[4][19] = jue[19] = jueves19.getBackground();
        color[4][20] = jue[20] = jueves20.getBackground();
        color[4][21] = jue[21] = jueves21.getBackground();
        color[4][22] = jue[22] = jueves22.getBackground();
        color[4][23] = jue[23] = jueves23.getBackground();
        color[4][24] = jue[24] = jueves24.getBackground();
        
        color[5][1] = vie[1] = viernes1.getBackground();
        color[5][2] = vie[2] = viernes2.getBackground();
        color[5][3] = vie[3] = viernes3.getBackground();
        color[5][4] = vie[4] = viernes4.getBackground();
        color[5][5] = vie[5] = viernes5.getBackground();
        color[5][6] = vie[6] = viernes6.getBackground();
        color[5][7] = vie[7] = viernes7.getBackground();
        color[5][8] = vie[8] = viernes8.getBackground();
        color[5][9] = vie[9] = viernes9.getBackground();
        color[5][10] = vie[10] = viernes10.getBackground();
        color[5][11] = vie[11] = viernes11.getBackground();
        color[5][12] = vie[12] = viernes12.getBackground();
        color[5][13] = vie[13] = viernes13.getBackground();
        color[5][14] = vie[14] = viernes14.getBackground();
        color[5][15] = vie[15] = viernes15.getBackground();
        color[5][16] = vie[16] = viernes16.getBackground();
        color[5][17] = vie[17] = viernes17.getBackground();
        color[5][18] = vie[18] = viernes18.getBackground();
        color[5][19] = vie[19] = viernes19.getBackground();
        color[5][20] = vie[20] = viernes20.getBackground();
        color[5][21] = vie[21] = viernes21.getBackground();
        color[5][22] = vie[22] = viernes22.getBackground();
        color[5][23] = vie[23] = viernes23.getBackground();
        color[5][24] = vie[24] = viernes24.getBackground();
        
        color[6][1] = sab[1] = sabado1.getBackground();
        color[6][2] = sab[2] = sabado2.getBackground();
        color[6][3] = sab[3] = sabado3.getBackground();
        color[6][4] = sab[4] = sabado4.getBackground();
        color[6][5] = sab[5] = sabado5.getBackground();
        color[6][6] = sab[6] = sabado6.getBackground();
        color[6][7] = sab[7] = sabado7.getBackground();
        color[6][8] = sab[8] = sabado8.getBackground();
        color[6][9] = sab[9] = sabado9.getBackground();
        color[6][10] = sab[10] = sabado10.getBackground();
        color[6][11] = sab[11] = sabado11.getBackground();
        color[6][12] = sab[12] = sabado12.getBackground();
        color[6][13] = sab[13] = sabado13.getBackground();
        color[6][14] = sab[14] = sabado14.getBackground();
        color[6][15] = sab[15] = sabado15.getBackground();
        color[6][16] = sab[16] = sabado16.getBackground();
        color[6][17] = sab[17] = sabado17.getBackground();
        color[6][18] = sab[18] = sabado18.getBackground();
        color[6][19] = sab[19] = sabado19.getBackground();
        color[6][20] = sab[20] = sabado20.getBackground();
        color[6][21] = sab[21] = sabado21.getBackground();
        color[6][22] = sab[22] = sabado22.getBackground();
        color[6][23] = sab[23] = sabado23.getBackground();
        color[6][24] = sab[24] = sabado24.getBackground();
        
        color[7][1] = dom[1] = domingo1.getBackground();
        color[7][2] = dom[2] = domingo2.getBackground();
        color[7][3] = dom[3] = domingo3.getBackground();
        color[7][4] = dom[4] = domingo4.getBackground();
        color[7][5] = dom[5] = domingo5.getBackground();
        color[7][6] = dom[6] = domingo6.getBackground();
        color[7][7] = dom[7] = domingo7.getBackground();
        color[7][8] = dom[8] = domingo8.getBackground();
        color[7][9] = dom[9] = domingo9.getBackground();
        color[7][10] = dom[10] = domingo10.getBackground();
        color[7][11] = dom[11] = domingo11.getBackground();
        color[7][12] = dom[12] = domingo12.getBackground();
        color[7][13] = dom[13] = domingo13.getBackground();
        color[7][14] = dom[14] = domingo14.getBackground();
        color[7][15] = dom[15] = domingo15.getBackground();
        color[7][16] = dom[16] = domingo16.getBackground();
        color[7][17] = dom[17] = domingo17.getBackground();
        color[7][18] = dom[18] = domingo18.getBackground();
        color[7][19] = dom[19] = domingo19.getBackground();
        color[7][20] = dom[20] = domingo20.getBackground();
        color[7][21] = dom[21] = domingo21.getBackground();
        color[7][22] = dom[22] = domingo22.getBackground();
        color[7][23] = dom[23] = domingo23.getBackground();
        color[7][24] = dom[24] = domingo24.getBackground();
    }
    
    public void setWhite(){
        panel1.setBackground(Color.white);
        panel2.setBackground(Color.white);
        panel3.setBackground(Color.white);
        panel4.setBackground(Color.white);
        panel5.setBackground(Color.white);
        panel6.setBackground(Color.white);
        panel7.setBackground(Color.white);
        panel8.setBackground(Color.white);
        panel9.setBackground(Color.white);
        panel10.setBackground(Color.white);
        panel11.setBackground(Color.white);
        panel12.setBackground(Color.white);
        panel13.setBackground(Color.white);
        panel14.setBackground(Color.white);
        panel15.setBackground(Color.white);
        panel16.setBackground(Color.white);
        panel17.setBackground(Color.white);
        panel18.setBackground(Color.white);
        panel19.setBackground(Color.white);
        panel20.setBackground(Color.white);
        panel21.setBackground(Color.white);
        panel22.setBackground(Color.white);
        panel23.setBackground(Color.white);
        panel24.setBackground(Color.white);
    }
    
    public void setWhiteDias(){
        pLunes.setBackground(Color.white);
        pMartes.setBackground(Color.white);
        pMiercoles.setBackground(Color.white);
        pJueves.setBackground(Color.white);
        pViernes.setBackground(Color.white);
        pSabado.setBackground(Color.white);
        pDomingo.setBackground(Color.white);
    }
    
    public boolean existe(String proyecto){
        boolean ex = false;
        for (int i = 0; i < proyectos.size(); i++) {
            if(proyectos.get(i).equals(proyecto)){
                ex = true;
            }
        }
        return ex;
    }
    
    public void insertBD(String proyecto){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat s2 = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = null;
            switch (col) {
                case 1:
                    fecha = lunes.getText();
                    break;
                case 2:
                    fecha = martes.getText();
                    break;
                case 3:
                    fecha = miercoles.getText();
                    break;
                case 4:
                    fecha = jueves.getText();
                    break;
                case 5:
                    fecha = viernes.getText();
                    break;
                case 6:
                    fecha = sabado.getText();
                    break;
                case 7:
                    fecha = domingo.getText();
                    break;
                default:
                    break;
            }
            Date d = s2.parse(fecha);
            fecha = s1.format(d);
            String sql = "select * from htpp where NumEmpleado like '"+numEmpleado+"' and Hora = '"+fila+"' and Fecha = '"+fecha+"'";
            ResultSet rs = st.executeQuery(sql);
            String existe = null;
            String id = null;
            while(rs.next()){
                existe = rs.getString("Proyecto");
                id = rs.getString("Id");
            }
            System.out.println(id);
            if(existe == null){
                String sql2 = "insert htpp (Fecha, NumEmpleado, Proyecto, Hora) values(?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, fecha);
                pst.setString(2, numEmpleado);
                pst.setString(3, proyecto);
                pst.setInt(4, fila);

                pst.executeUpdate();
            }else{
                if(proyecto.equals("")){
                    String sql2 = "delete from htpp where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql2);

                    pst.setString(1, id);

                    pst.executeUpdate();
                }else{
                    String sql2 = "update htpp set Proyecto = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql2);

                    pst.setString(1, proyecto);
                    pst.setString(2, id);

                    pst.executeUpdate();
                }
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Cronometro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void focusLost(JTextField dia, Color ac){
        if(existe(dia.getText())){
            if(!dia.getText().equals("")){
                dia.setBackground(ac);
                if(ac.equals(new Color(32,55,100)) || ac.equals(new Color(48,84,150))){
                    dia.setForeground(Color.white);
                }
                try{
                    insertBD(dia.getText());
                }catch(Exception e){
                    
                }
            }
        }else{
                dia.setForeground(Color.black);
                dia.setBackground(Color.white);
                insertBD(dia.getText());
            }
    }
    
    public void limpiar(){
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 25; j++) {
                txt[i][j].setText("");
            }
        }
    }
    
    public void setComp(int x, JTextField lunes, JTextField martes, JTextField miercoles, JTextField jueves, JTextField viernes, JTextField sabado, JTextField domingo){
        txt[1][x] = lunes;
        txt[2][x] = martes;
        txt[3][x] = miercoles;
        txt[4][x] = jueves;
        txt[5][x] = viernes;
        txt[6][x] = sabado;
        txt[7][x] = domingo;
    }
    
    public void getComp(){
        txt = new JTextField[9][26];
        //-------------------------------------
        setComp(1, Lunes1, martes1, miercoles1, jueves1, viernes1, sabado1, domingo1);
        //-------------------------------------
        setComp(2, Lunes2, martes2, miercoles2, jueves2, viernes2, sabado2, domingo2);
        //-------------------------------------
        setComp(3, Lunes3, martes3, miercoles3, jueves3, viernes3, sabado3, domingo3);
        //-------------------------------------
        setComp(4, Lunes4, martes4, miercoles4, jueves4, viernes4, sabado4, domingo4);
        //-------------------------------------
        setComp(5, Lunes5, martes5, miercoles5, jueves5, viernes5, sabado5, domingo5);
        //-------------------------------------
        setComp(6, Lunes6, martes6, miercoles6, jueves6, viernes6, sabado6, domingo6);
        //-------------------------------------
        setComp(7, Lunes7, martes7, miercoles7, jueves7, viernes7, sabado7, domingo7);
        //-------------------------------------
        setComp(8, Lunes8, martes8, miercoles8, jueves8, viernes8, sabado8, domingo8);
        //-------------------------------------
        setComp(9, Lunes9, martes9, miercoles9, jueves9, viernes9, sabado9, domingo9);
        //-------------------------------------
        setComp(10, Lunes10, martes10, miercoles10, jueves10, viernes10, sabado10, domingo10);
        //-------------------------------------
        setComp(11, Lunes11, martes11, miercoles11, jueves11, viernes11, sabado11, domingo11);
        //-------------------------------------
        setComp(12, Lunes12, martes12, miercoles12, jueves12, viernes12, sabado12, domingo12);
        //-------------------------------------
        setComp(13, Lunes13, martes13, miercoles13, jueves13, viernes13, sabado13, domingo13);
        //-------------------------------------
        setComp(14, Lunes14, martes14, miercoles14, jueves14, viernes14, sabado14, domingo14);
        //-------------------------------------
        setComp(15, Lunes15, martes15, miercoles15, jueves15, viernes15, sabado15, domingo15);
        //-------------------------------------
        setComp(16, Lunes16, martes16, miercoles16, jueves16, viernes16, sabado16, domingo16);
        //-------------------------------------
        setComp(17, Lunes17, martes17, miercoles17, jueves17, viernes17, sabado17, domingo17);
        //-------------------------------------
        setComp(18, Lunes18, martes18, miercoles18, jueves18, viernes18, sabado18, domingo18);
        //-------------------------------------
        setComp(19, Lunes19, martes19, miercoles19, jueves19, viernes19, sabado19, domingo19);
        //-------------------------------------
        setComp(20, Lunes20, martes20, miercoles20, jueves20, viernes20, sabado20, domingo20);
        //-------------------------------------
        setComp(21, Lunes21, martes21, miercoles21, jueves21, viernes21, sabado21, domingo21);
        //-------------------------------------
        setComp(22, Lunes22, martes22, miercoles22, jueves22, viernes22, sabado22, domingo22);
        //-------------------------------------
        setComp(23, Lunes23, martes23, miercoles23, jueves23, viernes23, sabado23, domingo23);
        //-------------------------------------
        setComp(24, Lunes24, martes24, miercoles24, jueves24, viernes24, sabado24, domingo24);
        //-------------------------------------
    }
    
    public void verDatos(){
        try{
            SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = s1.parse(lunes.getText());
            Date d2 = s1.parse(domingo.getText());
            String lu = s2.format(d1);
            String domi = s2.format(d2);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from htpp where NumEmpleado like '"+numEmpleado+"' and Fecha between '"+lu+"' and '"+domi+"'";
            ResultSet rs = st.executeQuery(sql);
            int hora;
            while(rs.next()){
                Date d;
                hora = rs.getInt("Hora");
                int x=hora,y=0;
                d = s2.parse(rs.getString("Fecha"));
                String fec = s1.format(d);
                if(fec.equals(lunes.getText())){
                    y = 1;
                }else if(fec.equals(martes.getText())){
                    y = 2;
                }else if(fec.equals(miercoles.getText())){
                    y = 3;
                }else if(fec.equals(jueves.getText())){
                    y = 4;
                }else if(fec.equals(viernes.getText())){
                    y = 5;
                }else if(fec.equals(sabado.getText())){
                    y = 6;
                }else if(fec.equals(domingo.getText())){
                    y = 7;
                }
                if(x != 0 && y != 0){
                    txt[y][x].setText(rs.getString("Proyecto"));
                    focusLost(txt[y][x], color[y][x]);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Cronometro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verDepa(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumEmpleado, Puesto from registroempleados where NumEmpleado like '"+numEmpleado+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lblDepa.setText(rs.getString("Puesto"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Cronometro(String id) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        numEmpleado = id;
        insertarSemanas();
        getColor();
        setBlanco();
        addAuto();
        cmbSemanas.setSelectedIndex(0);
        getComp();
        verDepa();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        opciones = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbSemanas = new RSMaterialComponent.RSComboBoxMaterial();
        lblYear = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        lblDepa = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        pLunes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pMartes = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pMiercoles = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pJueves = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pViernes = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        pSabado = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        pDomingo = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lunes = new javax.swing.JLabel();
        martes = new javax.swing.JLabel();
        miercoles = new javax.swing.JLabel();
        jueves = new javax.swing.JLabel();
        viernes = new javax.swing.JLabel();
        sabado = new javax.swing.JLabel();
        domingo = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        panel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        panel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        panel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        panel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        panel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        panel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        panel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        panel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        panel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        panel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        panel13 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        panel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        panel15 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        panel16 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        panel17 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        panel18 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        panel19 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        panel20 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        panel21 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        panel22 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        panel23 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        panel24 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        Lunes1 = new javax.swing.JTextField();
        Lunes2 = new javax.swing.JTextField();
        Lunes3 = new javax.swing.JTextField();
        Lunes4 = new javax.swing.JTextField();
        Lunes5 = new javax.swing.JTextField();
        Lunes6 = new javax.swing.JTextField();
        Lunes7 = new javax.swing.JTextField();
        Lunes8 = new javax.swing.JTextField();
        Lunes9 = new javax.swing.JTextField();
        Lunes10 = new javax.swing.JTextField();
        Lunes11 = new javax.swing.JTextField();
        Lunes12 = new javax.swing.JTextField();
        Lunes13 = new javax.swing.JTextField();
        Lunes14 = new javax.swing.JTextField();
        Lunes15 = new javax.swing.JTextField();
        Lunes16 = new javax.swing.JTextField();
        Lunes17 = new javax.swing.JTextField();
        Lunes18 = new javax.swing.JTextField();
        Lunes19 = new javax.swing.JTextField();
        Lunes20 = new javax.swing.JTextField();
        Lunes21 = new javax.swing.JTextField();
        Lunes22 = new javax.swing.JTextField();
        Lunes23 = new javax.swing.JTextField();
        Lunes24 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        martes1 = new javax.swing.JTextField();
        martes2 = new javax.swing.JTextField();
        martes3 = new javax.swing.JTextField();
        martes4 = new javax.swing.JTextField();
        martes5 = new javax.swing.JTextField();
        martes6 = new javax.swing.JTextField();
        martes7 = new javax.swing.JTextField();
        martes8 = new javax.swing.JTextField();
        martes9 = new javax.swing.JTextField();
        martes10 = new javax.swing.JTextField();
        martes11 = new javax.swing.JTextField();
        martes12 = new javax.swing.JTextField();
        martes13 = new javax.swing.JTextField();
        martes14 = new javax.swing.JTextField();
        martes15 = new javax.swing.JTextField();
        martes16 = new javax.swing.JTextField();
        martes17 = new javax.swing.JTextField();
        martes18 = new javax.swing.JTextField();
        martes19 = new javax.swing.JTextField();
        martes20 = new javax.swing.JTextField();
        martes21 = new javax.swing.JTextField();
        martes22 = new javax.swing.JTextField();
        martes23 = new javax.swing.JTextField();
        martes24 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        miercoles1 = new javax.swing.JTextField();
        miercoles2 = new javax.swing.JTextField();
        miercoles3 = new javax.swing.JTextField();
        miercoles4 = new javax.swing.JTextField();
        miercoles5 = new javax.swing.JTextField();
        miercoles6 = new javax.swing.JTextField();
        miercoles7 = new javax.swing.JTextField();
        miercoles8 = new javax.swing.JTextField();
        miercoles9 = new javax.swing.JTextField();
        miercoles10 = new javax.swing.JTextField();
        miercoles11 = new javax.swing.JTextField();
        miercoles12 = new javax.swing.JTextField();
        miercoles13 = new javax.swing.JTextField();
        miercoles14 = new javax.swing.JTextField();
        miercoles15 = new javax.swing.JTextField();
        miercoles16 = new javax.swing.JTextField();
        miercoles17 = new javax.swing.JTextField();
        miercoles18 = new javax.swing.JTextField();
        miercoles19 = new javax.swing.JTextField();
        miercoles20 = new javax.swing.JTextField();
        miercoles21 = new javax.swing.JTextField();
        miercoles22 = new javax.swing.JTextField();
        miercoles23 = new javax.swing.JTextField();
        miercoles24 = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jueves1 = new javax.swing.JTextField();
        jueves2 = new javax.swing.JTextField();
        jueves3 = new javax.swing.JTextField();
        jueves4 = new javax.swing.JTextField();
        jueves5 = new javax.swing.JTextField();
        jueves6 = new javax.swing.JTextField();
        jueves7 = new javax.swing.JTextField();
        jueves8 = new javax.swing.JTextField();
        jueves9 = new javax.swing.JTextField();
        jueves10 = new javax.swing.JTextField();
        jueves11 = new javax.swing.JTextField();
        jueves12 = new javax.swing.JTextField();
        jueves13 = new javax.swing.JTextField();
        jueves14 = new javax.swing.JTextField();
        jueves15 = new javax.swing.JTextField();
        jueves16 = new javax.swing.JTextField();
        jueves17 = new javax.swing.JTextField();
        jueves18 = new javax.swing.JTextField();
        jueves19 = new javax.swing.JTextField();
        jueves20 = new javax.swing.JTextField();
        jueves21 = new javax.swing.JTextField();
        jueves22 = new javax.swing.JTextField();
        jueves23 = new javax.swing.JTextField();
        jueves24 = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        viernes1 = new javax.swing.JTextField();
        viernes2 = new javax.swing.JTextField();
        viernes3 = new javax.swing.JTextField();
        viernes4 = new javax.swing.JTextField();
        viernes5 = new javax.swing.JTextField();
        viernes6 = new javax.swing.JTextField();
        viernes7 = new javax.swing.JTextField();
        viernes8 = new javax.swing.JTextField();
        viernes9 = new javax.swing.JTextField();
        viernes10 = new javax.swing.JTextField();
        viernes11 = new javax.swing.JTextField();
        viernes12 = new javax.swing.JTextField();
        viernes13 = new javax.swing.JTextField();
        viernes14 = new javax.swing.JTextField();
        viernes15 = new javax.swing.JTextField();
        viernes16 = new javax.swing.JTextField();
        viernes17 = new javax.swing.JTextField();
        viernes18 = new javax.swing.JTextField();
        viernes19 = new javax.swing.JTextField();
        viernes20 = new javax.swing.JTextField();
        viernes21 = new javax.swing.JTextField();
        viernes22 = new javax.swing.JTextField();
        viernes23 = new javax.swing.JTextField();
        viernes24 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        sabado1 = new javax.swing.JTextField();
        sabado2 = new javax.swing.JTextField();
        sabado3 = new javax.swing.JTextField();
        sabado4 = new javax.swing.JTextField();
        sabado5 = new javax.swing.JTextField();
        sabado6 = new javax.swing.JTextField();
        sabado7 = new javax.swing.JTextField();
        sabado8 = new javax.swing.JTextField();
        sabado9 = new javax.swing.JTextField();
        sabado10 = new javax.swing.JTextField();
        sabado11 = new javax.swing.JTextField();
        sabado12 = new javax.swing.JTextField();
        sabado13 = new javax.swing.JTextField();
        sabado14 = new javax.swing.JTextField();
        sabado15 = new javax.swing.JTextField();
        sabado16 = new javax.swing.JTextField();
        sabado17 = new javax.swing.JTextField();
        sabado18 = new javax.swing.JTextField();
        sabado19 = new javax.swing.JTextField();
        sabado20 = new javax.swing.JTextField();
        sabado21 = new javax.swing.JTextField();
        sabado22 = new javax.swing.JTextField();
        sabado23 = new javax.swing.JTextField();
        sabado24 = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        domingo1 = new javax.swing.JTextField();
        domingo2 = new javax.swing.JTextField();
        domingo3 = new javax.swing.JTextField();
        domingo4 = new javax.swing.JTextField();
        domingo5 = new javax.swing.JTextField();
        domingo6 = new javax.swing.JTextField();
        domingo7 = new javax.swing.JTextField();
        domingo8 = new javax.swing.JTextField();
        domingo9 = new javax.swing.JTextField();
        domingo10 = new javax.swing.JTextField();
        domingo11 = new javax.swing.JTextField();
        domingo12 = new javax.swing.JTextField();
        domingo13 = new javax.swing.JTextField();
        domingo14 = new javax.swing.JTextField();
        domingo15 = new javax.swing.JTextField();
        domingo16 = new javax.swing.JTextField();
        domingo17 = new javax.swing.JTextField();
        domingo18 = new javax.swing.JTextField();
        domingo19 = new javax.swing.JTextField();
        domingo20 = new javax.swing.JTextField();
        domingo21 = new javax.swing.JTextField();
        domingo22 = new javax.swing.JTextField();
        domingo23 = new javax.swing.JTextField();
        domingo24 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();

        jMenuItem2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem2.setText("Opciones");
        jMenuItem2.setEnabled(false);
        opciones.add(jMenuItem2);

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/documento.png"))); // NOI18N
        jMenuItem1.setText("      Agregar notas                 ");
        opciones.add(jMenuItem1);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(" X ");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        btnSalir.add(jLabel1);

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 102, 0));
        jLabel9.setText("HTPP");
        jPanel3.add(jLabel9);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Semana:");
        jPanel20.add(jLabel2);

        cmbSemanas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar" }));
        cmbSemanas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSemanasActionPerformed(evt);
            }
        });
        jPanel20.add(cmbSemanas);

        lblYear.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblYear.setForeground(new java.awt.Color(0, 0, 0));
        lblYear.setText("Año");
        jPanel20.add(lblYear);

        jPanel5.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        lblDepa.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblDepa.setForeground(new java.awt.Color(102, 0, 0));
        lblDepa.setText("Departamento");
        jPanel21.add(lblDepa);

        jPanel5.add(jPanel21, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(1, 7, 10, 0));

        pLunes.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Lunes");
        pLunes.add(jLabel3);

        jPanel7.add(pLunes);

        pMartes.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Martes");
        pMartes.add(jLabel4);

        jPanel7.add(pMartes);

        pMiercoles.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Miercoles");
        pMiercoles.add(jLabel5);

        jPanel7.add(pMiercoles);

        pJueves.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Jueves");
        pJueves.add(jLabel6);

        jPanel7.add(pJueves);

        pViernes.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Viernes");
        pViernes.add(jLabel7);

        jPanel7.add(pViernes);

        pSabado.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Sabado");
        pSabado.add(jLabel8);

        jPanel7.add(pSabado);

        pDomingo.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Domingo");
        pDomingo.add(jLabel10);

        jPanel7.add(pDomingo);

        jPanel6.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        lunes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lunes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lunes.setText("  /  /");
        jPanel9.add(lunes);

        martes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        martes.setText("  /  /");
        jPanel9.add(martes);

        miercoles.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        miercoles.setText("  /  /");
        jPanel9.add(miercoles);

        jueves.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jueves.setText("  /  /");
        jPanel9.add(jueves);

        viernes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        viernes.setText("  /  /");
        jPanel9.add(viernes);

        sabado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sabado.setText("  /  /");
        jPanel9.add(sabado);

        domingo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        domingo.setText("  /  /");
        jPanel9.add(domingo);

        jPanel8.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(24, 5, 5, 5));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setBackground(new java.awt.Color(0, 153, 51));
        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("     01     ");
        panel1.add(jLabel12);

        jPanel10.add(panel1);

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(0, 153, 51));
        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("02");
        panel2.add(jLabel13);

        jPanel10.add(panel2);

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setBackground(new java.awt.Color(0, 153, 51));
        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("03");
        panel3.add(jLabel14);

        jPanel10.add(panel3);

        panel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(0, 153, 51));
        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("04");
        panel4.add(jLabel15);

        jPanel10.add(panel4);

        panel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setBackground(new java.awt.Color(0, 153, 51));
        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("05");
        panel5.add(jLabel16);

        jPanel10.add(panel5);

        panel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setBackground(new java.awt.Color(0, 153, 51));
        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("06");
        panel6.add(jLabel17);

        jPanel10.add(panel6);

        panel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setBackground(new java.awt.Color(0, 153, 51));
        jLabel18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("07");
        panel7.add(jLabel18);

        jPanel10.add(panel7);

        panel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setBackground(new java.awt.Color(0, 153, 51));
        jLabel19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("08");
        panel8.add(jLabel19);

        jPanel10.add(panel8);

        panel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setBackground(new java.awt.Color(0, 153, 51));
        jLabel20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("09");
        panel9.add(jLabel20);

        jPanel10.add(panel9);

        panel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setBackground(new java.awt.Color(0, 153, 51));
        jLabel21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("10");
        panel10.add(jLabel21);

        jPanel10.add(panel10);

        panel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setBackground(new java.awt.Color(0, 153, 51));
        jLabel22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("11");
        panel11.add(jLabel22);

        jPanel10.add(panel11);

        panel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setBackground(new java.awt.Color(0, 153, 51));
        jLabel23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("12");
        panel12.add(jLabel23);

        jPanel10.add(panel12);

        panel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setBackground(new java.awt.Color(0, 153, 51));
        jLabel24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("13");
        panel13.add(jLabel24);

        jPanel10.add(panel13);

        panel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setBackground(new java.awt.Color(0, 153, 51));
        jLabel25.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("14");
        panel14.add(jLabel25);

        jPanel10.add(panel14);

        panel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel26.setBackground(new java.awt.Color(0, 153, 51));
        jLabel26.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("15");
        panel15.add(jLabel26);

        jPanel10.add(panel15);

        panel16.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setBackground(new java.awt.Color(0, 153, 51));
        jLabel27.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("16");
        panel16.add(jLabel27);

        jPanel10.add(panel16);

        panel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setBackground(new java.awt.Color(0, 153, 51));
        jLabel28.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("17");
        panel17.add(jLabel28);

        jPanel10.add(panel17);

        panel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setBackground(new java.awt.Color(0, 153, 51));
        jLabel29.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("18");
        panel18.add(jLabel29);

        jPanel10.add(panel18);

        panel19.setBackground(new java.awt.Color(255, 255, 255));

        jLabel30.setBackground(new java.awt.Color(0, 153, 51));
        jLabel30.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("19");
        panel19.add(jLabel30);

        jPanel10.add(panel19);

        panel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setBackground(new java.awt.Color(0, 153, 51));
        jLabel31.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("20");
        panel20.add(jLabel31);

        jPanel10.add(panel20);

        panel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setBackground(new java.awt.Color(0, 153, 51));
        jLabel32.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("21");
        panel21.add(jLabel32);

        jPanel10.add(panel21);

        panel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setBackground(new java.awt.Color(0, 153, 51));
        jLabel33.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("22");
        panel22.add(jLabel33);

        jPanel10.add(panel22);

        panel23.setBackground(new java.awt.Color(255, 255, 255));

        jLabel34.setBackground(new java.awt.Color(0, 153, 51));
        jLabel34.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("23");
        panel23.add(jLabel34);

        jPanel10.add(panel23);

        panel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel36.setBackground(new java.awt.Color(0, 153, 51));
        jLabel36.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("24");
        panel24.add(jLabel36);

        jPanel10.add(panel24);

        jPanel8.add(jPanel10, java.awt.BorderLayout.WEST);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(1, 7, 5, 0));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.GridLayout(24, 0, 0, 5));

        Lunes1.setBackground(new java.awt.Color(217, 225, 242));
        Lunes1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes1.setForeground(new java.awt.Color(0, 0, 0));
        Lunes1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes1.setComponentPopupMenu(opciones);
        Lunes1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes1FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes1FocusLost(evt);
            }
        });
        jPanel12.add(Lunes1);

        Lunes2.setBackground(new java.awt.Color(180, 198, 231));
        Lunes2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes2.setForeground(new java.awt.Color(0, 0, 0));
        Lunes2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes2.setComponentPopupMenu(opciones);
        Lunes2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo2FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes2FocusLost(evt);
            }
        });
        jPanel12.add(Lunes2);

        Lunes3.setBackground(new java.awt.Color(142, 169, 219));
        Lunes3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes3.setForeground(new java.awt.Color(0, 0, 0));
        Lunes3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes3.setComponentPopupMenu(opciones);
        Lunes3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes3FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes3FocusLost(evt);
            }
        });
        jPanel12.add(Lunes3);

        Lunes4.setBackground(new java.awt.Color(48, 84, 150));
        Lunes4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes4.setForeground(new java.awt.Color(0, 0, 0));
        Lunes4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes4.setComponentPopupMenu(opciones);
        Lunes4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes4FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes4FocusLost(evt);
            }
        });
        jPanel12.add(Lunes4);

        Lunes5.setBackground(new java.awt.Color(32, 55, 100));
        Lunes5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes5.setForeground(new java.awt.Color(0, 0, 0));
        Lunes5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes5.setComponentPopupMenu(opciones);
        Lunes5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo5FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes5FocusLost(evt);
            }
        });
        jPanel12.add(Lunes5);

        Lunes6.setBackground(new java.awt.Color(32, 55, 100));
        Lunes6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes6.setForeground(new java.awt.Color(0, 0, 0));
        Lunes6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes6.setComponentPopupMenu(opciones);
        Lunes6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves6FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes6FocusLost(evt);
            }
        });
        jPanel12.add(Lunes6);

        Lunes7.setBackground(new java.awt.Color(48, 84, 150));
        Lunes7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes7.setForeground(new java.awt.Color(0, 0, 0));
        Lunes7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes7.setComponentPopupMenu(opciones);
        Lunes7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles7FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes7FocusLost(evt);
            }
        });
        jPanel12.add(Lunes7);

        Lunes8.setBackground(new java.awt.Color(142, 169, 219));
        Lunes8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes8.setForeground(new java.awt.Color(0, 0, 0));
        Lunes8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes8.setComponentPopupMenu(opciones);
        Lunes8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles8FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes8FocusLost(evt);
            }
        });
        jPanel12.add(Lunes8);

        Lunes9.setBackground(new java.awt.Color(180, 198, 231));
        Lunes9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes9.setForeground(new java.awt.Color(0, 0, 0));
        Lunes9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes9.setComponentPopupMenu(opciones);
        Lunes9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes9FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes9FocusLost(evt);
            }
        });
        jPanel12.add(Lunes9);

        Lunes10.setBackground(new java.awt.Color(217, 225, 242));
        Lunes10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes10.setForeground(new java.awt.Color(0, 0, 0));
        Lunes10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes10.setComponentPopupMenu(opciones);
        Lunes10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes10FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes10FocusLost(evt);
            }
        });
        jPanel12.add(Lunes10);

        Lunes11.setBackground(new java.awt.Color(217, 225, 242));
        Lunes11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes11.setForeground(new java.awt.Color(0, 0, 0));
        Lunes11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes11.setComponentPopupMenu(opciones);
        Lunes11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo11FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes11FocusLost(evt);
            }
        });
        jPanel12.add(Lunes11);

        Lunes12.setBackground(new java.awt.Color(180, 198, 231));
        Lunes12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes12.setForeground(new java.awt.Color(0, 0, 0));
        Lunes12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes12.setComponentPopupMenu(opciones);
        Lunes12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes12FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes12FocusLost(evt);
            }
        });
        jPanel12.add(Lunes12);

        Lunes13.setBackground(new java.awt.Color(142, 169, 219));
        Lunes13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes13.setForeground(new java.awt.Color(0, 0, 0));
        Lunes13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes13.setComponentPopupMenu(opciones);
        Lunes13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes13FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes13FocusLost(evt);
            }
        });
        jPanel12.add(Lunes13);

        Lunes14.setBackground(new java.awt.Color(48, 84, 150));
        Lunes14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes14.setForeground(new java.awt.Color(0, 0, 0));
        Lunes14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes14.setComponentPopupMenu(opciones);
        Lunes14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo14FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes14FocusLost(evt);
            }
        });
        jPanel12.add(Lunes14);

        Lunes15.setBackground(new java.awt.Color(32, 55, 100));
        Lunes15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes15.setForeground(new java.awt.Color(0, 0, 0));
        Lunes15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes15.setComponentPopupMenu(opciones);
        Lunes15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves15FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes15FocusLost(evt);
            }
        });
        jPanel12.add(Lunes15);

        Lunes16.setBackground(new java.awt.Color(32, 55, 100));
        Lunes16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes16.setForeground(new java.awt.Color(0, 0, 0));
        Lunes16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes16.setComponentPopupMenu(opciones);
        Lunes16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves16FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes16FocusLost(evt);
            }
        });
        jPanel12.add(Lunes16);

        Lunes17.setBackground(new java.awt.Color(48, 84, 150));
        Lunes17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes17.setForeground(new java.awt.Color(0, 0, 0));
        Lunes17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes17.setComponentPopupMenu(opciones);
        Lunes17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado17FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes17FocusLost(evt);
            }
        });
        jPanel12.add(Lunes17);

        Lunes18.setBackground(new java.awt.Color(142, 169, 219));
        Lunes18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes18.setForeground(new java.awt.Color(0, 0, 0));
        Lunes18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes18.setComponentPopupMenu(opciones);
        Lunes18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado18FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes18FocusLost(evt);
            }
        });
        jPanel12.add(Lunes18);

        Lunes19.setBackground(new java.awt.Color(180, 198, 231));
        Lunes19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes19.setForeground(new java.awt.Color(0, 0, 0));
        Lunes19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes19.setComponentPopupMenu(opciones);
        Lunes19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles19FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes19FocusLost(evt);
            }
        });
        jPanel12.add(Lunes19);

        Lunes20.setBackground(new java.awt.Color(217, 225, 242));
        Lunes20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes20.setForeground(new java.awt.Color(0, 0, 0));
        Lunes20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes20.setComponentPopupMenu(opciones);
        Lunes20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes20FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes20FocusLost(evt);
            }
        });
        jPanel12.add(Lunes20);

        Lunes21.setBackground(new java.awt.Color(217, 225, 242));
        Lunes21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes21.setForeground(new java.awt.Color(0, 0, 0));
        Lunes21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes21.setComponentPopupMenu(opciones);
        Lunes21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo21FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes21FocusLost(evt);
            }
        });
        jPanel12.add(Lunes21);

        Lunes22.setBackground(new java.awt.Color(180, 198, 231));
        Lunes22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes22.setForeground(new java.awt.Color(0, 0, 0));
        Lunes22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes22.setComponentPopupMenu(opciones);
        Lunes22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado22FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes22FocusLost(evt);
            }
        });
        jPanel12.add(Lunes22);

        Lunes23.setBackground(new java.awt.Color(142, 169, 219));
        Lunes23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes23.setForeground(new java.awt.Color(0, 0, 0));
        Lunes23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes23.setComponentPopupMenu(opciones);
        Lunes23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado23FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes23FocusLost(evt);
            }
        });
        jPanel12.add(Lunes23);

        Lunes24.setBackground(new java.awt.Color(48, 84, 150));
        Lunes24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lunes24.setForeground(new java.awt.Color(0, 0, 0));
        Lunes24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Lunes24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        Lunes24.setComponentPopupMenu(opciones);
        Lunes24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes24FocusGained(evt);
                focus1(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Lunes24FocusLost(evt);
            }
        });
        jPanel12.add(Lunes24);

        jPanel11.add(jPanel12);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(24, 0, 0, 5));

        martes1.setBackground(new java.awt.Color(32, 55, 100));
        martes1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes1.setForeground(new java.awt.Color(0, 0, 0));
        martes1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes1.setComponentPopupMenu(opciones);
        martes1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes1FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes1FocusLost(evt);
            }
        });
        jPanel14.add(martes1);

        martes2.setBackground(new java.awt.Color(32, 55, 100));
        martes2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes2.setForeground(new java.awt.Color(0, 0, 0));
        martes2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes2.setComponentPopupMenu(opciones);
        martes2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo2FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes2FocusLost(evt);
            }
        });
        jPanel14.add(martes2);

        martes3.setBackground(new java.awt.Color(48, 84, 150));
        martes3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes3.setForeground(new java.awt.Color(0, 0, 0));
        martes3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes3.setComponentPopupMenu(opciones);
        martes3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes3FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes3FocusLost(evt);
            }
        });
        jPanel14.add(martes3);

        martes4.setBackground(new java.awt.Color(142, 169, 219));
        martes4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes4.setForeground(new java.awt.Color(0, 0, 0));
        martes4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes4.setComponentPopupMenu(opciones);
        martes4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes4FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes4FocusLost(evt);
            }
        });
        jPanel14.add(martes4);

        martes5.setBackground(new java.awt.Color(180, 198, 231));
        martes5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes5.setForeground(new java.awt.Color(0, 0, 0));
        martes5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes5.setComponentPopupMenu(opciones);
        martes5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo5FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes5FocusLost(evt);
            }
        });
        jPanel14.add(martes5);

        martes6.setBackground(new java.awt.Color(217, 225, 242));
        martes6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes6.setForeground(new java.awt.Color(0, 0, 0));
        martes6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes6.setComponentPopupMenu(opciones);
        martes6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves6FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes6FocusLost(evt);
            }
        });
        jPanel14.add(martes6);

        martes7.setBackground(new java.awt.Color(217, 225, 242));
        martes7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes7.setForeground(new java.awt.Color(0, 0, 0));
        martes7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes7.setComponentPopupMenu(opciones);
        martes7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles7FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes7FocusLost(evt);
            }
        });
        jPanel14.add(martes7);

        martes8.setBackground(new java.awt.Color(180, 198, 231));
        martes8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes8.setForeground(new java.awt.Color(0, 0, 0));
        martes8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes8.setComponentPopupMenu(opciones);
        martes8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles8FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes8FocusLost(evt);
            }
        });
        jPanel14.add(martes8);

        martes9.setBackground(new java.awt.Color(142, 169, 219));
        martes9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes9.setForeground(new java.awt.Color(0, 0, 0));
        martes9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes9.setComponentPopupMenu(opciones);
        martes9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes9FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes9FocusLost(evt);
            }
        });
        jPanel14.add(martes9);

        martes10.setBackground(new java.awt.Color(48, 84, 150));
        martes10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes10.setForeground(new java.awt.Color(0, 0, 0));
        martes10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes10.setComponentPopupMenu(opciones);
        martes10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes10FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes10FocusLost(evt);
            }
        });
        jPanel14.add(martes10);

        martes11.setBackground(new java.awt.Color(32, 55, 100));
        martes11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes11.setForeground(new java.awt.Color(0, 0, 0));
        martes11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes11.setComponentPopupMenu(opciones);
        martes11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo11FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes11FocusLost(evt);
            }
        });
        jPanel14.add(martes11);

        martes12.setBackground(new java.awt.Color(32, 55, 100));
        martes12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes12.setForeground(new java.awt.Color(0, 0, 0));
        martes12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes12.setComponentPopupMenu(opciones);
        martes12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes12FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes12FocusLost(evt);
            }
        });
        jPanel14.add(martes12);

        martes13.setBackground(new java.awt.Color(48, 84, 150));
        martes13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes13.setForeground(new java.awt.Color(0, 0, 0));
        martes13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes13.setComponentPopupMenu(opciones);
        martes13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes13FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes13FocusLost(evt);
            }
        });
        jPanel14.add(martes13);

        martes14.setBackground(new java.awt.Color(142, 169, 219));
        martes14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes14.setForeground(new java.awt.Color(0, 0, 0));
        martes14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes14.setComponentPopupMenu(opciones);
        martes14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo14FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes14FocusLost(evt);
            }
        });
        jPanel14.add(martes14);

        martes15.setBackground(new java.awt.Color(180, 198, 231));
        martes15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes15.setForeground(new java.awt.Color(0, 0, 0));
        martes15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes15.setComponentPopupMenu(opciones);
        martes15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves15FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes15FocusLost(evt);
            }
        });
        jPanel14.add(martes15);

        martes16.setBackground(new java.awt.Color(217, 225, 242));
        martes16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes16.setForeground(new java.awt.Color(0, 0, 0));
        martes16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes16.setComponentPopupMenu(opciones);
        martes16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves16FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes16FocusLost(evt);
            }
        });
        jPanel14.add(martes16);

        martes17.setBackground(new java.awt.Color(217, 225, 242));
        martes17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes17.setForeground(new java.awt.Color(0, 0, 0));
        martes17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes17.setComponentPopupMenu(opciones);
        martes17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado17FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes17FocusLost(evt);
            }
        });
        jPanel14.add(martes17);

        martes18.setBackground(new java.awt.Color(180, 198, 231));
        martes18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes18.setForeground(new java.awt.Color(0, 0, 0));
        martes18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes18.setComponentPopupMenu(opciones);
        martes18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado18FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes18FocusLost(evt);
            }
        });
        jPanel14.add(martes18);

        martes19.setBackground(new java.awt.Color(142, 169, 219));
        martes19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes19.setForeground(new java.awt.Color(0, 0, 0));
        martes19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes19.setComponentPopupMenu(opciones);
        martes19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles19FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes19FocusLost(evt);
            }
        });
        jPanel14.add(martes19);

        martes20.setBackground(new java.awt.Color(48, 84, 150));
        martes20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes20.setForeground(new java.awt.Color(0, 0, 0));
        martes20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes20.setComponentPopupMenu(opciones);
        martes20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes20FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes20FocusLost(evt);
            }
        });
        jPanel14.add(martes20);

        martes21.setBackground(new java.awt.Color(32, 55, 100));
        martes21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes21.setForeground(new java.awt.Color(0, 0, 0));
        martes21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes21.setComponentPopupMenu(opciones);
        martes21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo21FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes21FocusLost(evt);
            }
        });
        jPanel14.add(martes21);

        martes22.setBackground(new java.awt.Color(32, 55, 100));
        martes22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes22.setForeground(new java.awt.Color(0, 0, 0));
        martes22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes22.setComponentPopupMenu(opciones);
        martes22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado22FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes22FocusLost(evt);
            }
        });
        jPanel14.add(martes22);

        martes23.setBackground(new java.awt.Color(48, 84, 150));
        martes23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes23.setForeground(new java.awt.Color(0, 0, 0));
        martes23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes23.setComponentPopupMenu(opciones);
        martes23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado23FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes23FocusLost(evt);
            }
        });
        jPanel14.add(martes23);

        martes24.setBackground(new java.awt.Color(142, 169, 219));
        martes24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes24.setForeground(new java.awt.Color(0, 0, 0));
        martes24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        martes24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        martes24.setComponentPopupMenu(opciones);
        martes24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes24FocusGained(evt);
                focus2(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                martes24FocusLost(evt);
            }
        });
        jPanel14.add(martes24);

        jPanel11.add(jPanel14);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.GridLayout(24, 0, 0, 5));

        miercoles1.setBackground(new java.awt.Color(180, 198, 231));
        miercoles1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles1.setForeground(new java.awt.Color(0, 0, 0));
        miercoles1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles1.setComponentPopupMenu(opciones);
        miercoles1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes1FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles1FocusLost(evt);
            }
        });
        jPanel15.add(miercoles1);

        miercoles2.setBackground(new java.awt.Color(217, 225, 242));
        miercoles2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles2.setForeground(new java.awt.Color(0, 0, 0));
        miercoles2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles2.setComponentPopupMenu(opciones);
        miercoles2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo2FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles2FocusLost(evt);
            }
        });
        jPanel15.add(miercoles2);

        miercoles3.setBackground(new java.awt.Color(217, 225, 242));
        miercoles3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles3.setForeground(new java.awt.Color(0, 0, 0));
        miercoles3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles3.setComponentPopupMenu(opciones);
        miercoles3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes3FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles3FocusLost(evt);
            }
        });
        jPanel15.add(miercoles3);

        miercoles4.setBackground(new java.awt.Color(180, 198, 231));
        miercoles4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles4.setForeground(new java.awt.Color(0, 0, 0));
        miercoles4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles4.setComponentPopupMenu(opciones);
        miercoles4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes4FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles4FocusLost(evt);
            }
        });
        jPanel15.add(miercoles4);

        miercoles5.setBackground(new java.awt.Color(142, 169, 219));
        miercoles5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles5.setForeground(new java.awt.Color(0, 0, 0));
        miercoles5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles5.setComponentPopupMenu(opciones);
        miercoles5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo5FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles5FocusLost(evt);
            }
        });
        jPanel15.add(miercoles5);

        miercoles6.setBackground(new java.awt.Color(48, 84, 150));
        miercoles6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles6.setForeground(new java.awt.Color(0, 0, 0));
        miercoles6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles6.setComponentPopupMenu(opciones);
        miercoles6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves6FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles6FocusLost(evt);
            }
        });
        jPanel15.add(miercoles6);

        miercoles7.setBackground(new java.awt.Color(32, 55, 100));
        miercoles7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles7.setForeground(new java.awt.Color(0, 0, 0));
        miercoles7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles7.setComponentPopupMenu(opciones);
        miercoles7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles7FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles7FocusLost(evt);
            }
        });
        jPanel15.add(miercoles7);

        miercoles8.setBackground(new java.awt.Color(32, 55, 100));
        miercoles8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles8.setForeground(new java.awt.Color(0, 0, 0));
        miercoles8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles8.setComponentPopupMenu(opciones);
        miercoles8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles8FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles8FocusLost(evt);
            }
        });
        jPanel15.add(miercoles8);

        miercoles9.setBackground(new java.awt.Color(48, 84, 150));
        miercoles9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles9.setForeground(new java.awt.Color(0, 0, 0));
        miercoles9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles9.setComponentPopupMenu(opciones);
        miercoles9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes9FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles9FocusLost(evt);
            }
        });
        jPanel15.add(miercoles9);

        miercoles10.setBackground(new java.awt.Color(142, 169, 219));
        miercoles10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles10.setForeground(new java.awt.Color(0, 0, 0));
        miercoles10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles10.setComponentPopupMenu(opciones);
        miercoles10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes10FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles10FocusLost(evt);
            }
        });
        jPanel15.add(miercoles10);

        miercoles11.setBackground(new java.awt.Color(180, 198, 231));
        miercoles11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles11.setForeground(new java.awt.Color(0, 0, 0));
        miercoles11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles11.setComponentPopupMenu(opciones);
        miercoles11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo11FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles11FocusLost(evt);
            }
        });
        jPanel15.add(miercoles11);

        miercoles12.setBackground(new java.awt.Color(217, 225, 242));
        miercoles12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles12.setForeground(new java.awt.Color(0, 0, 0));
        miercoles12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles12.setComponentPopupMenu(opciones);
        miercoles12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes12FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles12FocusLost(evt);
            }
        });
        jPanel15.add(miercoles12);

        miercoles13.setBackground(new java.awt.Color(217, 225, 242));
        miercoles13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles13.setForeground(new java.awt.Color(0, 0, 0));
        miercoles13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles13.setComponentPopupMenu(opciones);
        miercoles13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes13FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles13FocusLost(evt);
            }
        });
        jPanel15.add(miercoles13);

        miercoles14.setBackground(new java.awt.Color(180, 198, 231));
        miercoles14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles14.setForeground(new java.awt.Color(0, 0, 0));
        miercoles14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles14.setComponentPopupMenu(opciones);
        miercoles14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo14FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles14FocusLost(evt);
            }
        });
        jPanel15.add(miercoles14);

        miercoles15.setBackground(new java.awt.Color(142, 169, 219));
        miercoles15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles15.setForeground(new java.awt.Color(0, 0, 0));
        miercoles15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles15.setComponentPopupMenu(opciones);
        miercoles15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves15FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles15FocusLost(evt);
            }
        });
        jPanel15.add(miercoles15);

        miercoles16.setBackground(new java.awt.Color(48, 84, 150));
        miercoles16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles16.setForeground(new java.awt.Color(0, 0, 0));
        miercoles16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles16.setComponentPopupMenu(opciones);
        miercoles16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves16FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles16FocusLost(evt);
            }
        });
        jPanel15.add(miercoles16);

        miercoles17.setBackground(new java.awt.Color(32, 55, 100));
        miercoles17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles17.setForeground(new java.awt.Color(0, 0, 0));
        miercoles17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles17.setComponentPopupMenu(opciones);
        miercoles17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado17FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles17FocusLost(evt);
            }
        });
        jPanel15.add(miercoles17);

        miercoles18.setBackground(new java.awt.Color(32, 55, 100));
        miercoles18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles18.setForeground(new java.awt.Color(0, 0, 0));
        miercoles18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles18.setComponentPopupMenu(opciones);
        miercoles18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado18FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles18FocusLost(evt);
            }
        });
        jPanel15.add(miercoles18);

        miercoles19.setBackground(new java.awt.Color(48, 84, 150));
        miercoles19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles19.setForeground(new java.awt.Color(0, 0, 0));
        miercoles19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles19.setComponentPopupMenu(opciones);
        miercoles19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles19FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles19FocusLost(evt);
            }
        });
        jPanel15.add(miercoles19);

        miercoles20.setBackground(new java.awt.Color(142, 169, 219));
        miercoles20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles20.setForeground(new java.awt.Color(0, 0, 0));
        miercoles20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles20.setComponentPopupMenu(opciones);
        miercoles20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes20FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles20FocusLost(evt);
            }
        });
        jPanel15.add(miercoles20);

        miercoles21.setBackground(new java.awt.Color(180, 198, 231));
        miercoles21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles21.setForeground(new java.awt.Color(0, 0, 0));
        miercoles21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles21.setComponentPopupMenu(opciones);
        miercoles21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo21FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles21FocusLost(evt);
            }
        });
        jPanel15.add(miercoles21);

        miercoles22.setBackground(new java.awt.Color(217, 225, 242));
        miercoles22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles22.setForeground(new java.awt.Color(0, 0, 0));
        miercoles22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles22.setComponentPopupMenu(opciones);
        miercoles22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado22FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles22FocusLost(evt);
            }
        });
        jPanel15.add(miercoles22);

        miercoles23.setBackground(new java.awt.Color(217, 225, 242));
        miercoles23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles23.setForeground(new java.awt.Color(0, 0, 0));
        miercoles23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles23.setComponentPopupMenu(opciones);
        miercoles23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado23FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles23FocusLost(evt);
            }
        });
        jPanel15.add(miercoles23);

        miercoles24.setBackground(new java.awt.Color(180, 198, 231));
        miercoles24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles24.setForeground(new java.awt.Color(0, 0, 0));
        miercoles24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        miercoles24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        miercoles24.setComponentPopupMenu(opciones);
        miercoles24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes24FocusGained(evt);
                focusMiercoles(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                miercoles24FocusLost(evt);
            }
        });
        jPanel15.add(miercoles24);

        jPanel11.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.GridLayout(24, 0, 0, 5));

        jueves1.setBackground(new java.awt.Color(142, 169, 219));
        jueves1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves1.setForeground(new java.awt.Color(0, 0, 0));
        jueves1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves1.setComponentPopupMenu(opciones);
        jueves1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes1FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves1FocusLost(evt);
            }
        });
        jPanel16.add(jueves1);

        jueves2.setBackground(new java.awt.Color(48, 84, 150));
        jueves2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves2.setForeground(new java.awt.Color(0, 0, 0));
        jueves2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves2.setComponentPopupMenu(opciones);
        jueves2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo2FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves2FocusLost(evt);
            }
        });
        jPanel16.add(jueves2);

        jueves3.setBackground(new java.awt.Color(32, 55, 100));
        jueves3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves3.setForeground(new java.awt.Color(0, 0, 0));
        jueves3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves3.setComponentPopupMenu(opciones);
        jueves3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes3FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves3FocusLost(evt);
            }
        });
        jPanel16.add(jueves3);

        jueves4.setBackground(new java.awt.Color(32, 55, 100));
        jueves4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves4.setForeground(new java.awt.Color(0, 0, 0));
        jueves4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves4.setComponentPopupMenu(opciones);
        jueves4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes4FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves4FocusLost(evt);
            }
        });
        jPanel16.add(jueves4);

        jueves5.setBackground(new java.awt.Color(48, 84, 150));
        jueves5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves5.setForeground(new java.awt.Color(0, 0, 0));
        jueves5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves5.setComponentPopupMenu(opciones);
        jueves5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo5FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves5FocusLost(evt);
            }
        });
        jPanel16.add(jueves5);

        jueves6.setBackground(new java.awt.Color(142, 169, 219));
        jueves6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves6.setForeground(new java.awt.Color(0, 0, 0));
        jueves6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves6.setComponentPopupMenu(opciones);
        jueves6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves6FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves6FocusLost(evt);
            }
        });
        jPanel16.add(jueves6);

        jueves7.setBackground(new java.awt.Color(180, 198, 231));
        jueves7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves7.setForeground(new java.awt.Color(0, 0, 0));
        jueves7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves7.setComponentPopupMenu(opciones);
        jueves7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles7FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves7FocusLost(evt);
            }
        });
        jPanel16.add(jueves7);

        jueves8.setBackground(new java.awt.Color(217, 225, 242));
        jueves8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves8.setForeground(new java.awt.Color(0, 0, 0));
        jueves8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves8.setComponentPopupMenu(opciones);
        jueves8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles8FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves8FocusLost(evt);
            }
        });
        jPanel16.add(jueves8);

        jueves9.setBackground(new java.awt.Color(217, 225, 242));
        jueves9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves9.setForeground(new java.awt.Color(0, 0, 0));
        jueves9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves9.setComponentPopupMenu(opciones);
        jueves9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes9FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves9FocusLost(evt);
            }
        });
        jPanel16.add(jueves9);

        jueves10.setBackground(new java.awt.Color(180, 198, 231));
        jueves10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves10.setForeground(new java.awt.Color(0, 0, 0));
        jueves10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves10.setComponentPopupMenu(opciones);
        jueves10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes10FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves10FocusLost(evt);
            }
        });
        jPanel16.add(jueves10);

        jueves11.setBackground(new java.awt.Color(142, 169, 219));
        jueves11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves11.setForeground(new java.awt.Color(0, 0, 0));
        jueves11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves11.setComponentPopupMenu(opciones);
        jueves11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo11FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves11FocusLost(evt);
            }
        });
        jPanel16.add(jueves11);

        jueves12.setBackground(new java.awt.Color(48, 84, 150));
        jueves12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves12.setForeground(new java.awt.Color(0, 0, 0));
        jueves12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves12.setComponentPopupMenu(opciones);
        jueves12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes12FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves12FocusLost(evt);
            }
        });
        jPanel16.add(jueves12);

        jueves13.setBackground(new java.awt.Color(32, 55, 100));
        jueves13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves13.setForeground(new java.awt.Color(0, 0, 0));
        jueves13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves13.setComponentPopupMenu(opciones);
        jueves13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes13FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves13FocusLost(evt);
            }
        });
        jPanel16.add(jueves13);

        jueves14.setBackground(new java.awt.Color(32, 55, 100));
        jueves14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves14.setForeground(new java.awt.Color(0, 0, 0));
        jueves14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves14.setComponentPopupMenu(opciones);
        jueves14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo14FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves14FocusLost(evt);
            }
        });
        jPanel16.add(jueves14);

        jueves15.setBackground(new java.awt.Color(48, 84, 150));
        jueves15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves15.setForeground(new java.awt.Color(0, 0, 0));
        jueves15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves15.setComponentPopupMenu(opciones);
        jueves15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves15FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves15FocusLost(evt);
            }
        });
        jPanel16.add(jueves15);

        jueves16.setBackground(new java.awt.Color(142, 169, 219));
        jueves16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves16.setForeground(new java.awt.Color(0, 0, 0));
        jueves16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves16.setComponentPopupMenu(opciones);
        jueves16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves16FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves16FocusLost(evt);
            }
        });
        jPanel16.add(jueves16);

        jueves17.setBackground(new java.awt.Color(180, 198, 231));
        jueves17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves17.setForeground(new java.awt.Color(0, 0, 0));
        jueves17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves17.setComponentPopupMenu(opciones);
        jueves17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado17FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves17FocusLost(evt);
            }
        });
        jPanel16.add(jueves17);

        jueves18.setBackground(new java.awt.Color(217, 225, 242));
        jueves18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves18.setForeground(new java.awt.Color(0, 0, 0));
        jueves18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves18.setComponentPopupMenu(opciones);
        jueves18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado18FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves18FocusLost(evt);
            }
        });
        jPanel16.add(jueves18);

        jueves19.setBackground(new java.awt.Color(217, 225, 242));
        jueves19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves19.setForeground(new java.awt.Color(0, 0, 0));
        jueves19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves19.setComponentPopupMenu(opciones);
        jueves19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles19FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves19FocusLost(evt);
            }
        });
        jPanel16.add(jueves19);

        jueves20.setBackground(new java.awt.Color(180, 198, 231));
        jueves20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves20.setForeground(new java.awt.Color(0, 0, 0));
        jueves20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves20.setComponentPopupMenu(opciones);
        jueves20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes20FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves20FocusLost(evt);
            }
        });
        jPanel16.add(jueves20);

        jueves21.setBackground(new java.awt.Color(142, 169, 219));
        jueves21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves21.setForeground(new java.awt.Color(0, 0, 0));
        jueves21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves21.setComponentPopupMenu(opciones);
        jueves21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo21FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves21FocusLost(evt);
            }
        });
        jPanel16.add(jueves21);

        jueves22.setBackground(new java.awt.Color(48, 84, 150));
        jueves22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves22.setForeground(new java.awt.Color(0, 0, 0));
        jueves22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves22.setComponentPopupMenu(opciones);
        jueves22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado22FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves22FocusLost(evt);
            }
        });
        jPanel16.add(jueves22);

        jueves23.setBackground(new java.awt.Color(32, 55, 100));
        jueves23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves23.setForeground(new java.awt.Color(0, 0, 0));
        jueves23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves23.setComponentPopupMenu(opciones);
        jueves23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado23FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves23FocusLost(evt);
            }
        });
        jPanel16.add(jueves23);

        jueves24.setBackground(new java.awt.Color(32, 55, 100));
        jueves24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves24.setForeground(new java.awt.Color(0, 0, 0));
        jueves24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jueves24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jueves24.setComponentPopupMenu(opciones);
        jueves24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes24FocusGained(evt);
                focusJueves(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jueves24FocusLost(evt);
            }
        });
        jPanel16.add(jueves24);

        jPanel11.add(jPanel16);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.GridLayout(24, 0, 0, 5));

        viernes1.setBackground(new java.awt.Color(217, 225, 242));
        viernes1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes1.setForeground(new java.awt.Color(0, 0, 0));
        viernes1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes1.setComponentPopupMenu(opciones);
        viernes1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes1FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes1FocusLost(evt);
            }
        });
        jPanel17.add(viernes1);

        viernes2.setBackground(new java.awt.Color(180, 198, 231));
        viernes2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes2.setForeground(new java.awt.Color(0, 0, 0));
        viernes2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes2.setComponentPopupMenu(opciones);
        viernes2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo2FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes2FocusLost(evt);
            }
        });
        jPanel17.add(viernes2);

        viernes3.setBackground(new java.awt.Color(142, 169, 219));
        viernes3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes3.setForeground(new java.awt.Color(0, 0, 0));
        viernes3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes3.setComponentPopupMenu(opciones);
        viernes3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes3FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes3FocusLost(evt);
            }
        });
        jPanel17.add(viernes3);

        viernes4.setBackground(new java.awt.Color(48, 84, 150));
        viernes4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes4.setForeground(new java.awt.Color(0, 0, 0));
        viernes4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes4.setComponentPopupMenu(opciones);
        viernes4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes4FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes4FocusLost(evt);
            }
        });
        jPanel17.add(viernes4);

        viernes5.setBackground(new java.awt.Color(32, 55, 100));
        viernes5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes5.setForeground(new java.awt.Color(0, 0, 0));
        viernes5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes5.setComponentPopupMenu(opciones);
        viernes5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo5FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes5FocusLost(evt);
            }
        });
        jPanel17.add(viernes5);

        viernes6.setBackground(new java.awt.Color(32, 55, 100));
        viernes6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes6.setForeground(new java.awt.Color(0, 0, 0));
        viernes6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes6.setComponentPopupMenu(opciones);
        viernes6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves6FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes6FocusLost(evt);
            }
        });
        jPanel17.add(viernes6);

        viernes7.setBackground(new java.awt.Color(48, 84, 150));
        viernes7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes7.setForeground(new java.awt.Color(0, 0, 0));
        viernes7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes7.setComponentPopupMenu(opciones);
        viernes7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles7FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes7FocusLost(evt);
            }
        });
        jPanel17.add(viernes7);

        viernes8.setBackground(new java.awt.Color(142, 169, 219));
        viernes8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes8.setForeground(new java.awt.Color(0, 0, 0));
        viernes8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes8.setComponentPopupMenu(opciones);
        viernes8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles8FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes8FocusLost(evt);
            }
        });
        jPanel17.add(viernes8);

        viernes9.setBackground(new java.awt.Color(180, 198, 231));
        viernes9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes9.setForeground(new java.awt.Color(0, 0, 0));
        viernes9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes9.setComponentPopupMenu(opciones);
        viernes9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes9FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes9FocusLost(evt);
            }
        });
        jPanel17.add(viernes9);

        viernes10.setBackground(new java.awt.Color(217, 225, 242));
        viernes10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes10.setForeground(new java.awt.Color(0, 0, 0));
        viernes10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes10.setComponentPopupMenu(opciones);
        viernes10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes10FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes10FocusLost(evt);
            }
        });
        jPanel17.add(viernes10);

        viernes11.setBackground(new java.awt.Color(217, 225, 242));
        viernes11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes11.setForeground(new java.awt.Color(0, 0, 0));
        viernes11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes11.setComponentPopupMenu(opciones);
        viernes11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo11FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes11FocusLost(evt);
            }
        });
        jPanel17.add(viernes11);

        viernes12.setBackground(new java.awt.Color(180, 198, 231));
        viernes12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes12.setForeground(new java.awt.Color(0, 0, 0));
        viernes12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes12.setComponentPopupMenu(opciones);
        viernes12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes12FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes12FocusLost(evt);
            }
        });
        jPanel17.add(viernes12);

        viernes13.setBackground(new java.awt.Color(142, 169, 219));
        viernes13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes13.setForeground(new java.awt.Color(0, 0, 0));
        viernes13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes13.setComponentPopupMenu(opciones);
        viernes13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes13FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes13FocusLost(evt);
            }
        });
        jPanel17.add(viernes13);

        viernes14.setBackground(new java.awt.Color(48, 84, 150));
        viernes14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes14.setForeground(new java.awt.Color(0, 0, 0));
        viernes14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes14.setComponentPopupMenu(opciones);
        viernes14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo14FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes14FocusLost(evt);
            }
        });
        jPanel17.add(viernes14);

        viernes15.setBackground(new java.awt.Color(32, 55, 100));
        viernes15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes15.setForeground(new java.awt.Color(0, 0, 0));
        viernes15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes15.setComponentPopupMenu(opciones);
        viernes15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves15FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes15FocusLost(evt);
            }
        });
        jPanel17.add(viernes15);

        viernes16.setBackground(new java.awt.Color(32, 55, 100));
        viernes16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes16.setForeground(new java.awt.Color(0, 0, 0));
        viernes16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes16.setComponentPopupMenu(opciones);
        viernes16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves16FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes16FocusLost(evt);
            }
        });
        jPanel17.add(viernes16);

        viernes17.setBackground(new java.awt.Color(48, 84, 150));
        viernes17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes17.setForeground(new java.awt.Color(0, 0, 0));
        viernes17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes17.setComponentPopupMenu(opciones);
        viernes17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado17FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes17FocusLost(evt);
            }
        });
        jPanel17.add(viernes17);

        viernes18.setBackground(new java.awt.Color(142, 169, 219));
        viernes18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes18.setForeground(new java.awt.Color(0, 0, 0));
        viernes18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes18.setComponentPopupMenu(opciones);
        viernes18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado18FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes18FocusLost(evt);
            }
        });
        jPanel17.add(viernes18);

        viernes19.setBackground(new java.awt.Color(180, 198, 231));
        viernes19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes19.setForeground(new java.awt.Color(0, 0, 0));
        viernes19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes19.setComponentPopupMenu(opciones);
        viernes19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles19FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes19FocusLost(evt);
            }
        });
        jPanel17.add(viernes19);

        viernes20.setBackground(new java.awt.Color(217, 225, 242));
        viernes20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes20.setForeground(new java.awt.Color(0, 0, 0));
        viernes20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes20.setComponentPopupMenu(opciones);
        viernes20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes20FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes20FocusLost(evt);
            }
        });
        jPanel17.add(viernes20);

        viernes21.setBackground(new java.awt.Color(217, 225, 242));
        viernes21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes21.setForeground(new java.awt.Color(0, 0, 0));
        viernes21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes21.setComponentPopupMenu(opciones);
        viernes21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo21FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes21FocusLost(evt);
            }
        });
        jPanel17.add(viernes21);

        viernes22.setBackground(new java.awt.Color(180, 198, 231));
        viernes22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes22.setForeground(new java.awt.Color(0, 0, 0));
        viernes22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes22.setComponentPopupMenu(opciones);
        viernes22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado22FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes22FocusLost(evt);
            }
        });
        jPanel17.add(viernes22);

        viernes23.setBackground(new java.awt.Color(142, 169, 219));
        viernes23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes23.setForeground(new java.awt.Color(0, 0, 0));
        viernes23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes23.setComponentPopupMenu(opciones);
        viernes23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado23FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes23FocusLost(evt);
            }
        });
        jPanel17.add(viernes23);

        viernes24.setBackground(new java.awt.Color(48, 84, 150));
        viernes24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes24.setForeground(new java.awt.Color(0, 0, 0));
        viernes24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        viernes24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        viernes24.setComponentPopupMenu(opciones);
        viernes24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes24FocusGained(evt);
                focusViernes(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                viernes24FocusLost(evt);
            }
        });
        jPanel17.add(viernes24);

        jPanel11.add(jPanel17);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.GridLayout(24, 0, 0, 5));

        sabado1.setBackground(new java.awt.Color(32, 55, 100));
        sabado1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado1.setForeground(new java.awt.Color(0, 0, 0));
        sabado1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado1.setComponentPopupMenu(opciones);
        sabado1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes1FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado1FocusLost(evt);
            }
        });
        jPanel18.add(sabado1);

        sabado2.setBackground(new java.awt.Color(32, 55, 100));
        sabado2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado2.setForeground(new java.awt.Color(0, 0, 0));
        sabado2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado2.setComponentPopupMenu(opciones);
        sabado2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo2FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado2FocusLost(evt);
            }
        });
        jPanel18.add(sabado2);

        sabado3.setBackground(new java.awt.Color(48, 84, 150));
        sabado3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado3.setForeground(new java.awt.Color(0, 0, 0));
        sabado3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado3.setComponentPopupMenu(opciones);
        sabado3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes3FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado3FocusLost(evt);
            }
        });
        jPanel18.add(sabado3);

        sabado4.setBackground(new java.awt.Color(142, 169, 219));
        sabado4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado4.setForeground(new java.awt.Color(0, 0, 0));
        sabado4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado4.setComponentPopupMenu(opciones);
        sabado4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes4FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado4FocusLost(evt);
            }
        });
        jPanel18.add(sabado4);

        sabado5.setBackground(new java.awt.Color(180, 198, 231));
        sabado5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado5.setForeground(new java.awt.Color(0, 0, 0));
        sabado5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado5.setComponentPopupMenu(opciones);
        sabado5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo5FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado5FocusLost(evt);
            }
        });
        jPanel18.add(sabado5);

        sabado6.setBackground(new java.awt.Color(217, 225, 242));
        sabado6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado6.setForeground(new java.awt.Color(0, 0, 0));
        sabado6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado6.setComponentPopupMenu(opciones);
        sabado6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves6FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado6FocusLost(evt);
            }
        });
        jPanel18.add(sabado6);

        sabado7.setBackground(new java.awt.Color(217, 225, 242));
        sabado7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado7.setForeground(new java.awt.Color(0, 0, 0));
        sabado7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado7.setComponentPopupMenu(opciones);
        sabado7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles7FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado7FocusLost(evt);
            }
        });
        jPanel18.add(sabado7);

        sabado8.setBackground(new java.awt.Color(180, 198, 231));
        sabado8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado8.setForeground(new java.awt.Color(0, 0, 0));
        sabado8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado8.setComponentPopupMenu(opciones);
        sabado8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles8FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado8FocusLost(evt);
            }
        });
        jPanel18.add(sabado8);

        sabado9.setBackground(new java.awt.Color(142, 169, 219));
        sabado9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado9.setForeground(new java.awt.Color(0, 0, 0));
        sabado9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado9.setComponentPopupMenu(opciones);
        sabado9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes9FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado9FocusLost(evt);
            }
        });
        jPanel18.add(sabado9);

        sabado10.setBackground(new java.awt.Color(48, 84, 150));
        sabado10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado10.setForeground(new java.awt.Color(0, 0, 0));
        sabado10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado10.setComponentPopupMenu(opciones);
        sabado10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes10FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado10FocusLost(evt);
            }
        });
        jPanel18.add(sabado10);

        sabado11.setBackground(new java.awt.Color(32, 55, 100));
        sabado11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado11.setForeground(new java.awt.Color(0, 0, 0));
        sabado11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado11.setComponentPopupMenu(opciones);
        sabado11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo11FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado11FocusLost(evt);
            }
        });
        jPanel18.add(sabado11);

        sabado12.setBackground(new java.awt.Color(32, 55, 100));
        sabado12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado12.setForeground(new java.awt.Color(0, 0, 0));
        sabado12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado12.setComponentPopupMenu(opciones);
        sabado12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes12FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado12FocusLost(evt);
            }
        });
        jPanel18.add(sabado12);

        sabado13.setBackground(new java.awt.Color(48, 84, 150));
        sabado13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado13.setForeground(new java.awt.Color(0, 0, 0));
        sabado13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado13.setComponentPopupMenu(opciones);
        sabado13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes13FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado13FocusLost(evt);
            }
        });
        jPanel18.add(sabado13);

        sabado14.setBackground(new java.awt.Color(142, 169, 219));
        sabado14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado14.setForeground(new java.awt.Color(0, 0, 0));
        sabado14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado14.setComponentPopupMenu(opciones);
        sabado14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo14FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado14FocusLost(evt);
            }
        });
        jPanel18.add(sabado14);

        sabado15.setBackground(new java.awt.Color(180, 198, 231));
        sabado15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado15.setForeground(new java.awt.Color(0, 0, 0));
        sabado15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado15.setComponentPopupMenu(opciones);
        sabado15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves15FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado15FocusLost(evt);
            }
        });
        jPanel18.add(sabado15);

        sabado16.setBackground(new java.awt.Color(217, 225, 242));
        sabado16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado16.setForeground(new java.awt.Color(0, 0, 0));
        sabado16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado16.setComponentPopupMenu(opciones);
        sabado16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves16FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado16FocusLost(evt);
            }
        });
        jPanel18.add(sabado16);

        sabado17.setBackground(new java.awt.Color(217, 225, 242));
        sabado17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado17.setForeground(new java.awt.Color(0, 0, 0));
        sabado17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado17.setComponentPopupMenu(opciones);
        sabado17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado17FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado17FocusLost(evt);
            }
        });
        jPanel18.add(sabado17);

        sabado18.setBackground(new java.awt.Color(180, 198, 231));
        sabado18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado18.setForeground(new java.awt.Color(0, 0, 0));
        sabado18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado18.setComponentPopupMenu(opciones);
        sabado18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado18FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado18FocusLost(evt);
            }
        });
        jPanel18.add(sabado18);

        sabado19.setBackground(new java.awt.Color(142, 169, 219));
        sabado19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado19.setForeground(new java.awt.Color(0, 0, 0));
        sabado19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado19.setComponentPopupMenu(opciones);
        sabado19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles19FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado19FocusLost(evt);
            }
        });
        jPanel18.add(sabado19);

        sabado20.setBackground(new java.awt.Color(48, 84, 150));
        sabado20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado20.setForeground(new java.awt.Color(0, 0, 0));
        sabado20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado20.setComponentPopupMenu(opciones);
        sabado20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes20FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado20FocusLost(evt);
            }
        });
        jPanel18.add(sabado20);

        sabado21.setBackground(new java.awt.Color(32, 55, 100));
        sabado21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado21.setForeground(new java.awt.Color(0, 0, 0));
        sabado21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado21.setComponentPopupMenu(opciones);
        sabado21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo21FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado21FocusLost(evt);
            }
        });
        jPanel18.add(sabado21);

        sabado22.setBackground(new java.awt.Color(32, 55, 100));
        sabado22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado22.setForeground(new java.awt.Color(0, 0, 0));
        sabado22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado22.setComponentPopupMenu(opciones);
        sabado22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado22FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado22FocusLost(evt);
            }
        });
        jPanel18.add(sabado22);

        sabado23.setBackground(new java.awt.Color(48, 84, 150));
        sabado23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado23.setForeground(new java.awt.Color(0, 0, 0));
        sabado23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado23.setComponentPopupMenu(opciones);
        sabado23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado23FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado23FocusLost(evt);
            }
        });
        jPanel18.add(sabado23);

        sabado24.setBackground(new java.awt.Color(142, 169, 219));
        sabado24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado24.setForeground(new java.awt.Color(0, 0, 0));
        sabado24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sabado24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        sabado24.setComponentPopupMenu(opciones);
        sabado24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes24FocusGained(evt);
                focusSabado(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sabado24FocusLost(evt);
            }
        });
        jPanel18.add(sabado24);

        jPanel11.add(jPanel18);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new java.awt.GridLayout(24, 0, 0, 5));

        domingo1.setBackground(new java.awt.Color(180, 198, 231));
        domingo1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo1.setForeground(new java.awt.Color(0, 0, 0));
        domingo1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo1.setComponentPopupMenu(opciones);
        domingo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes1FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo1FocusLost(evt);
            }
        });
        jPanel19.add(domingo1);

        domingo2.setBackground(new java.awt.Color(217, 225, 242));
        domingo2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo2.setForeground(new java.awt.Color(0, 0, 0));
        domingo2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo2.setComponentPopupMenu(opciones);
        domingo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo2FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo2FocusLost(evt);
            }
        });
        jPanel19.add(domingo2);

        domingo3.setBackground(new java.awt.Color(217, 225, 242));
        domingo3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo3.setForeground(new java.awt.Color(0, 0, 0));
        domingo3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo3.setComponentPopupMenu(opciones);
        domingo3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes3FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo3FocusLost(evt);
            }
        });
        jPanel19.add(domingo3);

        domingo4.setBackground(new java.awt.Color(180, 198, 231));
        domingo4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo4.setForeground(new java.awt.Color(0, 0, 0));
        domingo4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo4.setComponentPopupMenu(opciones);
        domingo4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes4FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo4FocusLost(evt);
            }
        });
        jPanel19.add(domingo4);

        domingo5.setBackground(new java.awt.Color(142, 169, 219));
        domingo5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo5.setForeground(new java.awt.Color(0, 0, 0));
        domingo5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo5.setComponentPopupMenu(opciones);
        domingo5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo5FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo5FocusLost(evt);
            }
        });
        jPanel19.add(domingo5);

        domingo6.setBackground(new java.awt.Color(48, 84, 150));
        domingo6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo6.setForeground(new java.awt.Color(0, 0, 0));
        domingo6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo6.setComponentPopupMenu(opciones);
        domingo6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves6FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo6FocusLost(evt);
            }
        });
        jPanel19.add(domingo6);

        domingo7.setBackground(new java.awt.Color(32, 55, 100));
        domingo7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo7.setForeground(new java.awt.Color(0, 0, 0));
        domingo7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo7.setComponentPopupMenu(opciones);
        domingo7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles7FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo7FocusLost(evt);
            }
        });
        jPanel19.add(domingo7);

        domingo8.setBackground(new java.awt.Color(32, 55, 100));
        domingo8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo8.setForeground(new java.awt.Color(0, 0, 0));
        domingo8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo8.setComponentPopupMenu(opciones);
        domingo8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles8FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo8FocusLost(evt);
            }
        });
        jPanel19.add(domingo8);

        domingo9.setBackground(new java.awt.Color(48, 84, 150));
        domingo9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo9.setForeground(new java.awt.Color(0, 0, 0));
        domingo9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo9.setComponentPopupMenu(opciones);
        domingo9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes9FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo9FocusLost(evt);
            }
        });
        jPanel19.add(domingo9);

        domingo10.setBackground(new java.awt.Color(142, 169, 219));
        domingo10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo10.setForeground(new java.awt.Color(0, 0, 0));
        domingo10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo10.setComponentPopupMenu(opciones);
        domingo10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes10FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo10FocusLost(evt);
            }
        });
        jPanel19.add(domingo10);

        domingo11.setBackground(new java.awt.Color(180, 198, 231));
        domingo11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo11.setForeground(new java.awt.Color(0, 0, 0));
        domingo11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo11.setComponentPopupMenu(opciones);
        domingo11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo11FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo11FocusLost(evt);
            }
        });
        jPanel19.add(domingo11);

        domingo12.setBackground(new java.awt.Color(217, 225, 242));
        domingo12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo12.setForeground(new java.awt.Color(0, 0, 0));
        domingo12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo12.setComponentPopupMenu(opciones);
        domingo12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes12FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo12FocusLost(evt);
            }
        });
        jPanel19.add(domingo12);

        domingo13.setBackground(new java.awt.Color(217, 225, 242));
        domingo13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo13.setForeground(new java.awt.Color(0, 0, 0));
        domingo13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo13.setComponentPopupMenu(opciones);
        domingo13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                viernes13FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo13FocusLost(evt);
            }
        });
        jPanel19.add(domingo13);

        domingo14.setBackground(new java.awt.Color(180, 198, 231));
        domingo14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo14.setForeground(new java.awt.Color(0, 0, 0));
        domingo14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo14.setComponentPopupMenu(opciones);
        domingo14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo14FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo14FocusLost(evt);
            }
        });
        jPanel19.add(domingo14);

        domingo15.setBackground(new java.awt.Color(142, 169, 219));
        domingo15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo15.setForeground(new java.awt.Color(0, 0, 0));
        domingo15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo15.setComponentPopupMenu(opciones);
        domingo15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves15FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo15FocusLost(evt);
            }
        });
        jPanel19.add(domingo15);

        domingo16.setBackground(new java.awt.Color(48, 84, 150));
        domingo16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo16.setForeground(new java.awt.Color(0, 0, 0));
        domingo16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo16.setComponentPopupMenu(opciones);
        domingo16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jueves16FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo16FocusLost(evt);
            }
        });
        jPanel19.add(domingo16);

        domingo17.setBackground(new java.awt.Color(32, 55, 100));
        domingo17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo17.setForeground(new java.awt.Color(0, 0, 0));
        domingo17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo17.setComponentPopupMenu(opciones);
        domingo17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado17FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo17FocusLost(evt);
            }
        });
        jPanel19.add(domingo17);

        domingo18.setBackground(new java.awt.Color(32, 55, 100));
        domingo18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo18.setForeground(new java.awt.Color(0, 0, 0));
        domingo18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo18.setComponentPopupMenu(opciones);
        domingo18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado18FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo18FocusLost(evt);
            }
        });
        jPanel19.add(domingo18);

        domingo19.setBackground(new java.awt.Color(48, 84, 150));
        domingo19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo19.setForeground(new java.awt.Color(0, 0, 0));
        domingo19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo19.setComponentPopupMenu(opciones);
        domingo19.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miercoles19FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo19FocusLost(evt);
            }
        });
        jPanel19.add(domingo19);

        domingo20.setBackground(new java.awt.Color(142, 169, 219));
        domingo20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo20.setForeground(new java.awt.Color(0, 0, 0));
        domingo20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo20.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo20.setComponentPopupMenu(opciones);
        domingo20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Lunes20FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo20FocusLost(evt);
            }
        });
        jPanel19.add(domingo20);

        domingo21.setBackground(new java.awt.Color(180, 198, 231));
        domingo21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo21.setForeground(new java.awt.Color(0, 0, 0));
        domingo21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo21.setComponentPopupMenu(opciones);
        domingo21.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                domingo21FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo21FocusLost(evt);
            }
        });
        jPanel19.add(domingo21);

        domingo22.setBackground(new java.awt.Color(217, 225, 242));
        domingo22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo22.setForeground(new java.awt.Color(0, 0, 0));
        domingo22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo22.setComponentPopupMenu(opciones);
        domingo22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado22FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo22FocusLost(evt);
            }
        });
        jPanel19.add(domingo22);

        domingo23.setBackground(new java.awt.Color(217, 225, 242));
        domingo23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo23.setForeground(new java.awt.Color(0, 0, 0));
        domingo23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo23.setComponentPopupMenu(opciones);
        domingo23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sabado23FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo23FocusLost(evt);
            }
        });
        jPanel19.add(domingo23);

        domingo24.setBackground(new java.awt.Color(180, 198, 231));
        domingo24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo24.setForeground(new java.awt.Color(0, 0, 0));
        domingo24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        domingo24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        domingo24.setComponentPopupMenu(opciones);
        domingo24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                martes24FocusGained(evt);
                focusDomingo(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                domingo24FocusLost(evt);
            }
        });
        jPanel19.add(domingo24);

        jPanel11.add(jPanel19);

        jPanel8.add(jPanel11, java.awt.BorderLayout.CENTER);

        jLabel35.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText(" ");
        jPanel8.add(jLabel35, java.awt.BorderLayout.PAGE_END);

        jPanel6.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void cmbSemanasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSemanasActionPerformed
        if(cmbSemanas.getSelectedItem() != null){
            if(!cmbSemanas.getSelectedItem().equals("") && !lblYear.getText().equals("Año")){
                setFecha();
                if(this.isVisible()){
                    limpiar();
                    setBlanco();
                    verDatos();
                }
            }
        }
    }//GEN-LAST:event_cmbSemanasActionPerformed

    private void Lunes1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes1FocusLost
        focusLost(Lunes1, lun[1]);
    }//GEN-LAST:event_Lunes1FocusLost

    private void Lunes2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes2FocusLost
        focusLost(Lunes2, lun[2]);
    }//GEN-LAST:event_Lunes2FocusLost

    private void Lunes3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes3FocusLost
        focusLost(Lunes3, lun[3]);
    }//GEN-LAST:event_Lunes3FocusLost

    private void Lunes4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes4FocusLost
        focusLost(Lunes4, lun[4]);
    }//GEN-LAST:event_Lunes4FocusLost

    private void Lunes5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes5FocusLost
        focusLost(Lunes5, lun[5]);
    }//GEN-LAST:event_Lunes5FocusLost

    private void Lunes6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes6FocusLost
        focusLost(Lunes6, lun[6]);
    }//GEN-LAST:event_Lunes6FocusLost

    private void Lunes7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes7FocusLost
        focusLost(Lunes7, lun[7]);
    }//GEN-LAST:event_Lunes7FocusLost

    private void Lunes8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes8FocusLost
        focusLost(Lunes8, lun[8]);
    }//GEN-LAST:event_Lunes8FocusLost

    private void Lunes9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes9FocusLost
        focusLost(Lunes9, lun[9]);
    }//GEN-LAST:event_Lunes9FocusLost

    private void Lunes10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes10FocusLost
        focusLost(Lunes10, lun[10]);
    }//GEN-LAST:event_Lunes10FocusLost

    private void Lunes11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes11FocusLost
        focusLost(Lunes11, lun[11]);
    }//GEN-LAST:event_Lunes11FocusLost

    private void Lunes12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes12FocusLost
        focusLost(Lunes12, lun[12]);
    }//GEN-LAST:event_Lunes12FocusLost

    private void Lunes13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes13FocusLost
        focusLost(Lunes13, lun[13]);
    }//GEN-LAST:event_Lunes13FocusLost

    private void Lunes14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes14FocusLost
        focusLost(Lunes14, lun[14]);
    }//GEN-LAST:event_Lunes14FocusLost

    private void Lunes15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes15FocusLost
        focusLost(Lunes15, lun[15]);
    }//GEN-LAST:event_Lunes15FocusLost

    private void Lunes16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes16FocusLost
        focusLost(Lunes16, lun[16]);
    }//GEN-LAST:event_Lunes16FocusLost

    private void Lunes17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes17FocusLost
        focusLost(Lunes17, lun[17]);
    }//GEN-LAST:event_Lunes17FocusLost

    private void Lunes18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes18FocusLost
        focusLost(Lunes18, lun[18]);
    }//GEN-LAST:event_Lunes18FocusLost

    private void Lunes19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes19FocusLost
        focusLost(Lunes19, lun[19]);
    }//GEN-LAST:event_Lunes19FocusLost

    private void Lunes20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes20FocusLost
        focusLost(Lunes20, lun[20]);
    }//GEN-LAST:event_Lunes20FocusLost

    private void Lunes21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes21FocusLost
        focusLost(Lunes21, lun[21]);
    }//GEN-LAST:event_Lunes21FocusLost

    private void Lunes22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes22FocusLost
        focusLost(Lunes22, lun[22]);
    }//GEN-LAST:event_Lunes22FocusLost

    private void Lunes23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes23FocusLost
        focusLost(Lunes23, lun[23]);
    }//GEN-LAST:event_Lunes23FocusLost

    private void Lunes24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes24FocusLost
        focusLost(Lunes24, lun[24]);
    }//GEN-LAST:event_Lunes24FocusLost

    private void martes1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes1FocusLost
        focusLost(martes1, mar[1]);
    }//GEN-LAST:event_martes1FocusLost

    private void martes2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes2FocusLost
        focusLost(martes2, mar[2]);
    }//GEN-LAST:event_martes2FocusLost

    private void martes3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes3FocusLost
        focusLost(martes3, mar[3]);
    }//GEN-LAST:event_martes3FocusLost

    private void martes4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes4FocusLost
        focusLost(martes4, mar[4]);
    }//GEN-LAST:event_martes4FocusLost

    private void martes5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes5FocusLost
        focusLost(martes5, mar[5]);
    }//GEN-LAST:event_martes5FocusLost

    private void martes6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes6FocusLost
        focusLost(martes6, mar[6]);
    }//GEN-LAST:event_martes6FocusLost

    private void martes7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes7FocusLost
        focusLost(martes7, mar[7]);
    }//GEN-LAST:event_martes7FocusLost

    private void martes8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes8FocusLost
        focusLost(martes8, mar[8]);
    }//GEN-LAST:event_martes8FocusLost

    private void martes9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes9FocusLost
        focusLost(martes9, mar[9]);
    }//GEN-LAST:event_martes9FocusLost

    private void martes10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes10FocusLost
        focusLost(martes10, mar[10]);
    }//GEN-LAST:event_martes10FocusLost

    private void martes11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes11FocusLost
        focusLost(martes11, mar[11]);
    }//GEN-LAST:event_martes11FocusLost

    private void martes12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes12FocusLost
        focusLost(martes12, mar[12]);
    }//GEN-LAST:event_martes12FocusLost

    private void martes13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes13FocusLost
        focusLost(martes13, mar[13]);
    }//GEN-LAST:event_martes13FocusLost

    private void martes14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes14FocusLost
        focusLost(martes14, mar[14]);
    }//GEN-LAST:event_martes14FocusLost

    private void martes15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes15FocusLost
        focusLost(martes15, mar[15]);
    }//GEN-LAST:event_martes15FocusLost

    private void martes16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes16FocusLost
        focusLost(martes16, mar[16]);
    }//GEN-LAST:event_martes16FocusLost

    private void martes17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes17FocusLost
        focusLost(martes17, mar[17]);
    }//GEN-LAST:event_martes17FocusLost

    private void martes18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes18FocusLost
        focusLost(martes18, mar[18]);
    }//GEN-LAST:event_martes18FocusLost

    private void martes19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes19FocusLost
        focusLost(martes19, mar[19]);
    }//GEN-LAST:event_martes19FocusLost

    private void martes20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes20FocusLost
        focusLost(martes20, mar[20]);
    }//GEN-LAST:event_martes20FocusLost

    private void martes21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes21FocusLost
        focusLost(martes21, mar[21]);
    }//GEN-LAST:event_martes21FocusLost

    private void martes22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes22FocusLost
        focusLost(martes22, mar[22]);
    }//GEN-LAST:event_martes22FocusLost

    private void martes23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes23FocusLost
        focusLost(martes23, mar[23]);
    }//GEN-LAST:event_martes23FocusLost

    private void martes24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes24FocusLost
        focusLost(martes24, mar[24]);
    }//GEN-LAST:event_martes24FocusLost

    private void miercoles1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles1FocusLost
        focusLost(miercoles1, mie[1]);
    }//GEN-LAST:event_miercoles1FocusLost

    private void miercoles2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles2FocusLost
        focusLost(miercoles2, mie[2]);
    }//GEN-LAST:event_miercoles2FocusLost

    private void miercoles3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles3FocusLost
        focusLost(miercoles3, mie[3]);
    }//GEN-LAST:event_miercoles3FocusLost

    private void miercoles4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles4FocusLost
        focusLost(miercoles4, mie[4]);
    }//GEN-LAST:event_miercoles4FocusLost

    private void miercoles5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles5FocusLost
        focusLost(miercoles5, mie[5]);
    }//GEN-LAST:event_miercoles5FocusLost

    private void miercoles6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles6FocusLost
        focusLost(miercoles6, mie[6]);
    }//GEN-LAST:event_miercoles6FocusLost

    private void miercoles7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles7FocusLost
        focusLost(miercoles7, mie[7]);
    }//GEN-LAST:event_miercoles7FocusLost

    private void miercoles8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles8FocusLost
        focusLost(miercoles8, mie[8]);
    }//GEN-LAST:event_miercoles8FocusLost

    private void miercoles9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles9FocusLost
        focusLost(miercoles9, mie[9]);
    }//GEN-LAST:event_miercoles9FocusLost

    private void miercoles10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles10FocusLost
        focusLost(miercoles10, mie[10]);
    }//GEN-LAST:event_miercoles10FocusLost

    private void miercoles11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles11FocusLost
        focusLost(miercoles11, mie[11]);
    }//GEN-LAST:event_miercoles11FocusLost

    private void miercoles12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles12FocusLost
        focusLost(miercoles12, mie[12]);
    }//GEN-LAST:event_miercoles12FocusLost

    private void miercoles13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles13FocusLost
        focusLost(miercoles13, mie[13]);
    }//GEN-LAST:event_miercoles13FocusLost

    private void miercoles14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles14FocusLost
        focusLost(miercoles14, mie[14]);
    }//GEN-LAST:event_miercoles14FocusLost

    private void miercoles15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles15FocusLost
        focusLost(miercoles15, mie[15]);
    }//GEN-LAST:event_miercoles15FocusLost

    private void miercoles16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles16FocusLost
        focusLost(miercoles16, mie[16]);
    }//GEN-LAST:event_miercoles16FocusLost

    private void miercoles17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles17FocusLost
        focusLost(miercoles17, mie[17]);
    }//GEN-LAST:event_miercoles17FocusLost

    private void miercoles18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles18FocusLost
        focusLost(miercoles18, mie[18]);
    }//GEN-LAST:event_miercoles18FocusLost

    private void miercoles19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles19FocusLost
        focusLost(miercoles19, mie[19]);
    }//GEN-LAST:event_miercoles19FocusLost

    private void miercoles20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles20FocusLost
        focusLost(miercoles20, mie[20]);
    }//GEN-LAST:event_miercoles20FocusLost

    private void miercoles21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles21FocusLost
        focusLost(miercoles21, mie[21]);
    }//GEN-LAST:event_miercoles21FocusLost

    private void miercoles22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles22FocusLost
        focusLost(miercoles22, mie[22]);
    }//GEN-LAST:event_miercoles22FocusLost

    private void miercoles23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles23FocusLost
        focusLost(miercoles23, mie[23]);
    }//GEN-LAST:event_miercoles23FocusLost

    private void miercoles24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles24FocusLost
        focusLost(miercoles24, mie[24]);
    }//GEN-LAST:event_miercoles24FocusLost

    private void jueves1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves1FocusLost
        focusLost(jueves1, jue[1]);
    }//GEN-LAST:event_jueves1FocusLost

    private void jueves2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves2FocusLost
        focusLost(jueves2, jue[2]);
    }//GEN-LAST:event_jueves2FocusLost

    private void jueves3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves3FocusLost
        focusLost(jueves3, jue[3]);
    }//GEN-LAST:event_jueves3FocusLost

    private void jueves4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves4FocusLost
        focusLost(jueves4, jue[4]);
    }//GEN-LAST:event_jueves4FocusLost

    private void jueves5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves5FocusLost
        focusLost(jueves5, jue[5]);
    }//GEN-LAST:event_jueves5FocusLost

    private void jueves6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves6FocusLost
        focusLost(jueves6, jue[6]);
    }//GEN-LAST:event_jueves6FocusLost

    private void jueves7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves7FocusLost
        focusLost(jueves7, jue[7]);
    }//GEN-LAST:event_jueves7FocusLost

    private void jueves8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves8FocusLost
        focusLost(jueves8, jue[8]);
    }//GEN-LAST:event_jueves8FocusLost

    private void jueves9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves9FocusLost
        focusLost(jueves9, jue[9]);
    }//GEN-LAST:event_jueves9FocusLost

    private void jueves10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves10FocusLost
        focusLost(jueves10, jue[10]);
    }//GEN-LAST:event_jueves10FocusLost

    private void jueves11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves11FocusLost
        focusLost(jueves11, jue[11]);
    }//GEN-LAST:event_jueves11FocusLost

    private void jueves12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves12FocusLost
        focusLost(jueves12, jue[12]);
    }//GEN-LAST:event_jueves12FocusLost

    private void jueves13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves13FocusLost
        focusLost(jueves13, jue[13]);
    }//GEN-LAST:event_jueves13FocusLost

    private void jueves14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves14FocusLost
        focusLost(jueves14, jue[14]);
    }//GEN-LAST:event_jueves14FocusLost

    private void jueves15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves15FocusLost
        focusLost(jueves15, jue[15]);
    }//GEN-LAST:event_jueves15FocusLost

    private void jueves16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves16FocusLost
        focusLost(jueves16, jue[16]);
    }//GEN-LAST:event_jueves16FocusLost

    private void jueves17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves17FocusLost
        focusLost(jueves17, jue[17]);
    }//GEN-LAST:event_jueves17FocusLost

    private void jueves18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves18FocusLost
        focusLost(jueves18, jue[18]);
    }//GEN-LAST:event_jueves18FocusLost

    private void jueves19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves19FocusLost
        focusLost(jueves19, jue[19]);
    }//GEN-LAST:event_jueves19FocusLost

    private void jueves20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves20FocusLost
        focusLost(jueves2, jue[20]);
    }//GEN-LAST:event_jueves20FocusLost

    private void jueves21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves21FocusLost
        focusLost(jueves2, jue[21]);
    }//GEN-LAST:event_jueves21FocusLost

    private void jueves22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves22FocusLost
        focusLost(jueves2, jue[22]);
    }//GEN-LAST:event_jueves22FocusLost

    private void jueves23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves23FocusLost
        focusLost(jueves2, jue[23]);
    }//GEN-LAST:event_jueves23FocusLost

    private void jueves24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves24FocusLost
        focusLost(jueves2, jue[24]);
    }//GEN-LAST:event_jueves24FocusLost

    private void viernes1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes1FocusLost
        focusLost(viernes1, vie[1]);
    }//GEN-LAST:event_viernes1FocusLost

    private void viernes2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes2FocusLost
        focusLost(viernes2, vie[2]);
    }//GEN-LAST:event_viernes2FocusLost

    private void viernes3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes3FocusLost
        focusLost(viernes3, vie[3]);
    }//GEN-LAST:event_viernes3FocusLost

    private void viernes4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes4FocusLost
        focusLost(viernes4, vie[4]);
    }//GEN-LAST:event_viernes4FocusLost

    private void viernes5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes5FocusLost
        focusLost(viernes5, vie[5]);
    }//GEN-LAST:event_viernes5FocusLost

    private void viernes6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes6FocusLost
        focusLost(viernes6, vie[6]);
    }//GEN-LAST:event_viernes6FocusLost

    private void viernes7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes7FocusLost
        focusLost(viernes7, vie[7]);
    }//GEN-LAST:event_viernes7FocusLost

    private void viernes8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes8FocusLost
        focusLost(viernes8, vie[8]);
    }//GEN-LAST:event_viernes8FocusLost

    private void viernes9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes9FocusLost
        focusLost(viernes9, vie[9]);
    }//GEN-LAST:event_viernes9FocusLost

    private void viernes10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes10FocusLost
        focusLost(viernes10, vie[10]);
    }//GEN-LAST:event_viernes10FocusLost

    private void viernes11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes11FocusLost
        focusLost(viernes11, vie[11]);
    }//GEN-LAST:event_viernes11FocusLost

    private void viernes12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes12FocusLost
        focusLost(viernes12, vie[12]);
    }//GEN-LAST:event_viernes12FocusLost

    private void viernes13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes13FocusLost
        focusLost(viernes13, vie[13]);
    }//GEN-LAST:event_viernes13FocusLost

    private void viernes14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes14FocusLost
        focusLost(viernes14, vie[14]);
    }//GEN-LAST:event_viernes14FocusLost

    private void viernes15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes15FocusLost
        focusLost(viernes15, vie[15]);
    }//GEN-LAST:event_viernes15FocusLost

    private void viernes16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes16FocusLost
        focusLost(viernes16, vie[16]);
    }//GEN-LAST:event_viernes16FocusLost

    private void viernes17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes17FocusLost
        focusLost(viernes17, vie[17]);
    }//GEN-LAST:event_viernes17FocusLost

    private void viernes18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes18FocusLost
        focusLost(viernes18, vie[18]);
    }//GEN-LAST:event_viernes18FocusLost

    private void viernes19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes19FocusLost
        focusLost(viernes19, vie[19]);
    }//GEN-LAST:event_viernes19FocusLost

    private void viernes20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes20FocusLost
        focusLost(viernes20, vie[20]);
    }//GEN-LAST:event_viernes20FocusLost

    private void viernes21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes21FocusLost
        focusLost(viernes21, vie[21]);
    }//GEN-LAST:event_viernes21FocusLost

    private void viernes22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes22FocusLost
        focusLost(viernes22, vie[22]);
    }//GEN-LAST:event_viernes22FocusLost

    private void viernes23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes23FocusLost
        focusLost(viernes23, vie[23]);
    }//GEN-LAST:event_viernes23FocusLost

    private void viernes24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes24FocusLost
        focusLost(viernes24, vie[24]);
    }//GEN-LAST:event_viernes24FocusLost

    private void sabado1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado1FocusLost
        focusLost(sabado1, sab[1]);
    }//GEN-LAST:event_sabado1FocusLost

    private void sabado2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado2FocusLost
        focusLost(sabado2, sab[2]);
    }//GEN-LAST:event_sabado2FocusLost

    private void sabado3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado3FocusLost
        focusLost(sabado3, sab[3]);
    }//GEN-LAST:event_sabado3FocusLost

    private void sabado4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado4FocusLost
        focusLost(sabado4, sab[4]);
    }//GEN-LAST:event_sabado4FocusLost

    private void sabado5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado5FocusLost
        focusLost(sabado5, sab[5]);
    }//GEN-LAST:event_sabado5FocusLost

    private void sabado6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado6FocusLost
        focusLost(sabado6, sab[6]);
    }//GEN-LAST:event_sabado6FocusLost

    private void sabado7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado7FocusLost
        focusLost(sabado7, sab[7]);
    }//GEN-LAST:event_sabado7FocusLost

    private void sabado8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado8FocusLost
        focusLost(sabado8, sab[8]);
    }//GEN-LAST:event_sabado8FocusLost

    private void sabado9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado9FocusLost
        focusLost(sabado9, sab[9]);
    }//GEN-LAST:event_sabado9FocusLost

    private void sabado10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado10FocusLost
        focusLost(sabado10, sab[10]);
    }//GEN-LAST:event_sabado10FocusLost

    private void sabado11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado11FocusLost
        focusLost(sabado11, sab[11]);
    }//GEN-LAST:event_sabado11FocusLost

    private void sabado12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado12FocusLost
        focusLost(sabado12, sab[12]);
    }//GEN-LAST:event_sabado12FocusLost

    private void sabado13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado13FocusLost
        focusLost(sabado13, sab[13]);
    }//GEN-LAST:event_sabado13FocusLost

    private void sabado14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado14FocusLost
        focusLost(sabado14, sab[14]);
    }//GEN-LAST:event_sabado14FocusLost

    private void sabado15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado15FocusLost
        focusLost(sabado15, sab[15]);
    }//GEN-LAST:event_sabado15FocusLost

    private void sabado16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado16FocusLost
        focusLost(sabado16, sab[16]);
    }//GEN-LAST:event_sabado16FocusLost

    private void sabado17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado17FocusLost
        focusLost(sabado17, sab[17]);
    }//GEN-LAST:event_sabado17FocusLost

    private void sabado18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado18FocusLost
        focusLost(sabado18, sab[18]);
    }//GEN-LAST:event_sabado18FocusLost

    private void sabado19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado19FocusLost
        focusLost(sabado19, sab[19]);
    }//GEN-LAST:event_sabado19FocusLost

    private void sabado20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado20FocusLost
        focusLost(sabado20, sab[20]);
    }//GEN-LAST:event_sabado20FocusLost

    private void sabado21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado21FocusLost
        focusLost(sabado21, sab[21]);
    }//GEN-LAST:event_sabado21FocusLost

    private void sabado22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado22FocusLost
        focusLost(sabado22, sab[22]);
    }//GEN-LAST:event_sabado22FocusLost

    private void sabado23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado23FocusLost
        focusLost(sabado23, sab[23]);
    }//GEN-LAST:event_sabado23FocusLost

    private void sabado24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado24FocusLost
        focusLost(sabado24, sab[24]);
    }//GEN-LAST:event_sabado24FocusLost

    private void domingo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo1FocusLost
        focusLost(domingo1, dom[1]);
    }//GEN-LAST:event_domingo1FocusLost

    private void domingo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo2FocusLost
        focusLost(domingo2, dom[2]);
    }//GEN-LAST:event_domingo2FocusLost

    private void domingo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo3FocusLost
        focusLost(domingo3, dom[3]);
    }//GEN-LAST:event_domingo3FocusLost

    private void domingo4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo4FocusLost
        focusLost(domingo4, dom[4]);
    }//GEN-LAST:event_domingo4FocusLost

    private void domingo5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo5FocusLost
        focusLost(domingo5, dom[5]);
    }//GEN-LAST:event_domingo5FocusLost

    private void domingo6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo6FocusLost
        focusLost(domingo6, dom[6]);
    }//GEN-LAST:event_domingo6FocusLost

    private void domingo7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo7FocusLost
        focusLost(domingo7, dom[7]);
    }//GEN-LAST:event_domingo7FocusLost

    private void domingo8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo8FocusLost
        focusLost(domingo8, dom[8]);
    }//GEN-LAST:event_domingo8FocusLost

    private void domingo9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo9FocusLost
        focusLost(domingo9, dom[9]);
    }//GEN-LAST:event_domingo9FocusLost

    private void domingo10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo10FocusLost
        focusLost(domingo10, dom[10]);
    }//GEN-LAST:event_domingo10FocusLost

    private void domingo11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo11FocusLost
        focusLost(domingo11, dom[11]);
    }//GEN-LAST:event_domingo11FocusLost

    private void domingo12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo12FocusLost
        focusLost(domingo12, dom[12]);
    }//GEN-LAST:event_domingo12FocusLost

    private void domingo13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo13FocusLost
        focusLost(domingo13, dom[13]);
    }//GEN-LAST:event_domingo13FocusLost

    private void domingo14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo14FocusLost
        focusLost(domingo14, dom[14]);
    }//GEN-LAST:event_domingo14FocusLost

    private void domingo15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo15FocusLost
        focusLost(domingo15, dom[15]);
    }//GEN-LAST:event_domingo15FocusLost

    private void domingo16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo16FocusLost
        focusLost(domingo16, dom[16]);
    }//GEN-LAST:event_domingo16FocusLost

    private void domingo17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo17FocusLost
        focusLost(domingo17, dom[17]);
    }//GEN-LAST:event_domingo17FocusLost

    private void domingo18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo18FocusLost
        focusLost(domingo18, dom[18]);
    }//GEN-LAST:event_domingo18FocusLost

    private void domingo19FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo19FocusLost
        focusLost(domingo19, dom[19]);
    }//GEN-LAST:event_domingo19FocusLost

    private void domingo20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo20FocusLost
        focusLost(domingo20, dom[20]);
    }//GEN-LAST:event_domingo20FocusLost

    private void domingo21FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo21FocusLost
        focusLost(domingo21, dom[21]);
    }//GEN-LAST:event_domingo21FocusLost

    private void domingo22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo22FocusLost
        focusLost(domingo22, dom[22]);
    }//GEN-LAST:event_domingo22FocusLost

    private void domingo23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo23FocusLost
        focusLost(domingo23, dom[23]);
    }//GEN-LAST:event_domingo23FocusLost

    private void domingo24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo24FocusLost
        focusLost(domingo24, dom[24]);
    }//GEN-LAST:event_domingo24FocusLost

    private void Lunes1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes1FocusGained
        setWhite();
        panel1.setBackground(Color.green);
        fila = 1;
    }//GEN-LAST:event_Lunes1FocusGained

    private void domingo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo2FocusGained
        setWhite();
        panel2.setBackground(Color.green);
        fila = 2;
    }//GEN-LAST:event_domingo2FocusGained

    private void viernes3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes3FocusGained
        setWhite();
        panel3.setBackground(Color.green);
        fila = 3;
    }//GEN-LAST:event_viernes3FocusGained

    private void Lunes4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes4FocusGained
        setWhite();
        panel4.setBackground(Color.green);
        fila = 4;
    }//GEN-LAST:event_Lunes4FocusGained

    private void domingo5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo5FocusGained
        setWhite();
        panel5.setBackground(Color.green);
        fila = 5;
    }//GEN-LAST:event_domingo5FocusGained

    private void jueves6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves6FocusGained
        setWhite();
        panel6.setBackground(Color.green);
        fila = 6;
    }//GEN-LAST:event_jueves6FocusGained

    private void miercoles7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles7FocusGained
        setWhite();
        panel7.setBackground(Color.green);
        fila = 7;
    }//GEN-LAST:event_miercoles7FocusGained

    private void miercoles8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles8FocusGained
        setWhite();
        panel8.setBackground(Color.green);
        fila = 8;
    }//GEN-LAST:event_miercoles8FocusGained

    private void martes9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes9FocusGained
        setWhite();
        panel9.setBackground(Color.green);
        fila = 9;
    }//GEN-LAST:event_martes9FocusGained

    private void martes10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes10FocusGained
        setWhite();
        panel10.setBackground(Color.green);
        fila = 10;
    }//GEN-LAST:event_martes10FocusGained

    private void domingo11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo11FocusGained
        setWhite();
        panel11.setBackground(Color.green);
        fila = 11;
    }//GEN-LAST:event_domingo11FocusGained

    private void martes12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes12FocusGained
        setWhite();
        panel12.setBackground(Color.green);
        fila = 12;
    }//GEN-LAST:event_martes12FocusGained

    private void viernes13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viernes13FocusGained
        setWhite();
        panel13.setBackground(Color.green);
        fila = 13;
    }//GEN-LAST:event_viernes13FocusGained

    private void domingo14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo14FocusGained
        setWhite();
        panel14.setBackground(Color.green);
        fila = 14;
    }//GEN-LAST:event_domingo14FocusGained

    private void jueves15FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves15FocusGained
        setWhite();
        panel15.setBackground(Color.green);
        fila = 15;
    }//GEN-LAST:event_jueves15FocusGained

    private void jueves16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jueves16FocusGained
        setWhite();
        panel16.setBackground(Color.green);
        fila = 16;
    }//GEN-LAST:event_jueves16FocusGained

    private void sabado17FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado17FocusGained
        setWhite();
        panel17.setBackground(Color.green);
        fila = 17;
    }//GEN-LAST:event_sabado17FocusGained

    private void sabado18FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado18FocusGained
        setWhite();
        panel18.setBackground(Color.green);
        fila = 18;
    }//GEN-LAST:event_sabado18FocusGained

    private void miercoles19FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miercoles19FocusGained
        setWhite();
        panel19.setBackground(Color.green);
        fila = 19;
    }//GEN-LAST:event_miercoles19FocusGained

    private void Lunes20FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Lunes20FocusGained
        setWhite();
        panel20.setBackground(Color.green);
        fila = 20;
    }//GEN-LAST:event_Lunes20FocusGained

    private void domingo21FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_domingo21FocusGained
        setWhite();
        panel21.setBackground(Color.green);
        fila = 21;
    }//GEN-LAST:event_domingo21FocusGained

    private void sabado22FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado22FocusGained
        setWhite();
        panel22.setBackground(Color.green);
        fila = 22;
    }//GEN-LAST:event_sabado22FocusGained

    private void sabado23FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sabado23FocusGained
        setWhite();
        panel23.setBackground(Color.green);
        fila = 23;
    }//GEN-LAST:event_sabado23FocusGained

    private void martes24FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_martes24FocusGained
        setWhite();
        panel24.setBackground(Color.green);
        fila = 24;
    }//GEN-LAST:event_martes24FocusGained

    private void focus1(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focus1
        setWhiteDias();
        pLunes.setBackground(Color.green);
        col = 1;
    }//GEN-LAST:event_focus1

    private void focus2(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focus2
        setWhiteDias();
        pMartes.setBackground(Color.green);
        col = 2;
    }//GEN-LAST:event_focus2

    private void focusMiercoles(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusMiercoles
        setWhiteDias();
        pMiercoles.setBackground(Color.green);
        col = 3;
    }//GEN-LAST:event_focusMiercoles

    private void focusJueves(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusJueves
        setWhiteDias();
        pJueves.setBackground(Color.green);
        col = 4;
    }//GEN-LAST:event_focusJueves

    private void focusViernes(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusViernes
        setWhiteDias();
        pViernes.setBackground(Color.green);
        col = 5;
    }//GEN-LAST:event_focusViernes

    private void focusSabado(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusSabado
        setWhiteDias();
        pSabado.setBackground(Color.green);
        col = 6;
    }//GEN-LAST:event_focusSabado

    private void focusDomingo(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusDomingo
        setWhiteDias();
        pDomingo.setBackground(Color.green);
        col = 7;
    }//GEN-LAST:event_focusDomingo


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Lunes1;
    private javax.swing.JTextField Lunes10;
    private javax.swing.JTextField Lunes11;
    private javax.swing.JTextField Lunes12;
    private javax.swing.JTextField Lunes13;
    private javax.swing.JTextField Lunes14;
    private javax.swing.JTextField Lunes15;
    private javax.swing.JTextField Lunes16;
    private javax.swing.JTextField Lunes17;
    private javax.swing.JTextField Lunes18;
    private javax.swing.JTextField Lunes19;
    private javax.swing.JTextField Lunes2;
    private javax.swing.JTextField Lunes20;
    private javax.swing.JTextField Lunes21;
    private javax.swing.JTextField Lunes22;
    private javax.swing.JTextField Lunes23;
    private javax.swing.JTextField Lunes24;
    private javax.swing.JTextField Lunes3;
    private javax.swing.JTextField Lunes4;
    private javax.swing.JTextField Lunes5;
    private javax.swing.JTextField Lunes6;
    private javax.swing.JTextField Lunes7;
    private javax.swing.JTextField Lunes8;
    private javax.swing.JTextField Lunes9;
    private javax.swing.JPanel btnSalir;
    private RSMaterialComponent.RSComboBoxMaterial cmbSemanas;
    private javax.swing.JLabel domingo;
    private javax.swing.JTextField domingo1;
    private javax.swing.JTextField domingo10;
    private javax.swing.JTextField domingo11;
    private javax.swing.JTextField domingo12;
    private javax.swing.JTextField domingo13;
    private javax.swing.JTextField domingo14;
    private javax.swing.JTextField domingo15;
    private javax.swing.JTextField domingo16;
    private javax.swing.JTextField domingo17;
    private javax.swing.JTextField domingo18;
    private javax.swing.JTextField domingo19;
    private javax.swing.JTextField domingo2;
    private javax.swing.JTextField domingo20;
    private javax.swing.JTextField domingo21;
    private javax.swing.JTextField domingo22;
    private javax.swing.JTextField domingo23;
    private javax.swing.JTextField domingo24;
    private javax.swing.JTextField domingo3;
    private javax.swing.JTextField domingo4;
    private javax.swing.JTextField domingo5;
    private javax.swing.JTextField domingo6;
    private javax.swing.JTextField domingo7;
    private javax.swing.JTextField domingo8;
    private javax.swing.JTextField domingo9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel jueves;
    private javax.swing.JTextField jueves1;
    private javax.swing.JTextField jueves10;
    private javax.swing.JTextField jueves11;
    private javax.swing.JTextField jueves12;
    private javax.swing.JTextField jueves13;
    private javax.swing.JTextField jueves14;
    private javax.swing.JTextField jueves15;
    private javax.swing.JTextField jueves16;
    private javax.swing.JTextField jueves17;
    private javax.swing.JTextField jueves18;
    private javax.swing.JTextField jueves19;
    private javax.swing.JTextField jueves2;
    private javax.swing.JTextField jueves20;
    private javax.swing.JTextField jueves21;
    private javax.swing.JTextField jueves22;
    private javax.swing.JTextField jueves23;
    private javax.swing.JTextField jueves24;
    private javax.swing.JTextField jueves3;
    private javax.swing.JTextField jueves4;
    private javax.swing.JTextField jueves5;
    private javax.swing.JTextField jueves6;
    private javax.swing.JTextField jueves7;
    private javax.swing.JTextField jueves8;
    private javax.swing.JTextField jueves9;
    private javax.swing.JLabel lblDepa;
    private javax.swing.JLabel lblYear;
    private javax.swing.JLabel lunes;
    private javax.swing.JLabel martes;
    private javax.swing.JTextField martes1;
    private javax.swing.JTextField martes10;
    private javax.swing.JTextField martes11;
    private javax.swing.JTextField martes12;
    private javax.swing.JTextField martes13;
    private javax.swing.JTextField martes14;
    private javax.swing.JTextField martes15;
    private javax.swing.JTextField martes16;
    private javax.swing.JTextField martes17;
    private javax.swing.JTextField martes18;
    private javax.swing.JTextField martes19;
    private javax.swing.JTextField martes2;
    private javax.swing.JTextField martes20;
    private javax.swing.JTextField martes21;
    private javax.swing.JTextField martes22;
    private javax.swing.JTextField martes23;
    private javax.swing.JTextField martes24;
    private javax.swing.JTextField martes3;
    private javax.swing.JTextField martes4;
    private javax.swing.JTextField martes5;
    private javax.swing.JTextField martes6;
    private javax.swing.JTextField martes7;
    private javax.swing.JTextField martes8;
    private javax.swing.JTextField martes9;
    private javax.swing.JLabel miercoles;
    private javax.swing.JTextField miercoles1;
    private javax.swing.JTextField miercoles10;
    private javax.swing.JTextField miercoles11;
    private javax.swing.JTextField miercoles12;
    private javax.swing.JTextField miercoles13;
    private javax.swing.JTextField miercoles14;
    private javax.swing.JTextField miercoles15;
    private javax.swing.JTextField miercoles16;
    private javax.swing.JTextField miercoles17;
    private javax.swing.JTextField miercoles18;
    private javax.swing.JTextField miercoles19;
    private javax.swing.JTextField miercoles2;
    private javax.swing.JTextField miercoles20;
    private javax.swing.JTextField miercoles21;
    private javax.swing.JTextField miercoles22;
    private javax.swing.JTextField miercoles23;
    private javax.swing.JTextField miercoles24;
    private javax.swing.JTextField miercoles3;
    private javax.swing.JTextField miercoles4;
    private javax.swing.JTextField miercoles5;
    private javax.swing.JTextField miercoles6;
    private javax.swing.JTextField miercoles7;
    private javax.swing.JTextField miercoles8;
    private javax.swing.JTextField miercoles9;
    private javax.swing.JPopupMenu opciones;
    private javax.swing.JPanel pDomingo;
    private javax.swing.JPanel pJueves;
    private javax.swing.JPanel pLunes;
    private javax.swing.JPanel pMartes;
    private javax.swing.JPanel pMiercoles;
    private javax.swing.JPanel pSabado;
    private javax.swing.JPanel pViernes;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel10;
    private javax.swing.JPanel panel11;
    private javax.swing.JPanel panel12;
    private javax.swing.JPanel panel13;
    private javax.swing.JPanel panel14;
    private javax.swing.JPanel panel15;
    private javax.swing.JPanel panel16;
    private javax.swing.JPanel panel17;
    private javax.swing.JPanel panel18;
    private javax.swing.JPanel panel19;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel20;
    private javax.swing.JPanel panel21;
    private javax.swing.JPanel panel22;
    private javax.swing.JPanel panel23;
    private javax.swing.JPanel panel24;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel panel5;
    private javax.swing.JPanel panel6;
    private javax.swing.JPanel panel7;
    private javax.swing.JPanel panel8;
    private javax.swing.JPanel panel9;
    private javax.swing.JLabel sabado;
    private javax.swing.JTextField sabado1;
    private javax.swing.JTextField sabado10;
    private javax.swing.JTextField sabado11;
    private javax.swing.JTextField sabado12;
    private javax.swing.JTextField sabado13;
    private javax.swing.JTextField sabado14;
    private javax.swing.JTextField sabado15;
    private javax.swing.JTextField sabado16;
    private javax.swing.JTextField sabado17;
    private javax.swing.JTextField sabado18;
    private javax.swing.JTextField sabado19;
    private javax.swing.JTextField sabado2;
    private javax.swing.JTextField sabado20;
    private javax.swing.JTextField sabado21;
    private javax.swing.JTextField sabado22;
    private javax.swing.JTextField sabado23;
    private javax.swing.JTextField sabado24;
    private javax.swing.JTextField sabado3;
    private javax.swing.JTextField sabado4;
    private javax.swing.JTextField sabado5;
    private javax.swing.JTextField sabado6;
    private javax.swing.JTextField sabado7;
    private javax.swing.JTextField sabado8;
    private javax.swing.JTextField sabado9;
    private javax.swing.JLabel viernes;
    private javax.swing.JTextField viernes1;
    private javax.swing.JTextField viernes10;
    private javax.swing.JTextField viernes11;
    private javax.swing.JTextField viernes12;
    private javax.swing.JTextField viernes13;
    private javax.swing.JTextField viernes14;
    private javax.swing.JTextField viernes15;
    private javax.swing.JTextField viernes16;
    private javax.swing.JTextField viernes17;
    private javax.swing.JTextField viernes18;
    private javax.swing.JTextField viernes19;
    private javax.swing.JTextField viernes2;
    private javax.swing.JTextField viernes20;
    private javax.swing.JTextField viernes21;
    private javax.swing.JTextField viernes22;
    private javax.swing.JTextField viernes23;
    private javax.swing.JTextField viernes24;
    private javax.swing.JTextField viernes3;
    private javax.swing.JTextField viernes4;
    private javax.swing.JTextField viernes5;
    private javax.swing.JTextField viernes6;
    private javax.swing.JTextField viernes7;
    private javax.swing.JTextField viernes8;
    private javax.swing.JTextField viernes9;
    // End of variables declaration//GEN-END:variables
}
