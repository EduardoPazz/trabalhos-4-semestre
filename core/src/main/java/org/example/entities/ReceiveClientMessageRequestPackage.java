package org.example.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ReceiveClientMessageRequestPackage(ClientCredentials address, LocalDateTime dateFrom,
                                                 LocalDateTime dateTo) implements Serializable {

    public ClientCredentials getClientAddress() {
        return this.address;
    }

    public LocalDateTime getDateFrom() {
        return this.dateFrom;
    }

    public LocalDateTime getDateTo() {
        return this.dateTo;
    }
}
