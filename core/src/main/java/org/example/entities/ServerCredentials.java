package org.example.entities;

public record ServerCredentials(String address, int port, String domain) {
    @Override
    public String toString() {
        return domain + " " + address + ":" + port;
    }
}
