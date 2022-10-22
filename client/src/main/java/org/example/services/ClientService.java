package org.example.services;

import org.example.core.ClientAddress;
import org.example.core.Message;
import org.example.core.ServerAddress;

public class ClientService {


    private ServerAddress hostedServer;
    private ClientAddress clientData;

    public void sendMessage(Message message)
    {
        //TODO:
        // - Receber mensagem da interface
        // - Enviar mensagem ao servidor HOST
    }

    public void receiveMessage()
    {
        //TODO:
        // - Chamar função para enviar mensagem ao servidor Host para receber as mensagens
        // - Armazenar isso no repositório (BD) do cliente
    }


    public void authenticate()
    {
        //TODO:
        // - Enviar mensagem de autenticação ao servidor, com usuário e senha
        // - Armazenar o token de comunicação
    }
}
