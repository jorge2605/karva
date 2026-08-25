package Modelo;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Stack;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class EnviarCorreoCambioCompras {

    public static void main(String[] args) {
        try {
            Stack<Integer> pila = new Stack<>();
            pila.add(10);
            pila.add(26);
            pila.add(39);
            pila.add(50);
            pila.add(61);
            pila.add(62);
            pila.add(63);
            pila.add(64);
            pila.add(65);
            pila.add(66);
            pila.add(86);
            pila.add(91);
            pila.add(105);
            pila.add(106);
            pila.add(107);
            pila.add(108);
            pila.add(109);
            pila.add(110);
            pila.add(115);
            pila.add(120);
            pila.add(120);
            pila.add(122);
            pila.add(123);
            pila.add(124);
            pila.add(126);
            pila.add(134);
            pila.add(140);
            pila.add(141);
            pila.add(142);
            pila.add(143);
            pila.add(144);
            pila.add(154);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroprov_compras where Correo is not null";
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            int cont1 = 0;
            while (rs.next()) {
                cont++;
                if(pila.search(cont) == -1){
                    cont1++;
                    String correo = rs.getString("Correo");
                    String nombre = rs.getString("Nombre");
                    System.out.println(cont + ".-" + nombre + " - " + correo);
                    enviarCorreo(correo);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void enviarCorreo(String proveedor) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("compras01@si3i.com", "3ileo2020##");
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("compras01@si3i.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(proveedor));
            message.setSubject("Cambio de comprador");

            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "        <tr>\n"
                    + "            <td align=\"center\" style=\"background-color: #f5f5f5; padding: 40px;\">\n"
                    + "                <table width=\"90%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" style=\"background-color: #ffffff; padding: 40px; color: #666666;\">\n"
                    + "                            <h1 style=\"font-size: 24px; margin-bottom: 20px; background-color: #005ec2; padding: 20px; color: white;\">Cambio de personal</h1>\n"
                    + "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Buen dia, Mi nombre es Daniela Castro"
                    + " nueva integrante en el departamento de compras, apartir de ahora yo sere la encargada del departamento, cualquier duda o aclaracion estoy a sus ordenes</p>\n"
                    + "                            <p style=\"color: #666666; font-size: 14px; margin-top: 40px; font-weight:700;\">Saludos!</p>\n"
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "            </td>\n"
                    + "        </tr>\n"
                    + "    </table>\n"
                    + "\n"
                    + "</body>";

            // Asignar contenido HTML al cuerpo del mensaje
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html");

            // Combinar las partes en un multiparte
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

//            JOptionPane.showMessageDialog(null, "El correo ha sido enviado correctamente.");

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }
}
