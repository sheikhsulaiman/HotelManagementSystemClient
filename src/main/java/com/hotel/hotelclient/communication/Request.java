package com.hotel.hotelclient.communication;

public class Request {
    public static String logIn(String userId, String password){
        return ("logInRequest"+"~"+userId+"~"+password);
    }
    public static String newUserRegistration(String firstName,String lastName, String password,String phoneNumber,String gender,String email,String address){
        return ("newUserRegistration"+"~"+firstName+"~"+lastName+"~"+password+"~"+phoneNumber+"~"+gender+"~"+email+"~"+address);
    }
    public static String newBooking(String roomNo,String userId,String checkInDate,String checkOutDate,String payType,String roomService,String poolAccess,String carParking){
        return ("newBooking"+"~"+roomNo+"~"+userId+"~"+checkInDate+"~"+checkOutDate+"~"+payType+"~"+"Unpaid"+"~"+roomService+"~"+poolAccess+"~"+carParking);
    }

    public static String fetchCalendar(){
        return ("getCalendar");
    }

    public static String fetchBookings(String userId){
        return ("getBookingsOf"+"~"+userId);
    }
    public static String fetchRooms(){
        return ("getRooms");
    }
    public static String fetchInvoiceId(String bookingId){
        return ("getInvoice"+"~"+bookingId);
    }

    public static String createNewInvoice(String bookingId,String cost,String status){
        return ("createNewInvoice"+"~"+bookingId+"~"+cost+"~"+status);
    }
    public static String updateProfile(String firstname,String lastname,String phone,String gender,String email,String address,String userId,String password){
        return ("updateProfile"+"~"+firstname+"~"+lastname+"~"+phone+"~"+gender+"~"+email+"~"+address+"~"+userId+"~"+password);
    }

    public static String getBookingDetailsForClient(){
        return ("getBookingDetailsForClient");
    }
}
