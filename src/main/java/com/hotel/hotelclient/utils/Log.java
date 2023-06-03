package com.hotel.hotelclient.utils;

public class Log {
    private static boolean logInStatus;

    public static boolean isLogInStatus() {
        return logInStatus;
    }

    public static void setLogInStatus(boolean logInStatus) {
        Log.logInStatus = logInStatus;
    }
}
