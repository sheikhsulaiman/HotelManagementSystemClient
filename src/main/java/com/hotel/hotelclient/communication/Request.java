package com.hotel.hotelclient.communication;

public class Request {
    public static String logIn(String userId, String password){
        return ("logInRequest"+","+userId+","+password);
    }
    public static String newUserRegistration(String firstName,String lastName, String password,String phoneNumber,String gender,String email,String address){
        return ("newUserRegistration"+","+firstName+","+lastName+","+password+","+phoneNumber+","+gender+","+email+","+address);
    }

    public static String fetchBookings(String userId){
        return ("getBookingsOf"+","+userId);
    }
    public static String fetchRooms(){
        return ("getRooms");
    }
}
