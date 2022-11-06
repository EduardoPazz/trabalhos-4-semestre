package org.example.services;

import org.example.entities.Auth;
import org.example.entities.AuthResponse;
import org.example.entities.ClientCredentials;
import org.example.entities.DeliveryResponse;
import org.example.entities.Message;
import org.example.entities.MessagePackage;
import org.example.entities.ReceiveClientMessageRequestPackage;
import org.example.entities.ReceiveClientMessageResponsePackage;
import org.example.entities.ServerCredentials;
import org.example.enums.AuthStatusEnum;
import org.example.enums.DeliveryStatus;
import org.example.enums.HostTypeEnum;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;
import org.example.exceptions.NotAuthenticatedException;
import org.example.repositories.ClientRepository;
import org.example.requestsService.RequestServices;

import java.io.IOException;
import java.time.LocalDateTime;

public class ClientService {

    private final RequestServices requestServices;
    private final ClientRepository clientRepository;

    public ClientService(final RequestServices requestService, final ClientRepository clientRepository) {
        requestServices = requestService;
        this.clientRepository = clientRepository;
    }





    public void sendMessage(final String recipientEmail, final String subject,
            final String body) throws ClientNotFoundException, NotAuthenticatedException, IOException, ClassNotFoundException, DomainNotFoundException {

        final ClientCredentials clientCredentials = clientRepository.getClientCredentials();
        final ServerCredentials serverAddress = clientRepository.getServerCredentials();

        final Message message = new Message(recipientEmail, clientCredentials.username(), serverAddress.domain(), subject, body);
        final MessagePackage messagePackage =
                new MessagePackage(HostTypeEnum.CLIENT, clientCredentials.token(), message);

        final DeliveryResponse response =
                (DeliveryResponse) requestServices.requestServer(serverAddress, messagePackage);

        if (response.getStatus() == DeliveryStatus.UNKNOWN_CLIENT) {
            throw new ClientNotFoundException("Cliente não encontrado!");
        }

        if (response.getStatus() == DeliveryStatus.UNKNOWN_DOMAIN) {
            throw new DomainNotFoundException("Domínio não encontrado!");
        }

        if (response.getStatus() == DeliveryStatus.NOT_AUTHENTICATED) {
            throw new NotAuthenticatedException("Não autenticado! Sessão expirou!");
        }
    }




    public void receiveMessage(final LocalDateTime dateFrom,
            final LocalDateTime dateTo) throws IOException, ClassNotFoundException {
        //TODO:
        // - Chamar função para enviar mensagem ao servidor Host para receber as mensagens
        // - Armazenar isso no repositório do cliente

        final var clientAddressData = clientRepository.getClientCredentials();
        final ServerCredentials serverCredentials = clientRepository.getServerCredentials();

        final var messageRequest = new ReceiveClientMessageRequestPackage(clientAddressData, dateFrom, dateTo);
        final var response =
                (ReceiveClientMessageResponsePackage) requestServices.requestServer(serverCredentials, messageRequest);

        clientRepository.storeMessages(response.getMessages());
    }





    public void authenticate(final String username,
            final String password) throws NotAuthenticatedException, IOException, ClassNotFoundException {
        final Auth auth = new Auth(username, password);
        final ServerCredentials serverCredentials = clientRepository.getServerCredentials();

        final AuthResponse authResponse;
        authResponse = (AuthResponse) requestServices.requestServer(serverCredentials, auth);

        if (authResponse.getAuthStatus() == AuthStatusEnum.AUTHENTICATED) {
            final ClientCredentials clientCredentials = new ClientCredentials(username, authResponse.getToken());

            clientRepository.setClientCredentials(clientCredentials);
        } else {
            throw new NotAuthenticatedException("Falha ao autenticar, verifique suas credenciais.");
        }
    }
}
