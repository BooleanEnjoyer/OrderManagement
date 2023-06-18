package com.example.OrderManagement.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PLACED("PLACED"),
    ASSEMBLED("ASSEMBLED"),
    ON_THE_WAY("ON_THE_WAY"),
    IN_DESTINATION("IN_DESTINATION"),
    COMPLETED("COMPLETED");
    private final String label;
}
