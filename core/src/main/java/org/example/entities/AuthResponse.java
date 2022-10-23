package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class AuthResponse {

    public AuthResponse(
            String typeParam,
            String authStatusParam,
            String tokenParam,
            LocalDate expiresInParam
    )
    {
        type = typeParam;
        authStatus = authStatusParam;
        expiresIn = expiresInParam;
        token = tokenParam;
    }

    private String type;
    private String authStatus;
    private String token;
    private LocalDate expiresIn;

}
