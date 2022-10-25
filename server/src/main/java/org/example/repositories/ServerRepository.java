package org.example.repositories;

import org.example.entities.ClientAddressCredentials;
import org.example.entities.ServerAddress;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerRepository {


    private List<ClientAddressCredentials> clients;
    private List<ServerAddress> knownServer;

    public ServerRepository()
    {
        clients = new ArrayList<>();
        clients.add(new ClientAddressCredentials(
                "127.0.0.1",
                666,
                "vinicius",
                "123"
        ));

        kownServer = new ArrayList<ServerAddress>();
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


    public ServerAddress getServerAddressByDomain(String domainParam)
    {
        var serverAddresses = knownServer.stream()
                .filter(x -> x.domain == domainParam)
                .findFirst()
                .orElse(null);

        return serverAddresses;
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
