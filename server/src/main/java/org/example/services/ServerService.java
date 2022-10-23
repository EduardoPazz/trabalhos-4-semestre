package org.example.services;

import org.example.entities.Auth;
import org.example.entities.AuthResponse;
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
                    "auth",
                    0,
                    "",
                    null
            );
        }

        String token = "ABC1234";
        var expiresDate = LocalDate.now().plusDays(1);

        _serverRepository.UpdateTokenClientCredentials(auth.getAlias(),token,expiresDate);

        return new AuthResponse(
                "auth",
                1,
                token,
                expiresDate
        );

    }







}
