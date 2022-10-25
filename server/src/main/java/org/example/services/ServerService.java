package org.example.services;

import org.example.entities.Auth;
import org.example.entities.AuthResponse;
import org.example.entities.DeliveryResponse;
import org.example.entities.MessagePackage;
import org.example.enums.AuthStatusEnum;
import org.example.enums.HostTypeEnum;
import org.example.repositories.ServerRepository;

import java.time.LocalDate;

public class ServerService {

    private final ServerRepository _serverRepository;

    public ServerService(
            ServerRepository serverRepository
    )
    {
        _serverRepository = serverRepository;
    }

    public AuthResponse AuthRequest(Auth auth)
    {
        var clientCredentials = _serverRepository.getByAliasAndPassword(auth.getAlias(),auth.getPassword());

        if(clientCredentials == null)
        {
            return new AuthResponse(
                    AuthStatusEnum.NOT_AUTHENTICATED,
                    "",
                    null
            );
        }

        String token = "ABC1234";
        var expiresDate = LocalDate.now().plusDays(1);

        _serverRepository.UpdateTokenClientCredentials(auth.getAlias(),token,expiresDate);

        return new AuthResponse(
                AuthStatusEnum.AUTHENTICATED,
                token,
                expiresDate
        );
    }




    public DeliveryResponse receiveMessageRedirect(MessagePackage message)
    {
        if(message.getHostTypeEnum() == HostTypeEnum.CLIENT)
            return sendMessageFromClient(message);
        else
            return receiveMessageFromServer(message);
    }

    private DeliveryResponse sendMessageFromClient(MessagePackage messagePackage)
    {
        //TODO:
        // - Verificar se é o domínio do destinatário é o próprio servidor
        // - Se for o próprio servidor,
        //       - Se o cliente for conhecido, armazene a mensagem no repositório
        //       - Se o cliente não for conhecido, retorne a mensagem UNKNOW_CLIENT
        // - Se for outro servidor, verifica se conhece o servidor
        // - Se não conhece o serivodor, retorne a mensagem de UNKNOW_SERVER
        // - Se o servidor destinatário não conhece o cliente retorne UNKNOW_CLIENT
    }


    private DeliveryResponse receiveMessageFromServer(MessagePackage messagePackage)
    {

        //TODO:
        // - Como reconhecer o servidor?
        // - Vai fazer alguns passos de identificação de servidor?
        // - Pensar em uma forma de apresentação de servodor, casos os mesmos não sejam conhecidos ainda
    }




}
