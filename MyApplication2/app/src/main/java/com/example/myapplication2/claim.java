package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class claim extends AppCompatActivity {


    EditText mTextlicencesnumber;
    Button mButtonsubmitclaim2;

    private final static String CLAIM_COMMAND = "CLA";
    private final static String COMMAND_SPLIT_REGEX = ":";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);


            mTextlicencesnumber = (EditText) findViewById(R.id.edittext_platenumber);
        mButtonsubmitclaim2 = (Button) findViewById(R.id.button_submitclaim2);


        mButtonsubmitclaim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String plateNumber = mTextlicencesnumber.getText().toString().trim();
                if (plateNumber.equals("")) {
                    Toast.makeText(claim.this, "Invalid Licence Plate", Toast.LENGTH_SHORT).show(); // if not give the user an error msg

                } else if (verifyClaim(plateNumber)) {
                    Intent LoginIntent = new Intent(claim.this, finalpage.class);
                    startActivity(LoginIntent);
                } else {
                    Toast.makeText(claim.this, "Invalid Licence Plate", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }


            }
        });

    }

    public boolean verifyClaim(String license) {
        DatagramSocket sendSocket=null,socket=null;
        InetAddress local=null;
        try{
                socket = new DatagramSocket(3005);
                socket.setSoTimeout(1000);
                sendSocket = new DatagramSocket();
                local = InetAddress.getByName("192.168.0.181");

            DatagramPacket claimAck = new DatagramPacket(new byte[100], 100);

            String dataToSend = CLAIM_COMMAND+ COMMAND_SPLIT_REGEX + license;
            DatagramPacket claimPacket = new DatagramPacket(dataToSend.getBytes(), dataToSend.getBytes().length, local, 2000);
            sendSocket.send(claimPacket);

            socket.receive(claimAck);
            System.out.println(new String(claimAck.getData()).trim());
            return (new String(claimAck.getData()).trim().equals(CLAIM_COMMAND+"ACK"));

        } catch (SocketTimeoutException ex) {
            Toast.makeText(claim.this, "Socket bad pls help", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
        } catch (IOException e) {
            Toast.makeText(claim.this, "IO Ex", Toast.LENGTH_SHORT).show(); // if not give the user an error msg

        }finally{
            socket.close();
            sendSocket.close();
        }

        return false;
    }

}
