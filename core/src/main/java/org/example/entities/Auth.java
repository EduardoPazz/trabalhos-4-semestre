package org.example.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auth {

    public Auth(
            String aliasParam,
            String passwordParam
    )
    {
        alias = aliasParam;
        password = passwordParam;
    }

    private String alias;
    private String password;

    @Override
    public String toString()
    {
        return "auth;" + getAlias() + ";" + getPassword() + "\n";
    }
}