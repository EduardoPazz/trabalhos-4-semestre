package org.example.entities;

import java.time.LocalDate;

public record MessageListRequisition(String alias, LocalDate dateFrom, LocalDate dateTo) {
}
