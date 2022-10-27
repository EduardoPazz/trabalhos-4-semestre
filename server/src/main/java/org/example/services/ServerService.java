package org.example.services;

import org.example.entities.Auth;
import org.example.entities.AuthResponse;
import org.example.entities.DeliveryResponse;
import org.example.entities.Message;
import org.example.entities.MessagePackage;
import org.example.entities.ServerCredentials;
import org.example.enums.AuthStatusEnum;
import org.example.enums.DeliveryStatus;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;
import org.example.repositories.ServerRepository;
import org.example.requestService.RequestService;

import java.time.LocalDate;

public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    // TODO: implement real authentication
    public AuthResponse authRequest(Auth auth) {
        var clientCredentials = serverRepository.getClientByAliasAndPassword(auth.getAlias(), auth.getPassword());

        if (clientCredentials == null) {
            return new AuthResponse(AuthStatusEnum.NOT_AUTHENTICATED, "", null);
        }

        String token = "ABC1234";
        var expiresDate = LocalDate.now().plusDays(1);

        serverRepository.updateTokenClientCredentials(auth.getAlias(), token, expiresDate);

        return new AuthResponse(AuthStatusEnum.AUTHENTICATED, token, expiresDate);
    }


    public DeliveryResponse receiveMessageRedirect(MessagePackage message) {
        return switch (message.hostType()) {
            case CLIENT -> sendMessageFromClient(message);
            case SERVER -> receiveMessageFromServer(message);
        };
    }

    private DeliveryResponse sendMessageFromClient(MessagePackage messagePackage) {
        Message message = messagePackage.message();

        String recipientDomain = message.getToDomain();
        String recipientAlias = message.getToAlias();

        if (recipientDomain.equals(serverRepository.getOwnDomain())) {
            return storeMessageIfPossibleAndGetDeliveryResponse(message, recipientAlias);
        }

        try {
            ServerCredentials recipientServer = serverRepository.getServerByDomain(recipientDomain);
            return (DeliveryResponse) RequestService.requestServer(recipientServer, messagePackage);
        } catch (DomainNotFoundException e) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_DOMAIN);
        }
    }


    private DeliveryResponse receiveMessageFromServer(MessagePackage messagePackage) {
        Message message = messagePackage.message();

        String recipientDomain = message.getToDomain();
        String recipientAlias = message.getToAlias();

        if (!recipientDomain.equals(serverRepository.getOwnDomain())) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_DOMAIN);
        }

        return storeMessageIfPossibleAndGetDeliveryResponse(message, recipientAlias);

        //TODO:
        // - Como reconhecer o servidor?
        // - Vai fazer alguns passos de identificação de servidor?
        // - Pensar em uma forma de apresentação de servodor, casos os mesmos não sejam conhecidos ainda
    }

    private DeliveryResponse storeMessageIfPossibleAndGetDeliveryResponse(Message message, String recipientAlias) {
        try {
            serverRepository.storeMessage(recipientAlias, message);
            return new DeliveryResponse(DeliveryStatus.SUCCESS);
        } catch (ClientNotFoundException e) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_CLIENT);
        }
    }


}
