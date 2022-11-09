package org.example.exceptions;

public class ClientNotFoundException extends Exception {
    public ClientNotFoundException(final String msg) {
        super(msg);
    }
}
