package org.example.entities;

import org.example.enums.HostTypeEnum;

public record MessagePackage(HostTypeEnum hostType, String token, Message message) {
}
