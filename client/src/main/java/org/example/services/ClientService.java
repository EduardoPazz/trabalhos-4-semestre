package org.example.services;

import java.time.LocalDate;
import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.spi.LocaleServiceProvider;

import org.example.entities.Auth;
import org.example.entities.AuthResponse;
import org.example.entities.ClientAddress;
import org.example.entities.DeliveryResponse;
import org.example.entities.Message;
import org.example.entities.MessagePackage;
import org.example.entities.ReceiveClientMessageResponsePackage;
import org.example.entities.ReceiveClientMessageRequestPackage;
import org.example.entities.ServerCredentials;
import org.example.enums.AuthStatusEnum;
import org.example.enums.DeliveryStatus;
import org.example.enums.HostTypeEnum;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;
import org.example.exceptions.NotAuthenticatedException;
import org.example.repositories.ClientRepository;
import org.example.requestsService.RequestServices;
import org.example.entities.MessageListRequisition;

public class ClientService {

    private final RequestServices requestServices;
    private final ClientRepository clientRepository;
    private ServerCredentials hostedServer;
    private ClientAddress clientData;

    public ClientService(RequestServices requestService, ClientRepository clientRepository) {
        requestServices = requestService;
        this.clientRepository = clientRepository;
    }

    public void sendMessage(String emailAddressParam, String subjectParam,
                            String bodyParam) throws ClientNotFoundException, NotAuthenticatedException {


        var clientAddressData = clientRepository.getClientAddress();
        ServerCredentials serverAddress = null;
        try {
            serverAddress = clientRepository.getConnectedServer(clientAddressData.getDomain());
        } catch (DomainNotFoundException e) {
            throw new RuntimeException(e);
        }

        var message = new Message(emailAddressParam, clientAddressData.getAlias(), subjectParam, bodyParam);
        var messagePackage = new MessagePackage(HostTypeEnum.CLIENT, clientAddressData.getToken(), message);

        var response = (DeliveryResponse) requestServices.requestServer(serverAddress, messagePackage);

        if (response.getStatus() == DeliveryStatus.UNKNOWN_CLIENT) {
            throw new ClientNotFoundException("Cliente não encontrado!");
        }

        if (response.getStatus() == DeliveryStatus.UNKNOWN_DOMAIN) {
            throw new ClientNotFoundException("Domínio não encontrado!");
        }

        if (response.getStatus() == DeliveryStatus.NOT_AUTHENTICATED) {
            throw new NotAuthenticatedException("Não autenticado! Sessão expirou!");
        }
    }

    public void receiveMessage(String alias, LocalDate dateFrom, LocalDate dateTo) throws ClientNotFoundException {
        //TODO:
        // - Chamar função para enviar mensagem ao servidor Host para receber as mensagens
        // - Armazenar isso no repositório do cliente

        var clientAddressData = clientRepository.getClientAddress();

        var MessageRequest = new ReceiveClientMessageRequestPackage(clientAddressData, dateFrom, dateTo);
        var response = (ReceiveClientMessageResponsePackage) requestServices.requestServer(hostedServer, MessageRequest);

        clientRepository.saveMessages(response.getMessages());
        var MessageListRequisition = new MessageListRequisition(alias, dateFrom, dateTo);
        List<Message> messages = (clientMessagesBox) requestServices.requestServer(hostedServer, MessageListRequisition);


        // - Armazenar isso no repositório (BD) do cliente
        clientRepository.storeMessages(messages);
    }


    public void authenticate(String domain, String alias, String password) throws NotAuthenticatedException {
        var auth = new Auth(alias, password);
        ServerCredentials serverAddress = null;
        try {
            serverAddress = clientRepository.getConnectedServer(domain);
        } catch (DomainNotFoundException e) {
            throw new RuntimeException(e);
        }

        var authResponse = (AuthResponse) requestServices.requestServer(serverAddress, auth);

        if (authResponse.getAuthStatus() == AuthStatusEnum.AUTHENTICATED) {
            var clientAdress = new ClientAddress(alias, "usp.br", authResponse.getToken());

            clientRepository.setClientAddress(clientAdress);
        } else {
            throw new NotAuthenticatedException("Falha ao autenticar, verifique suas credenciais!");
        }
    }
}
