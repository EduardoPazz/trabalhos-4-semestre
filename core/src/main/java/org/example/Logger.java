package org.example;

public record Logger(String prefix) {
    public void log(String message) {
        System.out.println(prefix + " | " + message);
    }
}
