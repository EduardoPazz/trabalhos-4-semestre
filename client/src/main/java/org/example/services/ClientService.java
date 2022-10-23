package org.example.services;

import org.example.entities.*;
import org.example.enums.AuthStatusEnum;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.NotAuthenticatedException;
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



    public void sendMessage(
            String emailAddressParam,
            String fromAliasParam,
            String subjectParam,
            String bodyParam
    ) throws ClientNotFoundException, NotAuthenticatedException {

        var message = new Message(emailAddressParam, fromAliasParam, subjectParam, bodyParam);
        var serverAddress = _clientRepository.GetConectedServer();
        var token = _clientRepository.getTokenClient();

        /*var sendMessageResponse = _requestService.SendMessageRequest(serverAddress, message, token);

        if(sendMessageResponse.getStatus() == DeliveryStatusEnum.UNKNOW_CLIENT)
        {
            throw new ClientNotFoundException("Cliente não encontrado!");
        }
        if(sendMessageResponse.getStatus() == DeliveryStatusEnum.UNKNOW_DOMAIN)
        {
            throw new ClientNotFoundException("Domínio não encontrado!");
        }
        if(sendMessageResponse.getStatus() == DeliveryStatusEnum.NOT_AUTHENTICATED)
        {
            throw new NotAuthenticatedException("Não autenticado! Sessão expirou!");
        }*/
    }

    public void receiveMessage()
    {
        //TODO:
        // - Chamar função para enviar mensagem ao servidor Host para receber as mensagens
        // - Armazenar isso no repositório (BD) do cliente
    }


    public void authenticate(String alias, String password) throws NotAuthenticatedException
    {
        var auth = new Auth(alias, password);
        var serverAddress = _clientRepository.GetConectedServer();
        var authResponse = (AuthResponse)_requestService.requestServer(serverAddress, auth);

        if(authResponse.getAuthStatus() == AuthStatusEnum.AUTHENTICATED){
            _clientRepository.setTokenClient(authResponse.getToken());
        }else{
            throw new NotAuthenticatedException("Falha ao autenticar, verifique suas credenciais!");
        }
    }
}
