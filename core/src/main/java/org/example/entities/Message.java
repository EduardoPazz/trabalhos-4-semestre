package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.DeliveryStatus;

import java.time.LocalDate;
import java.util.Comparator;

@Getter
@Setter
public class Message implements Comparable<Message> {

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

    @Override
    public int compareTo(Message m) {
        if(this.getSendDate().isBefore(m.getSendDate())) return -1;
        if(this.getSendDate().isAfter(m.getSendDate())) return 1;
        return 0;
    }
}
