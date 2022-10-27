package org.example.repositories;

import lombok.AllArgsConstructor;
import org.example.entities.ClientAddressCredentials;
import org.example.entities.Message;
import org.example.entities.ServerCredentials;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ServerRepository {


    private ServerCredentials ownCredentials;
    private Set<ServerCredentials> knownServers;
    private Map<ClientAddressCredentials, Set<Message>> clientToMessages;

    public ServerRepository(ServerCredentials ownCredentials, Set<ServerCredentials> knownServers,
                            Set<ClientAddressCredentials> clients) {
        this.ownCredentials = ownCredentials;
        this.knownServers = knownServers;
        this.clientToMessages = clients.stream()
                .collect(Collectors.toMap(client -> client, client -> new HashSet<>()));
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

    public ClientAddressCredentials getClientByAliasAndPassword(String alias,
                                                                String password) throws ClientNotFoundException {
        return clientToMessages.keySet()
                .stream()
                .filter(client -> client.getAlias().equals(alias) && client.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException(alias + " " + password));
    }


    public ServerCredentials getServerByDomain(String domain) throws DomainNotFoundException {
        return knownServers.stream()
                .filter(server -> server.domain().equals(domain))
                .findFirst()
                .orElseThrow(() -> new DomainNotFoundException(domain));
    }


    public void updateTokenClientCredentials(String alias, String token, LocalDate expiresDate) {
        ClientAddressCredentials outdatedClient =
                clientToMessages.keySet().stream().filter(client -> client.getAlias().equals(alias)).findFirst().get();

        outdatedClient.setToken(token);
        outdatedClient.setExpiresTokenIn(expiresDate);
    }

    public String getOwnDomain() {
        return ownCredentials.domain();
    }

}
