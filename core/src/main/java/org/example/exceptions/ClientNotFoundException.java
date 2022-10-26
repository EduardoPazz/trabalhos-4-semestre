package org.example.exceptions;

public class ClientNotFoundException extends Exception
{
    public ClientNotFoundException(String msg)
    {
        super(msg);
    }
}
