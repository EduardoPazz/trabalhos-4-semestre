package org.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

public record ReceiveClientMessageRequestPackage(ClientCredentials address, LocalDate dateFrom, LocalDate dateTo) implements Serializable {

    public ClientCredentials getClientAddress() {
        return this.address;
    }

    public LocalDate getDateFrom() {
        return this.dateFrom;
    }

    public LocalDate getDateTo() {
        return this.dateTo;
    }
}
