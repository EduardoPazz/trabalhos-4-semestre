package org.example.enums;

import java.io.Serializable;

public enum DeliveryStatus implements Serializable {
    SUCCESS, UNKNOWN_DOMAIN, UNKNOWN_CLIENT, NOT_AUTHENTICATED
}
