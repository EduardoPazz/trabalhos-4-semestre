package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ReceiveClientMessageResponsePackage implements Serializable {

    @Getter
    private final LocalDate dateFrom;
    @Getter
    private final LocalDate dateTo;
    @Getter
    private final List<Message> messages;
    @Setter
    private ClientCredentials clientCredentials;

    public ReceiveClientMessageResponsePackage(final ClientCredentials clientCredentialsParam, final LocalDate dateFromParam,
            final LocalDate dateToParam, final List<Message> messagesParam) {
        clientCredentials = clientCredentialsParam;
        dateFrom = dateFromParam;
        dateTo = dateToParam;
        messages = messagesParam;
    }

}
