package org.example.repositories;

import org.example.entities.ClientAddressCredentials;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerRepository {


    private List<ClientAddressCredentials> clients;

    public ServerRepository()
    {
        clients = new ArrayList<>();
        clients.add(new ClientAddressCredentials(
                "127.0.0.1",
                666,
                "vinicius",
                "123"
        ));
    }


    public ClientAddressCredentials getByAliasAndPassword(String alias, String password)
    {
        var client = clients.stream()
                .filter(
                        x -> x.getAlias().equals(alias)
                                && x.getPassword().equals(password)
                ).findFirst()
                .orElse(null);

        return client;
    }


    public void UpdateTokenClientCredentials(String alias, String token, LocalDate expiresDate)
    {
        var client = clients.stream()
                .filter(
                        x -> x.getAlias().equals(alias)
                ).findFirst()
                .get();

        client.setToken(token);
        client.setExpiresTokenIn(expiresDate);
    }


}
