package org.example.repositories;

import org.example.entities.ClientAddressCredentials;
import org.example.entities.Message;
import org.example.entities.ServerCredentials;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerRepository {


    private Map<ClientAddressCredentials, Set<Message>> clientToMessages = new HashMap<>();
    private ServerCredentials ownCredentials = new ServerCredentials("127.0.0.1", 666, "usp.br");
    private Set<ServerCredentials> knownServers = new HashSet<>();

    public ServerRepository() {
        clientToMessages.put(new ClientAddressCredentials("127.0.0.1", 666, "vinicius", "123"), new HashSet<>());
    }

    public void storeMessage(String alias, Message message) throws ClientNotFoundException {
        getClientMessagesByAlias(alias).add(message);
    }

    private Set<Message> getClientMessagesByAlias(String alias) throws ClientNotFoundException {
        return clientToMessages.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getAlias().equals(alias))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException(alias))
                .getValue();
    }

    public ClientAddressCredentials getClientByAliasAndPassword(String alias, String password) {
        return clientToMessages.keySet().stream()
                .filter(client -> client.getAlias().equals(alias) && client.getPassword().equals(password))
                .findFirst()
                .get();
    }


    public ServerCredentials getServerByDomain(String domain) throws DomainNotFoundException {
        return knownServers.stream().filter(server -> server.domain().equals(domain)).findFirst().orElseThrow(() -> new DomainNotFoundException(domain));
    }


    public void updateTokenClientCredentials(String alias, String token, LocalDate expiresDate) {
        var outdatedClient =
                clientToMessages.keySet().stream().filter(client -> client.getAlias().equals(alias)).findFirst().get();

        outdatedClient.setToken(token);
        outdatedClient.setExpiresTokenIn(expiresDate);
    }

    public String getOwnDomain() {
        return ownCredentials.domain();
    }

}
