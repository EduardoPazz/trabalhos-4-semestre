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


/*
 Repositório do cliente:
 Aqui ficam armazenadas as credenciais do cliente e do servidor, bem como as mensagens recebidas.
 */
public class ClientRepository {

    private final Set<Message> receivedMessages = new HashSet<>();
    @Getter
    private final ServerCredentials serverCredentials;
    @Getter
    @Setter
    private ClientCredentials clientCredentials;
    public ClientRepository(final ServerCredentials serverCredentials) {
        this.serverCredentials = serverCredentials;
    }
    public List<Message> getReceivedMessages() {
        final List<Message> receivedMessagesArr = new ArrayList<>(receivedMessages);
        receivedMessagesArr.sort(Message::compareTo);
        return receivedMessagesArr;
    }
    public void storeMessages(final Collection<Message> messages) {
        receivedMessages.addAll(messages);
    }
}
