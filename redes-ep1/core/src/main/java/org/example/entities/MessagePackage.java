package org.example.entities;

import org.example.enums.HostTypeEnum;

import java.io.Serializable;

public record MessagePackage(HostTypeEnum hostType, String token, Message message) implements Serializable {
}
