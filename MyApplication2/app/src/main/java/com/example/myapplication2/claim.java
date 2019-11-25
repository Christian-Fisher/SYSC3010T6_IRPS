package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class claim extends AppCompatActivity {

    EditText mTextlicencesnumber;
    Button mButtonsubmitclaim2;

    InetAddress local;
    private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
    private final static String LOGIN_COMMAND = "LOG";
    private final static String CLAIM_COMMAND = "CLA";
    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);

        mTextlicencesnumber = (EditText) findViewById(R.id.edittext_platenumber);
        mButtonsubmitclaim2 = (Button) findViewById(R.id.button_submitclaim2);

        // save the plate that the user inputed and send it to database to get checked
        // get an avilable spot  from database and output it to the user
        mButtonsubmitclaim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent LoginIntent = new Intent(claim.this, finalpage.class);

                startActivity(LoginIntent);
            }
        });

    }

    public boolean verifyClaim(String licence) {
        try {
            DatagramSocket socket = new DatagramSocket(3000);
            socket.setSoTimeout(10000);
            DatagramSocket sendSocket = new DatagramSocket();

            local = InetAddress.getByName("localhost");
            String dataToSend = CLAIM_COMMAND + COMMAND_SPLIT_REGEX + licence;
            DatagramPacket claimPacket = new DatagramPacket(dataToSend.getBytes(), dataToSend.getBytes().length, local, 2000);
            DatagramPacket claimAck = new DatagramPacket(new byte[100], 100);

            sendSocket.send(claimPacket);
            socket.receive(claimAck);

        } catch (SocketTimeoutException ex) {
            System.err.println("Bad things happening");
        } catch (IOException e) {
            System.err.println(e + "heartbeat app failed");
        }
        return false;
    }

}
