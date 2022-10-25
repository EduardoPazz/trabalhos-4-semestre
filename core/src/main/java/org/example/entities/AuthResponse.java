package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.AuthStatusEnum;
import org.example.enums.RequestTypeEnum;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
public class AuthResponse implements Serializable {

    public AuthResponse(
            AuthStatusEnum authStatusParam,
            String tokenParam,
            LocalDate expiresInParam
    )
    {
        type = RequestTypeEnum.AUTH;
        authStatus = authStatusParam;
        expiresIn = expiresInParam;
        token = tokenParam;
    }

    private RequestTypeEnum type;
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
