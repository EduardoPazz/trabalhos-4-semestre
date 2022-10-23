package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.AuthStatusEnum;

import java.time.LocalDate;


@Getter
@Setter
public class AuthResponse {

    public AuthResponse(
            String typeParam,
            int authStatusParam,
            String tokenParam,
            LocalDate expiresInParam
    )
    {
        type = typeParam;
        authStatus = authStatusParam == 1 ? AuthStatusEnum.AUTHENTICATED : AuthStatusEnum.NOT_AUTHENTICATED;
        expiresIn = expiresInParam;
        token = tokenParam;
    }

    private String type;
    private AuthStatusEnum authStatus;
    private String token;
    private LocalDate expiresIn;


    @Override
    public String toString()
    {
        int authStatusInt = 1;
        if(authStatus == AuthStatusEnum.NOT_AUTHENTICATED) authStatusInt = 0;

        return type + ";" + authStatusInt + ";" + token + ";" + expiresIn.toString();
    }

}
