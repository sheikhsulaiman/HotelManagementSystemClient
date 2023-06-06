package com.hotel.hotelclient.communication;

import com.hotel.hotelclient.utils.Log;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private String receive;

    public String getReceivedString() {
        return receive;
    }

    public Client(String send){
        try{
            String host = Log.getServerIp();
            Socket s = new Socket(host, 9999);

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
//        str = br.readLine();

                    dout.writeUTF(send.toString());
                    dout.flush();
                    receive = (din.readUTF().toString());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error");
            alert.setContentText("Server not found!");
            alert.show();
        }
    }
}
