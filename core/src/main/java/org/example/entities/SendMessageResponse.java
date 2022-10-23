package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.DeliveryStatusEnum;

import java.time.LocalDate;

@Getter
@Setter
public class SendMessageResponse
{
    public SendMessageResponse(
            String typeParam,
            LocalDate responseDate,
            int deliveryStatusParam
    )
    {
        type = typeParam;
        UTCDate = responseDate;
        status = getDeliveryStatusByInt(deliveryStatusParam);
    }

    private String type;
    private LocalDate UTCDate;
    private DeliveryStatusEnum status;



    private DeliveryStatusEnum getDeliveryStatusByInt(int value)
    {
        if(value == 0) {
            return DeliveryStatusEnum.SUCCESS;
        }
        if(value == 1) {
            return DeliveryStatusEnum.UNKNOW_DOMAIN;
        }
        if(value == 2) {
            return DeliveryStatusEnum.UNKNOW_CLIENT;
        }

        return DeliveryStatusEnum.NOT_AUTHENTICATED;
    }

}
