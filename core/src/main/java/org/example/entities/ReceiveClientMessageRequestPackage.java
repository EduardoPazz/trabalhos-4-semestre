package org.example.entities;

import java.time.LocalDate;

public record ReceiveClientMessageRequestPackage(String token, LocalDate dateFrom, LocalDate dateTo) {
}
