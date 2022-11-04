package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import org.example.enums.DeliveryStatus;

public class ReceiveClientMessageResponsePackage {

    public ReceiveClientMessageResponsePackage(
            ClientAddress clientAddressParam,
            LocalDate dateFromParam,
            LocalDate dateToParam,
            List<Message> messagesParam
    )
    {
        clientAddress = clientAddressParam;
        dateFrom = dateFromParam;
        dateTo = dateToParam;
        messages = messagesParam;
    }


    @Getter
    private LocalDate dateFrom;

    @Getter
    private LocalDate dateTo;

    @Setter
    private ClientAddress clientAddress;

    @Getter
    private List<Message> messages;
    
}
