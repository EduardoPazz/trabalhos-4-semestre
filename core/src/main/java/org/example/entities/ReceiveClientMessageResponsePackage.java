package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiveClientMessageResponsePackage implements Serializable {

    @Getter
    private final LocalDateTime dateFrom;
    @Getter
    private final LocalDateTime dateTo;
    @Getter
    private final List<Message> messages;
    @Setter
    private ClientCredentials clientCredentials;

    public ReceiveClientMessageResponsePackage(final ClientCredentials clientCredentialsParam, final LocalDateTime dateFromParam,
            final LocalDateTime dateToParam, final List<Message> messagesParam) {
        clientCredentials = clientCredentialsParam;
        dateFrom = dateFromParam;
        dateTo = dateToParam;
        messages = messagesParam;
    }

}
