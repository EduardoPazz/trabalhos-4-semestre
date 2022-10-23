package org.example.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientAddress {

    public ClientAddress(){
    }

    public ClientAddress(
            String aliasParam,
            String domainParam,
            String tokenParam
    )
    {
        alias = aliasParam;
        domain = domainParam;
        token = tokenParam;
    }

    private String address;
    private String alias;
    private String domain;
    private String token;
}
