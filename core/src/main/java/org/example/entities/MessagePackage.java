package org.example.entities;

import lombok.Getter;
import org.example.enums.HostTypeEnum;
import org.example.enums.RequestTypeEnum;

public class MessagePackage
{
    public MessagePackage(
            String tokenParam,
            Message messageParam,
            HostTypeEnum hostTypeEnumParam
    )
    {
        hostTypeEnum = hostTypeEnumParam;
        type = RequestTypeEnum.MESSAGE;
        token = tokenParam;
        message = messageParam;
    }

    @Getter
    private HostTypeEnum hostTypeEnum;

    @Getter
    private RequestTypeEnum type;

    @Getter
    private String token;

    @Getter
    private Message message;

}
