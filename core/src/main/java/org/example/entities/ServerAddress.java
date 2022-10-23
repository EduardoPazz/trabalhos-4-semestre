package org.example.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerAddress {
    private String address;
    private int port;
    private String domain;
}
