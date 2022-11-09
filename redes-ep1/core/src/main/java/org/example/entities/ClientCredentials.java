package org.example.entities;

import java.io.Serializable;

public record ClientCredentials(String username, String token) implements Serializable {

}
