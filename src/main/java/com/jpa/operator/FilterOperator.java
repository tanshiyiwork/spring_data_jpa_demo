package com.jpa.operator;

public enum FilterOperator {
    EQUAL,
    LIKE,
    LEFTLIKE,
    RIGHTLIKE,
    GT,
    GE,
    LT,
    LE,
    BETWEEN,
    ISNULL,
    ISNOTNULL,
    NOTEQUAL,
    NOTLIKE,
    IN;

    private FilterOperator() {
    }
}
