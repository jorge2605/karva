package com.app.sockets.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Servidor extends Observable implements Runnable {

    private int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        ServerSocket servidor = null;
        Socket socket = null;
        DataInputStream entrada;

        try {
            //Creamos el servidor del socket
            servidor = new ServerSocket(puerto);

            //Siempre estara escuchando peticiones
            while (true) {
                //Espero que el cliente se contecte
                socket = servidor.accept();
                entrada = new DataInputStream(socket.getInputStream());

                //Leemos el mensaje
                String mensaje = entrada.readUTF();

                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();

                socket.close();
            }
        } catch (IOException error) {
            System.out.println(error);
        }
    }

}
