package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.DeliveryStatus;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class Message implements Comparable<Message>, Serializable {

    private LocalDate sendDate;
    private String toAlias;
    private String toDomain;
    private String fromAlias;
    private String fromDomain;
    private String subject;
    private String body;
    private DeliveryStatus deliveryStatus;

    public Message(final String emailAddressParam, final String fromAliasParam, final String subjectParam,
            final String bodyParam) {
        final String[] emailAddressArr = emailAddressParam.split("@");

        toAlias = emailAddressArr[0];
        toDomain = emailAddressArr[1];

        fromAlias = fromAliasParam;
        subject = subjectParam;
        body = bodyParam;

        sendDate = LocalDate.now();
    }

    @Override
    public int compareTo(final Message m) {
        if (this.getSendDate().isBefore(m.getSendDate())) return -1;
        if (this.getSendDate().isAfter(m.getSendDate())) return 1;
        return 0;
    }
}
