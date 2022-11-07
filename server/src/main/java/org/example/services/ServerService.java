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
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;
import org.example.repositories.ServerRepository;
import org.example.requestService.RequestService;

import java.time.LocalDateTime;


//Classe responsável pela definição das funções realizadas pelo servidor
public class ServerService {

    private final ServerRepository serverRepository;

    //Incializa repositorio de servidores
    public ServerService(final ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    //Realiza a tratativa da autenticação do cliente. Recebe o pacote de autenticação e retorna o 
    //pacote de resposta de autenticação contendo o token de acesso com validade de 1 dia. Após 
    //a expiração do token é necessário realizar uma nova autenticação.
    public AuthResponse authRequest(final Auth auth) {
        try {
            serverRepository.getClientByAliasAndPassword(auth.getAlias(), auth.getPassword());
        } catch (final ClientNotFoundException e) {
            return new AuthResponse(AuthStatusEnum.NOT_AUTHENTICATED, "", null);
        }

        final String token = "ABC1234";
        final var expiresDate = LocalDateTime.now().plusDays(1);

        try {
            serverRepository.updateTokenClientCredentials(auth.getAlias(), token, expiresDate);
        } catch (final ClientNotFoundException e) {
            return new AuthResponse(AuthStatusEnum.NOT_AUTHENTICATED, "", null);
        }

        return new AuthResponse(AuthStatusEnum.AUTHENTICATED, token, expiresDate);
    }

    //Recebe uma mensagem tanto de servidor como de cliente.
    public DeliveryResponse receiveMessage(final MessagePackage message) {
        return switch (message.hostType()) {
            case CLIENT -> sendMessageFromClient(message);
            case SERVER -> receiveMessageFromServer(message);
        };
    }

    //Realiza um envio de mensagem solicitado por um cliente para outro cliente. 
    //Recebe o pacote de mensagem e retorna o pacote de resposta de entrega da mensagem.
    private DeliveryResponse sendMessageFromClient(final MessagePackage messagePackage) {
        final Message message = messagePackage.message();

        final String recipientDomain = message.getToDomain();
        final String recipientAlias = message.getToAlias();

        if (recipientDomain.equals(serverRepository.getOwnDomain())) {
            return storeMessageIfPossibleAndGetDeliveryResponse(message, recipientAlias);
        }

        try {
            final ServerCredentials recipientServer = serverRepository.getServerByDomain(recipientDomain);
            return (DeliveryResponse) RequestService.requestServer(recipientServer, messagePackage);
        } catch (final DomainNotFoundException e) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_DOMAIN);
        }
    }

    //Recebe mensagem de um servidor.
    private DeliveryResponse receiveMessageFromServer(final MessagePackage messagePackage) {
        final Message message = messagePackage.message();

        final String recipientDomain = message.getToDomain();
        final String recipientAlias = message.getToAlias();

        if (!recipientDomain.equals(serverRepository.getOwnDomain())) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_DOMAIN);
        }

        return storeMessageIfPossibleAndGetDeliveryResponse(message, recipientAlias);

    }

    //Armazena mensagem no repositório do servidor.
    private DeliveryResponse storeMessageIfPossibleAndGetDeliveryResponse(final Message message,
            final String recipientAlias) {
        try {
            serverRepository.storeMessage(recipientAlias, message);
            return new DeliveryResponse(DeliveryStatus.SUCCESS);
        } catch (final ClientNotFoundException e) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_CLIENT);
        }
    }

    //Realiza o envio de um pacote contendo a lista de mensagens de um cliente 
    //levando em conta um data range.
    public ReceiveClientMessageResponsePackage receiveClientMessageRequest(
            final ReceiveClientMessageRequestPackage request) {

        final ClientCredentials clientCredentials = request.getClientAddress();
        final LocalDateTime dateFrom = request.getDateFrom();
        final LocalDateTime dateTo = request.getDateTo();

        return new ReceiveClientMessageResponsePackage(clientCredentials, dateFrom, dateTo,
                                                       serverRepository.getMessagesByClientAddressAndDateRange(
                                                               clientCredentials, dateFrom, dateTo));
    }
}
