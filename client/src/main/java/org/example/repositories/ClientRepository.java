package org.example.repositories;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.ClientAddress;
import org.example.entities.Message;
import org.example.entities.ServerCredentials;
import org.example.exceptions.DomainNotFoundException;

import java.util.List;
import java.util.Set;

public class ClientRepository {

    public ClientRepository() {
        serverCredentialsSet.add(new ServerCredentials("127.0.0.1", 666, "usp.br"));
    }

    @Getter
    private Set<Message> receivedMessages;
    private Set<ServerCredentials> serverCredentialsSet;


    @Getter
    @Setter
    private ClientAddress clientAddress;

    public ServerCredentials getConnectedServer(String domain) throws DomainNotFoundException {

        var serverCredentials = serverCredentialsSet.stream().filter(
                x -> x.domain().equals(domain)
        ).findFirst().orElseThrow(() -> new DomainNotFoundException("Domínio não encontrado"));

        return serverCredentials;
    }

    public void saveMessages(List<Message> messages) {
        receivedMessages.addAll(messages);
    }

}
