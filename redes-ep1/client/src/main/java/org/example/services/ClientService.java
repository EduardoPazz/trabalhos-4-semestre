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

/*
  Classe que representa o serviço do cliente. É onde são definidas todas as funções chamadas pelo cliente, como autenticação, envio e recebimento de mensagens.
 */

public class ClientService {

    private final RequestServices requestServices;
    private final ClientRepository clientRepository;

    public ClientService(final RequestServices requestService, final ClientRepository clientRepository) {
        requestServices = requestService;
        this.clientRepository = clientRepository;
    }


    //Realiza o envio de mensagens para outro cliente
    public void sendMessage(final String recipientEmail, final String subject,
            final String body) throws ClientNotFoundException, NotAuthenticatedException, IOException, ClassNotFoundException, DomainNotFoundException {

        final ClientCredentials clientCredentials = clientRepository.getClientCredentials();
        final ServerCredentials serverAddress = clientRepository.getServerCredentials();

        final Message message =
                new Message(recipientEmail, clientCredentials.username(), serverAddress.domain(), subject, body);
        final MessagePackage messagePackage =
                new MessagePackage(HostTypeEnum.CLIENT, clientCredentials.token(), message);

        //Envio de requisição ao servidor
        final DeliveryResponse response =
                (DeliveryResponse) requestServices.requestServer(serverAddress, messagePackage);

        //Tratamento de erros
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


    //Recebe lista de mensagens recebidas
    public void receiveMessage(final LocalDateTime dateFrom,
            final LocalDateTime dateTo) throws IOException, ClassNotFoundException {

        final var clientAddressData = clientRepository.getClientCredentials();
        final ServerCredentials serverCredentials = clientRepository.getServerCredentials();

        //Envia requisição ao servidor
        final var messageRequest = new ReceiveClientMessageRequestPackage(clientAddressData, dateFrom, dateTo);

        //Recebe resposta do servidor
        final var response =
                (ReceiveClientMessageResponsePackage) requestServices.requestServer(serverCredentials, messageRequest);

        //Armazena no repositório
        clientRepository.storeMessages(response.getMessages());
    }


    //Realiza autenticação do cliente
    public void authenticate(final String username,
            final String password) throws NotAuthenticatedException, IOException, ClassNotFoundException {
        final Auth auth = new Auth(username, password);
        final ServerCredentials serverCredentials = clientRepository.getServerCredentials();

        final AuthResponse authResponse;
        //Envia requisição ao servidor
        authResponse = (AuthResponse) requestServices.requestServer(serverCredentials, auth);

        if (authResponse.getAuthStatus() == AuthStatusEnum.AUTHENTICATED) {

            //Gera token de autenticação e armazena para sessão
            final ClientCredentials clientCredentials = new ClientCredentials(username, authResponse.getToken());

            clientRepository.setClientCredentials(clientCredentials);
        } else {
            throw new NotAuthenticatedException("Falha ao autenticar, verifique suas credenciais.");
        }
    }
}
