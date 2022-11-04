package org.example.entities;

import java.time.LocalDate;

public record ReceiveClientMessageRequestPackage(ClientAddress address, LocalDate dateFrom, LocalDate dateTo) {

    public ClientAddress getClientAddress() {
        return this.address;
    }

    public LocalDate getDateFrom() {
        return this.dateFrom;
    }

    public LocalDate getDateTo() {
        return this.dateTo;
    }
}
