package org.example.entities;

import lombok.Getter;

@Getter
public class ClientCredentials {


    private final String username;
    private final String token;
    public ClientCredentials(final String username, final String token) {
        this.username = username;
        this.token = token;
    }
}
