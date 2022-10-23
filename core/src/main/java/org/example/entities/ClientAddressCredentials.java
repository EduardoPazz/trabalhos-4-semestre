package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientAddressCredentials
{
    public ClientAddressCredentials()
    {}

    public ClientAddressCredentials(
            String addressParam,
            int portParam,
            String aliasParam,
            String passwordParam
    )
    {
        address = addressParam;
        port = portParam;
        alias = aliasParam;
        password = passwordParam;
    }

    private String address;
    private int port;
    private String alias;
    private String token;
    private LocalDate expiresTokenIn;
    private String password;
}
