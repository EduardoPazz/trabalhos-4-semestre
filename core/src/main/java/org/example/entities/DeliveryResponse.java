package org.example.entities;

import lombok.Getter;
import org.example.enums.DeliveryStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class DeliveryResponse implements Serializable {
    private final DeliveryStatus status;
    private final LocalDateTime date;

    public DeliveryResponse(final DeliveryStatus status) {
        date = LocalDateTime.now();
        this.status = status;
    }
}
