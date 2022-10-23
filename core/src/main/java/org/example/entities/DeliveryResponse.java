package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.DeliveryStatusEnum;
import org.example.enums.RequestTypeEnum;

import java.time.LocalDate;

@Getter
@Setter
public class DeliveryResponse
{
    public DeliveryResponse(){

    }

    public DeliveryResponse(
            DeliveryStatusEnum statusParam
    )
    {
        type = RequestTypeEnum.DELIVERY;
        date = LocalDate.now();
        status = statusParam;
    }

    private DeliveryStatusEnum status;
    private LocalDate date;
    private RequestTypeEnum type;

}
