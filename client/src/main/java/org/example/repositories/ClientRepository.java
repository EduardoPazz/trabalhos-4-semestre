package org.example.repositories;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.ClientAddress;
import org.example.entities.Message;
import org.example.entities.ServerAddress;

import java.util.List;

public class ClientRepository {

    @Getter
    private List<Message> receivedMessages;

    @Getter
    @Setter
    private ClientAddress clientAddress;

    public ServerAddress GetConectedServer(){
        ServerAddress serverAddress = new ServerAddress();
        serverAddress.setAddress("127.0.0.1");
        serverAddress.setPort(666);
        serverAddress.setDomain("usp.br");
        return serverAddress;
    }


    public void AddReceivedMessages(List<Message> messages)
    {
        receivedMessages.addAll(messages);
    }

}
