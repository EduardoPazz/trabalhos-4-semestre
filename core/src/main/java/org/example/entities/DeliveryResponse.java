package org.example.entities;

import lombok.Getter;
import org.example.enums.DeliveryStatus;

import java.time.LocalDate;

@Getter
public class DeliveryResponse {
    private DeliveryStatus status;
    private LocalDate date;

    public DeliveryResponse(DeliveryStatus status) {
        date = LocalDate.now();
        this.status = status;
    }

}
