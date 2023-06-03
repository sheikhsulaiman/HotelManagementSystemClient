package com.hotel.hotelclient.communication;

public class Request {
    public static String logIn(String userId, String password){
        return ("logInRequest"+","+userId+","+password);
    }

    public static String fetchBookings(String userId){
        return ("getBookingsOf"+","+userId);
    }
}
