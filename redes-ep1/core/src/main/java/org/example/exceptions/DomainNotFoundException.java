package org.example.exceptions;

public class DomainNotFoundException extends Exception {
    public DomainNotFoundException(final String msg) {
        super(msg);
    }
}
