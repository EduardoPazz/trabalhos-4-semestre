package org.example.services;

import org.example.entities.*;
import org.example.enums.AuthStatusEnum;
import org.example.enums.DeliveryStatusEnum;
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
            String subjectParam,
            String bodyParam
    ) throws ClientNotFoundException, NotAuthenticatedException {


        var clientAddressData = _clientRepository.getClientAddress();
        var serverAddress = _clientRepository.GetConectedServer();

        var message = new Message(emailAddressParam, clientAddressData.getAlias(), subjectParam, bodyParam);
        var messagePackage = new MessagePackage(
                clientAddressData.getToken(),
                message
        );

        var response = (DeliveryResponse)_requestService.requestServer(serverAddress, messagePackage);

        /*var sendMessageResponse = _requestService.SendMessageRequest(serverAddress, message, token); */

        if(response.getStatus() == DeliveryStatusEnum.UNKNOW_CLIENT)
        {
            throw new ClientNotFoundException("Cliente não encontrado!");
        }

        if(response.getStatus() == DeliveryStatusEnum.UNKNOW_DOMAIN)
        {
            throw new ClientNotFoundException("Domínio não encontrado!");
        }

        if(response.getStatus() == DeliveryStatusEnum.NOT_AUTHENTICATED)
        {
            throw new NotAuthenticatedException("Não autenticado! Sessão expirou!");
        }
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
            var clientAdress = new ClientAddress(
                    alias,
                    "usp.br",
                    authResponse.getToken()
            );

            _clientRepository.setClientAddress(clientAdress);
        }else{
            throw new NotAuthenticatedException("Falha ao autenticar, verifique suas credenciais!");
        }
    }
}
