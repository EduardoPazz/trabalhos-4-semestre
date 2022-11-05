package org.example.services;

import org.example.entities.Auth;
import org.example.entities.AuthResponse;
import org.example.entities.ClientAddressCredentials;
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

import java.time.LocalDate;

public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(final ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    // TODO: implement real authentication
    public AuthResponse authRequest(final Auth auth) {
        ClientAddressCredentials clientCredentials = null;
        try {
            clientCredentials = serverRepository.getClientByAliasAndPassword(auth.getAlias(), auth.getPassword());
        } catch (final ClientNotFoundException e) {
            return new AuthResponse(AuthStatusEnum.NOT_AUTHENTICATED, "", null);
        }

        final String token = "ABC1234";
        final var expiresDate = LocalDate.now().plusDays(1);

        try {
            serverRepository.updateTokenClientCredentials(auth.getAlias(), token, expiresDate);
        } catch (final ClientNotFoundException e) {
            return new AuthResponse(AuthStatusEnum.NOT_AUTHENTICATED, "", null);
        }

        return new AuthResponse(AuthStatusEnum.AUTHENTICATED, token, expiresDate);
    }

    public DeliveryResponse receiveMessage(final MessagePackage message) {
        return switch (message.hostType()) {
            case CLIENT -> sendMessageFromClient(message);
            case SERVER -> receiveMessageFromServer(message);
        };
    }

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

    private DeliveryResponse receiveMessageFromServer(final MessagePackage messagePackage) {
        final Message message = messagePackage.message();

        final String recipientDomain = message.getToDomain();
        final String recipientAlias = message.getToAlias();

        if (!recipientDomain.equals(serverRepository.getOwnDomain())) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_DOMAIN);
        }

        return storeMessageIfPossibleAndGetDeliveryResponse(message, recipientAlias);

        //TODO:
        // - Como reconhecer o servidor?
        // - Vai fazer alguns passos de identificação de servidor?
        // - Pensar em uma forma de apresentação de servodor, casos os mesmos não sejam conhecidos ainda
    }

    private DeliveryResponse storeMessageIfPossibleAndGetDeliveryResponse(final Message message,
            final String recipientAlias) {
        try {
            serverRepository.storeMessage(recipientAlias, message);
            return new DeliveryResponse(DeliveryStatus.SUCCESS);
        } catch (final ClientNotFoundException e) {
            return new DeliveryResponse(DeliveryStatus.UNKNOWN_CLIENT);
        }
    }

    public ReceiveClientMessageResponsePackage receiveClientMessageRequest(
            final ReceiveClientMessageRequestPackage request) {

        final ClientCredentials clientCredentials = request.getClientAddress();
        final LocalDate dateFrom = request.getDateFrom();
        final LocalDate dateTo = request.getDateTo();

        return new ReceiveClientMessageResponsePackage(clientCredentials, dateFrom, dateTo,
                                                       serverRepository.getMessagesByClientAddressAndDateRange(
                                                               clientCredentials, dateFrom, dateTo));
    }
}
