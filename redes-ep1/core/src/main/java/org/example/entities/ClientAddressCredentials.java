package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientAddressCredentials {
    private String address;
    private int port;
    private String alias;
    private String token;
    private LocalDateTime expiresTokenIn;
    private String password;
    public ClientAddressCredentials(final String addressParam, final int portParam, final String aliasParam,
            final String passwordParam) {
        address = addressParam;
        port = portParam;
        alias = aliasParam;
        password = passwordParam;
    }
}
