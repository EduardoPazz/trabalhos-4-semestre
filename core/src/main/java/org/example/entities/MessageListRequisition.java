package org.example.entities;

import java.time.LocalDateTime;

public record MessageListRequisition(String alias, LocalDateTime dateFrom, LocalDateTime dateTo) {
}
