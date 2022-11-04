package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.DeliveryStatus;

import java.time.LocalDate;

@Getter
@Setter
public class SendMessageResponse {
    private String type;
    private LocalDate UTCDate;
    private DeliveryStatus status;

    public SendMessageResponse(final String typeParam, final LocalDate responseDate, final int deliveryStatusParam) {
        type = typeParam;
        UTCDate = responseDate;
        status = getDeliveryStatusByInt(deliveryStatusParam);
    }

    private DeliveryStatus getDeliveryStatusByInt(final int value) {
        return switch (value) {
            case 0 -> DeliveryStatus.SUCCESS;
            case 1 -> DeliveryStatus.UNKNOWN_DOMAIN;
            case 2 -> DeliveryStatus.UNKNOWN_CLIENT;
            default -> DeliveryStatus.NOT_AUTHENTICATED;
        };
    }
}
