package org.example.entities;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Auth implements Serializable {

    private String alias;
    private String password;

    public Auth(final String aliasParam, final String passwordParam) {
        alias = aliasParam;
        password = passwordParam;
    }

    @Override
    public String toString() {
        return "auth;" + getAlias() + ";" + getPassword() + "\n";
    }
}
