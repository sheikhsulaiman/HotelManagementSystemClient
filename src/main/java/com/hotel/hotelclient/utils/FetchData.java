package com.hotel.hotelclient.utils;

import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.database.DButils;

public class FetchData {
    public static void fetchAllDataFromServer(){
        DButils.clearAllBookings();
        DButils.clearAllCalendar();
        Media bookings = new Media(Request.fetchBookings(Integer.toString(Log.userId)));
        String rawBookingData = (bookings.getReceivedData());
        if(!(rawBookingData.isBlank()||rawBookingData.isEmpty())) {
            String[] listBooking = rawBookingData.split(":");
            //System.out.println(Arrays.toString(listBooking));
            for (String data : listBooking) {
                //System.out.println(Arrays.toString(data.split("~)));
                DButils.updateBookingTable(data.split("~"));
            }
        }
        Media calendar = new Media(Request.fetchCalendar());
        String rawCalendarData = (calendar.getReceivedData());
        if(!(rawCalendarData.isBlank()||rawCalendarData.isEmpty())) {
            String[] listCalendar = rawCalendarData.split(":");
            //System.out.println(Arrays.toString(listBooking));
            for (String data : listCalendar) {
                //System.out.println(Arrays.toString(data.split("~)));
                DButils.updateCalendarTable(data.split("~"));
            }
        }
    }
}
