package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.DeliveryStatus;

import java.time.LocalDate;

@Getter
@Setter
public class Message {

    private LocalDate sendDate;
    private String toAlias;
    private String toDomain;
    private String fromAlias;
    private String fromDomain;
    private String subject;
    private String body;
    private DeliveryStatus deliveryStatus;

    public Message(String emailAddressParam, String fromAliasParam, String subjectParam, String bodyParam) {
        String[] emailAddressArr = emailAddressParam.split("@");

        toAlias = emailAddressArr[0];
        toDomain = emailAddressArr[1];

        fromAlias = fromAliasParam;
        subject = subjectParam;
        body = bodyParam;

        sendDate = LocalDate.now();
    }
}
