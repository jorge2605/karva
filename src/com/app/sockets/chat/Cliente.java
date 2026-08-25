package com.app.sockets.chat;

import java.io.*;
import java.net.Socket;

public class Cliente implements Runnable{

    //Creamos los atriburtos de puerto y mensaje
    private int puerto;
    private String mensaje;
    private String host;

    public Cliente(int puerto, String mensaje, String host) {
        this.puerto = puerto;
        this.mensaje = mensaje;
        this.host = host;
    }
    
    @Override
    public void run() {
        //Host del servidor
        final String HOST = host;
        
        //Puerto del servidor
        DataOutputStream salida;
        
        try{
            Socket socket = new Socket(HOST, puerto);
            salida = new DataOutputStream(socket.getOutputStream());
            
            //enviamos el mensaje
            salida.writeUTF(mensaje);
        }catch(IOException error){
            System.out.println(error);
        }
    }
    
}
