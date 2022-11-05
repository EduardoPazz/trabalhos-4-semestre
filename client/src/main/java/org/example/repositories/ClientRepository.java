package org.example.repositories;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.ClientCredentials;
import org.example.entities.Message;
import org.example.entities.ServerCredentials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientRepository {

    private final Set<Message> receivedMessages = new HashSet<>();

    public List<Message> getReceivedMessages() {
        List<Message> receivedMessagesArr = new ArrayList<>(receivedMessages);
        receivedMessagesArr.sort(Message::compareTo);
        return receivedMessagesArr;
    }

    @Getter
    private final ServerCredentials serverCredentials;
    @Getter
    @Setter
    private ClientCredentials clientCredentials;


    public ClientRepository(final ServerCredentials serverCredentials) {
        this.serverCredentials = serverCredentials;
    }

    public void storeMessages(final Collection<Message> messages) {
        receivedMessages.addAll(messages);
    }
}
