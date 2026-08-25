package com.app.sockets.dao;

public interface ConversacionDAO {

    public void registrarConversacionA(String mensaje,String chat, String usuario);

    public void registrarConversacionB(String mensaje,String chat, String usuario);

}
