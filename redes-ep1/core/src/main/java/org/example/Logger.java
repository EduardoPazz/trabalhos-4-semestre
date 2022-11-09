package org.example;

public record Logger(String prefix) {
    public void log(final String message) {
        System.out.println(prefix + " | " + message);
    }
}
