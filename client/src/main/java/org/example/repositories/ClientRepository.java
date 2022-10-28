package org.example.repositories;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.ClientAddress;
import org.example.entities.Message;
import org.example.entities.ServerCredentials;
import org.example.repositories.*;

import java.util.List;
import java.util.Set;

public class ClientRepository {

    @Getter
    private Set<Message> receivedMessages;
    
    @Getter
    @Setter
    private ClientAddress clientAddress;

    public ServerCredentials getConnectedServer() {
        return new ServerCredentials("127.0.0.1", 666, "usp.br");
    }


    public void addReceivedMessages(Set<Message> messages) {
        receivedMessages.addAll(messages);
    }


    public void storeMessages(Message message) {
        database_connection db= new database_connection();
        db.addMessage(message);
    }

}
