package org.example.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.DeliveryStatus;
import org.example.exceptions.DomainNotFoundException;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class Message implements Comparable<Message>, Serializable {

    private LocalDateTime sendDate;
    private String toAlias;
    private String toDomain;
    private String fromAlias;
    private String fromDomain;
    private String subject;
    private String body;
    private DeliveryStatus deliveryStatus;

    public Message(final String emailAddressParam, final String fromAliasParam, final String fromDomainParam, final String subjectParam,
            final String bodyParam) throws DomainNotFoundException {
        final String[] emailAddressArr = emailAddressParam.split("@");

        if(emailAddressArr.length != 2) {
            throw new DomainNotFoundException("Endereço de email inválido!");
        }

        toAlias = emailAddressArr[0];
        toDomain = emailAddressArr[1];

        fromDomain = fromDomainParam;
        fromAlias = fromAliasParam;
        subject = subjectParam;
        body = bodyParam;

        sendDate = LocalDateTime.now();
    }

    @Override
    public int compareTo(final Message m) {
        if (this.getSendDate().isBefore(m.getSendDate())) return -1;
        if (this.getSendDate().isAfter(m.getSendDate())) return 1;
        return 0;
    }
}
