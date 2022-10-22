package org.example.repositories;

import org.example.core.ServerAddress;

public class ClientRepository {


    public ServerAddress GetConectedServer(){
        ServerAddress serverAddress = new ServerAddress();
        serverAddress.setAddress("1231231232");
        serverAddress.setDomain("usp.br");
        return serverAddress;
    }

    //TODO:
    // - Listar Mensagens Recebidas
    // - Armazenar mensagens Recebidas
    // - Consultar Serivor Conectado

}
