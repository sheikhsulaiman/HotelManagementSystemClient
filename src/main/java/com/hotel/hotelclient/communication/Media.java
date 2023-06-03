package com.hotel.hotelclient.communication;

public class Media {
    public String send = null;
    private static Client client;
    public Media(String send){
        this.send = send;
        client = new Client(send);
    }

    public String getReceivedData(){
        return client.getReceivedString();
    }
}
