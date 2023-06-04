package com.hotel.hotelclient.communication;

public class Request {
    public static String logIn(String userId, String password){
        return ("logInRequest"+","+userId+","+password);
    }
    public static String newUserRegistration(String firstName,String lastName, String password,String phoneNumber,String gender,String email,String address){
        return ("newUserRegistration"+","+firstName+","+lastName+","+password+","+phoneNumber+","+gender+","+email+","+address);
    }
    public static String newBooking(String roomNo,String userId,String checkInDate,String checkOutDate,String payType,String roomService,String poolAccess,String carParking){
        return ("newBooking"+","+roomNo+","+userId+","+checkInDate+","+checkOutDate+","+payType+","+"Unpaid"+","+roomService+","+poolAccess+","+carParking);
    }

    public static String fetchCalendar(){
        return ("getCalendar");
    }

    public static String fetchBookings(String userId){
        return ("getBookingsOf"+","+userId);
    }
    public static String fetchRooms(){
        return ("getRooms");
    }
}
