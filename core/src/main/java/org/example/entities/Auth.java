package org.example.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auth {

    public Auth(
            String alias,
            String password
    )
    {
        Alias = alias;
        Password = password;
    }

    public String Alias;
    public String Password;

    @Override
    public String toString()
    {
        return "auth;" + getAlias() + ";" + getPassword();
    }
}