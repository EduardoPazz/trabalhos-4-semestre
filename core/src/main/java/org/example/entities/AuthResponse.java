package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.AuthStatusEnum;
import org.example.enums.RequestTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class AuthResponse implements Serializable {

    private RequestTypeEnum type;
    private AuthStatusEnum authStatus;
    private String token;
    private LocalDateTime expiresIn;

    public AuthResponse(final AuthStatusEnum authStatusParam, final String tokenParam,
            final LocalDateTime expiresInParam) {
        type = RequestTypeEnum.AUTH;
        authStatus = authStatusParam;
        expiresIn = expiresInParam;
        token = tokenParam;
    }
    @Override
    public String toString() {
        int authStatusInt = 1;
        if (authStatus == AuthStatusEnum.NOT_AUTHENTICATED) authStatusInt = 0;

        return type + ";" + authStatusInt + ";" + token + ";" + expiresIn.toString();
    }
}
