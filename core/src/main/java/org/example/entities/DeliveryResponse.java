package org.example.entities;

import lombok.Getter;
import org.example.enums.DeliveryStatus;

import java.time.LocalDate;

@Getter
public class DeliveryResponse {
    private final DeliveryStatus status;
    private final LocalDate date;

    public DeliveryResponse(final DeliveryStatus status) {
        date = LocalDate.now();
        this.status = status;
    }

}
