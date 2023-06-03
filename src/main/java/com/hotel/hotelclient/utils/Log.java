package com.hotel.hotelclient.utils;

public class Log {
    private static boolean logInStatus;
    public static int userId;
    private static int phone;
    private static String firstname,lastname,gender,email,address;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Log.userId = userId;
    }

    public static int getPhone() {
        return phone;
    }

    public static void setPhone(int phone) {
        Log.phone = phone;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        Log.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        Log.lastname = lastname;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Log.gender = gender;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Log.email = email;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Log.address = address;
    }

    public static boolean isLogInStatus() {
        return logInStatus;
    }

    public static void setLogInStatus(boolean logInStatus) {
        Log.logInStatus = logInStatus;
    }
}
