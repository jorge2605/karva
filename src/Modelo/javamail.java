package Modelo;

import Conexiones.Conexion;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Stack;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class javamail {

    public File ordenDeCompra;
    public boolean nuevamod;

    public void sendVentas(Stack<String> copias, String destinatario, String asunto, String descripcion, File OC, String Cotizacion) {
        String PO = null;
        if (OC != null) {
            PO = OC.getAbsolutePath();
        }
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ventas01@si3i.com", "Ventas01??");
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ventas01@si3i.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            for (String copia : copias) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(copia));
            }
            message.setSubject(asunto);

            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "        <tr>\n"
                    + "            <td align=\"center\" style=\"background-color: #f5f5f5; padding: 40px;\">\n"
                    + "                <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" style=\"background-color: #ffffff; padding: 40px; color: #666666;\">\n"
                    + "                            <h1 style=\"color: #333333;\">Nuevo Proyecto</h1>\n"
                    + "                            <p style=\"color: #666666;\">Buen dia se abre nuevo proyecto</p>\n"
                    + "                            <p style=\"color: #666666;\">" + descripcion + "</p>\n"
                    + "                            <p style=\"color: #666666;\">Saludos!</p>\n"
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

            // Crear el archivo adjunto
            BodyPart attachmentBodyPart = null;
            if (PO != null) {
                attachmentBodyPart = new MimeBodyPart();
                String archivoAdjunto = PO; // Ruta completa del archivo a adjuntar
                DataSource fuenteDatos = new FileDataSource(archivoAdjunto);
                attachmentBodyPart.setDataHandler(new DataHandler(fuenteDatos));
                attachmentBodyPart.setFileName(fuenteDatos.getName());
            }
            BodyPart attachmentBodyPart2 = new MimeBodyPart();
            String archivoAdjunto2 = Cotizacion; // Ruta completa del archivo a adjuntar
            DataSource fuenteDatos2 = new FileDataSource(archivoAdjunto2);
            attachmentBodyPart2.setDataHandler(new DataHandler(fuenteDatos2));
            attachmentBodyPart2.setFileName(fuenteDatos2.getName());

            // Combinar las partes en un multiparte
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (PO != null) {
                multipart.addBodyPart(attachmentBodyPart);
            }
            multipart.addBodyPart(attachmentBodyPart2);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("El correo ha sido enviado correctamente.");

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void sendCotizacion(String[] copias, String destinatario, String asunto,
            String descripcion, File OC, String correo, String pass, String empresa, String notas) {
        String PO = null;
        if (OC != null) {
            PO = OC.getAbsolutePath();
        }
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correo, pass);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            for (String copia : copias) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(copia));
            }
            message.setSubject(asunto);
            
            String mensaje = (notas.equals("")) ? "" :  "                            <p style=\"color: #666666;\">Notas: " + notas + "</p>\n";
            
            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "        <tr>\n"
                    + "            <td align=\"center\" style=\"background-color: #f5f5f5; padding: 40px;\">\n"
                    + "                <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" style=\"background-color: #ffffff; padding: 40px; color: #666666;\">\n"
                    + "                            <h1 style=\"color: #333333;\">Cotizacion <b>" + descripcion + "</b></h1>\n"
                    + "                            <p style=\"color: #666666;\">Buen dia " + empresa + ", les enviamos el documento con la cotizacion solicitada.</p>\n"
                    + "                            <p style=\"color: #666666;\">Saludos!</p>\n"
                    + mensaje 
                    + "                        </td>\n"
                    + "                    </tr>\n"
                    + "                </table>\n"
                    + "            </td>\n"
                    + "        </tr>\n"
                    + "    </table>\n"
                    + "\n"
                    + "</body>";

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html");

            BodyPart attachmentBodyPart = null;
            if (PO != null) {
                attachmentBodyPart = new MimeBodyPart();
                String archivoAdjunto = PO; // Ruta completa del archivo a adjuntar
                DataSource fuenteDatos = new FileDataSource(archivoAdjunto);
                attachmentBodyPart.setDataHandler(new DataHandler(fuenteDatos));
                attachmentBodyPart.setFileName(fuenteDatos.getName());
            }

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (PO != null) {
                multipart.addBodyPart(attachmentBodyPart);
            }
            message.setContent(multipart);
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "El correo ha sido enviado correctamente.");

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Error al enviar correo: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendAlmacen(String destinatario, String copia, String asunto, Stack<String> material, Stack<String> cant, Stack<String> cantR) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("almacen01@si3i.com", "almacen01?");
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("almacen01@si3i.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copia));
            message.setSubject(asunto);

            String mat = "";
            for (int i = 0; i < material.size(); i++) {
                mat += "<tr>\n";
                mat += "<td>" + material.get(i) + "</td>\n";
                mat += "<td>" + cant.get(i) + "</td>\n";
                double cantA, cantB;
                try {
                    cantA = Double.parseDouble(cant.get(i));
                } catch (Exception e) {
                    cantA = 0;
                }
                try {
                    cantB = Double.parseDouble(cantR.get(i));
                } catch (Exception e) {
                    cantB = 0;
                }
                double cantReal = cantA - cantB;
                mat += "<td>" + cantReal + "</td>\n";
                mat += "</tr>";

            }

            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "        <tr>\n"
                    + "            <td align=\"center\" style=\"background-color: #f5f5f5; padding: 40px;\">\n"
                    + "                <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" style=\"background-color: #ffffff; padding: 40px; color: #666666;\">\n"
                    + "                            <h1 style=\"font-size: 24px; margin-bottom: 20px; background-color: #9b2f2f; padding: 20px; color: white;\">Recibo de material</h1>\n"
                    + "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">El siguiente material ha llegado a almacen:</p>\n"
                    + "                            <table border=\"0\" style=\"width: 100%;\">\n"
                    + "                               <tr>"
                    + "                                   <th style=\"width: 70%; \">Descripcion</th>"
                    + "                                   <th>Cantidad</th>"
                    + "                                   <th>Cantidad Faltante</th>"
                    + "                               </tr>"
                    + "                               " + mat
                    + "                            </table>\n"
                    + //            "                            <p style=\"color: #666666; font-size: 14px; margin-top: 40px; font-weight:700;\">"+mat+"</p>\n" +
                    "                            <p style=\"color: #666666; font-size: 14px; margin-top: 40px; font-weight:700;\">Gracias,</p>\n"
                    + "                            <p style=\"color: #c93900; font-size: 14px; font-weight: 700;\">3i TOWI</p>\n"
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

            System.out.println("El correo ha sido enviado correctamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR AL ENVIAR CORREO: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void sendAprobacion(String requisicion, String para, String copia, String from, String pass) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("compras01@si3i.com"));
            message.setSubject("Nueva Aprobacion: " + requisicion);

            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <div style=\"width: 70%; background-color: #f2fafe;margin: 20px auto;\">\n"
                    + "        <p style=\"font-family: Lexend; font-weight: 700; font-size: 30px; margin: 0; text-align: center; padding: 20px; color: #9abeb6;\">Nueva aprobacion</p>\n"
                    + "        <p style=\"font-weight: 900; text-align: center; font-size: 100px; font-family: Lexend; margin: 20px; color: #df8c68;\">" + requisicion + "</p>\n"
                    + "        <br>\n"
                    + "    </div> "
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

            JOptionPane.showMessageDialog(null, "El correo ha sido enviado correctamente.");

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
            System.out.println(e);
        }
    }

    public void jorge(Stack<String> material, String notas, JTable tabla, String asunto, String to, String from, String pass, String copia) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("compras01@si3i.com" + copia));
            message.setSubject(asunto);

            String mat = "";
            String proyectos = "";
            for (int i = 0; i < material.size(); i++) {
                for (int j = 0; j < tabla.getRowCount(); j++) {
                    if (material.get(i).equals(tabla.getValueAt(j, nuevamod == true ? 0 : 7).toString())) {
                        String descripcion = tabla.getValueAt(j, nuevamod == true ? 1 : 3).toString();
                        String cantidad = tabla.getValueAt(j, nuevamod == true ? 4 : 1).toString();
                        String noParte = tabla.getValueAt(j, nuevamod == true ? 2 : 4).toString();
                        Object val = tabla.getValueAt(j, 6);

                        if (val != null) {
                            String texto = val.toString();
                            String valor;

                            if (texto.length() >= 4) {
                                valor = texto.substring(0, 4);
                            } else {
                                valor = texto; // usa completo si es corto
                            }

                            if (proyectos.equals("")) {
                                proyectos = valor;
                            } else {
                                proyectos += ", " + valor;
                            }
                        } else {
                            System.out.println("ERROR -> valor null en columna 6");
                        }

                        if (i == 0) {
                            mat = "<tr><td style='border-bottom: 1px solid rgb(163,163,163);'>" + (i + 1) + "</td><td style='border-bottom: 1px solid rgb(163,163,163);'>" + noParte + "</td><td style='border-bottom: 1px solid rgb(163,163,163);'> " + descripcion + " </td><td style='border-bottom: 1px solid rgb(163,163,163); text-align: center;'> " + cantidad + " </td>"
                                    + "</tr>";
                        } else {
                            mat += "<tr><td style='border-bottom: 1px solid rgb(163,163,163);'>" + (i + 1) + "</td><td style='border-bottom: 1px solid rgb(163,163,163);'>" + noParte + "</td><td style='border-bottom: 1px solid rgb(163,163,163);'> " + descripcion + " </td><td style='border-bottom: 1px solid rgb(163,163,163); text-align: center;'> " + cantidad + " </td>"
                                    + "</tr>";
                        }
                    }
                }
            }
            proyectos = nuevamod ? "PR" : proyectos;
            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "        <tr>\n"
                    + "            <td align=\"center\" style=\"background-color: #f5f5f5; padding: 40px;\">\n"
                    + "                <table width=\"90%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" style=\"background-color: #ffffff; padding: 40px; color: #666666;\">\n"
                    + "                            <h1 style=\"font-size: 24px; margin-bottom: 20px; background-color: #005ec2; padding: 20px; color: white;\">Solicitud de cotizacion</h1>\n"
                    + "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Buen dia, envio el siguiente material para cotizacion:</p>\n"
                    + "                            <table style=\"color: #666666; font-size: 14px; margin-top: 10px; font-weight:700;\"> <tr><td>No. Item</td><td style='width: 30%; text-align:center;'>No. de parte</td><td style='width: 50%; text-align:center;'>Descripcion</td><td>Cantidad</td></tr>" + mat + "</table>\n"
                    + "                            <p style=\"color: #666666; font-size: 14px; margin-top: 40px; font-weight:700;\">Saludos!</p>\n"
                    + "                            <p style=\"color: #c93900; font-size: 14px; font-weight: 700;\">Notas: " + notas + "</p>\n"
                    + "                            <p style=\"color: #1a1a1a; font-size: 14px; font-weight: 700;\">Proyectos: " + proyectos + "</p>\n"
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

            BodyPart attachmentBodyPart = null;
            if (ordenDeCompra != null) {
                attachmentBodyPart = new MimeBodyPart();
                String archivoAdjunto = ordenDeCompra.getAbsolutePath(); // Ruta completa del archivo a adjuntar
                DataSource fuenteDatos = new FileDataSource(archivoAdjunto);
                attachmentBodyPart.setDataHandler(new DataHandler(fuenteDatos));
                attachmentBodyPart.setFileName(fuenteDatos.getName());
            }
            // Combinar las partes en un multiparte
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (attachmentBodyPart != null) {
                multipart.addBodyPart(attachmentBodyPart);
            }

            message.setContent(multipart);

            Transport.send(message);

            JOptionPane.showMessageDialog(null, "El correo ha sido enviado correctamente.");

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
            System.out.println(e);
        }
    }

    public void sendOC(String notas, String asunto, String to, String from, String pass, String ordenNo, String ordenReal, String copia) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("compras01@si3i.com" + copia));
            message.setSubject(asunto);

            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "        <tr>\n"
                    + "            <td align=\"center\" style=\"background-color: #f5f5f5; padding: 40px;\">\n"
                    + "                <table width=\"90%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" style=\"background-color: #ffffff; padding: 40px; color: #666666;\">\n"
                    + "                            <h1 style=\"font-size: 24px; margin-bottom: 20px; background-color: #005ec2; padding: 20px; color: white;\">Orden de compra</h1>\n"
                    + "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Buen dia, envio orden de compra: " + ordenReal + "</p>\n"
                    + "                            <p style=\"color: #666666; font-size: 14px; margin-top: 40px; font-weight:700;\">Saludos!</p>\n"
                    + "                            <p style=\"color: #c93900; font-size: 14px; font-weight: 700;\">Notas: " + notas + "</p>\n"
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

            BodyPart attachmentBodyPart = null;
            if (ordenNo != null) {
                if (!ordenNo.equals("")) {
                    attachmentBodyPart = new MimeBodyPart();
                    String archivoAdjunto = ordenNo;
                    DataSource fuenteDatos = new FileDataSource(archivoAdjunto);
                    attachmentBodyPart.setDataHandler(new DataHandler(fuenteDatos));
                    attachmentBodyPart.setFileName(fuenteDatos.getName());
                }
            }
            // Combinar las partes en un multiparte
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (attachmentBodyPart != null) {
                multipart.addBodyPart(attachmentBodyPart);
            }

            message.setContent(multipart);

            Transport.send(message);

            JOptionPane.showMessageDialog(null, "El correo ha sido enviado correctamente.");

        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    public static void responderCorreo() {
        final String fromEmail = "jorge.santacruz@si3i.com"; // Tu dirección de correo empresarial
        final String password = "jorge.santacruz?"; // Tu contraseña de correo
        final String toEmail = "santacruz.leonel.1h@hotmail.com"; // La dirección de correo del remitente

        Properties properties = new Properties();
//        properties.put("mail.store.protocol", "imaps"); // Utiliza IMAPS para correos seguros
//        properties.put("mail.imap.host", "mail.si3i.com"); // Servidor IMAP de tu dominio
//        properties.put("mail.imap.port", "993"); // Puerto IMAPS
        properties.put("mail.smtp.host", "mail.si3i.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        try {
            // Conectar a la bandeja de entrada
            Store store = session.getStore("imaps");
            store.connect("mail.si3i.com", fromEmail, password);

            // Abrir la carpeta de entrada
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            // Obtener los mensajes
            Message[] messages = inbox.getMessages();

            if (messages.length > 0) {
                Message lastMessage = messages[messages.length - 4];

                Address[] fromAdd = lastMessage.getFrom();
                String para = "";
                for (Address fromAdd1 : fromAdd) {
                    para += ((InternetAddress) fromAdd1).getAddress();
                }

                // Crear la respuesta
                Message replyMessage = lastMessage.reply(true);
                replyMessage.setFrom(new InternetAddress(fromEmail));
                replyMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
                replyMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(fromEmail));
                replyMessage.setSubject(lastMessage.getSubject());

                String replyContent = "Estimado remitente,\n\n"
                        + "Gracias por su correo. Aquí está mi respuesta:\n\n"
                        + "=== Respuesta ===\n"
                        + "Tu respuesta aquí.";

                replyMessage.setText(replyContent);

                // Enviar la respuesta
                Transport.send(replyMessage);
                System.out.println("Respuesta enviada exitosamente.");
            } else {
                System.out.println("No se encontraron mensajes en la bandeja de entrada.");
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMail() {
        Properties props = new Properties();
//        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "*");

        System.setProperty("mail.debug", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("santacruz.leonel.1h@hotmail.com", "26052000Aa.");
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Stack<String> pila = new Stack<>();
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select * from registroprov_compras where Correo is not null";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    pila.push(rs.getString("Correo"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e);
            }
//            for (int i = 0; i < pila.size(); i++) {
//                System.out.println(pila.get(i));
//            }
//            for (int i = 9; i < pila.size(); i++) {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("compras01@si3i.com"));
            message.setFrom(new InternetAddress("compras01@si3i.com"));
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pila.get(i)));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("compras01@si3i.com"));
            message.setSubject("Condiciones de pago");

            String htmlContent = "<body style=\"font-family: Arial, sans-serif;\">\n"
                    + "\n"
                    + "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "        <tr>\n"
                    + "            <td align=\"center\" style=\"background-color: #f5f5f5; padding: 40px;\">\n"
                    + "                <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                    <tr>\n"
                    + "                        <td align=\"center\" style=\"background-color: #ffffff; padding: 40px; color: #666666;\">\n"
                    + "                            <h1 style=\"font-size: 24px; margin-bottom: 20px; background-color: #ffa73b; padding: 20px; color: white;\"><br>Estatus de copndiciones de pago<br></h1>\n"
                    + "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Buen dia</p>\n"
                    + "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Estamos actualizando nuestra base de datos,</p>\n"
                    + "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Sobre nuestras condiciones de pago,</p>\n"
                    + //                    "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Podria hacerme el favor de ayudarme corroborar </p>\n" + 
                    //                    "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">nuestras condiciones de pago y crédito</p>\n" + 
                    //                    "                            <p style=\"color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 20px; font-weight:700; \">Saludos Cordiales.</p>\n" + 
                    "                            <tr>\n"
                    + "                    <td>\n"
                    + "                        <table style=\"width: 100%;\">\n"
                    + "                            <tr style=\"color: rgb(2, 64, 158); font-weight: 700; font-size: 20px;\">\n"
                    + "                                <td>compras01@si3i.com</td>\n"
                    + "                                <td>656 791 1365</td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\"color: rgb(112, 112, 112); font-size: 12px;\">\n"
                    + "                                <td>correo</td>\n"
                    + "                                <td>telefono</td>\n"
                    + "                            </tr>\n"
                    + "                        </table>\n"
                    + "                    </td>"
                    + "                            <p style=\"color: #c93900; font-size: 14px; font-weight: 700;\">3i TOWI</p>\n"
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

            System.out.println("El correo ha sido enviado correctamente.");
//                }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
