package com.hotel.hotelclient.utils;

public class Value {
    private static Integer integerValue;

    public static Integer getIntegerValue() {
        return integerValue;
    }

    public static void setIntegerValue(Integer integerValue) {
        Value.integerValue = integerValue;
    }
}
