package org.example.entities;

import lombok.Getter;
import org.example.enums.RequestTypeEnum;

public class MessagePackage
{
    public MessagePackage(
            String tokenParam,
            Message messageParam
    )
    {
        type = RequestTypeEnum.MESSAGE;
        token = tokenParam;
        message = messageParam;
    }



    @Getter
    private RequestTypeEnum type;

    @Getter
    private String token;

    @Getter
    private Message message;

}
