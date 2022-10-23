package org.example.services;

import org.example.entities.Auth;
import org.example.entities.ClientAddress;
import org.example.entities.Message;
import org.example.entities.ServerAddress;
import org.example.repositories.ClientRepository;
import org.example.requestsService.RequestServices;

public class ClientService {

    private final RequestServices _requestService;
    private final ClientRepository _clientRepository;
    public ClientService(
            RequestServices requestServices,
            ClientRepository clientRepository)
    {
        _requestService = requestServices;
        _clientRepository = clientRepository;
    }

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


    public void authenticate(String alias, String password)
    {
        var auth = new Auth(alias, password);
        var serverAddress = _clientRepository.GetConectedServer();
        var authResponse = _requestService.SendRequestAuth(serverAddress, auth);
        _clientRepository.setTokenClient(authResponse.getToken());

        //TODO:
        // - Enviar mensagem de autenticação ao servidor, com usuário e senha
        // - Armazenar o token de comunicação
    }
}
