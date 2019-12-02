package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    EditText mTextUsername;    // this is the space were the user write his user name or student number
    EditText mTextpassword;   // password or code
    Button mButtonLogin;      // button that takes the user to the other page to book if the login information were right
    InetAddress local;
    private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
    private final static String LOGIN_COMMAND = "LOG";
    private final static String CLAIM_COMMAND = "CLA";
    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appMainThread thread = new appMainThread();
        thread.run();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view
                    ) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        );

        mTextUsername = (EditText) findViewById(R.id.edittext_username);  // adding the id to the text space so we can use it in our methods
        mTextpassword = (EditText) findViewById(R.id.edittext_password);// adding the id to the text space so we can use it in our methods
        mButtonLogin = (Button) findViewById(R.id.button_login);   // adding the id to the button so we can use it in our methods

        mButtonLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view
                    ) {
                        if (verifyLogin(mTextUsername.getText().toString().trim(), mTextpassword.getText().toString().trim()) == true) {
                            Intent LoginIntent = new Intent(MainActivity.this, book.class);
                            startActivity(LoginIntent);
                        } else {
                            Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                        }
                    }
                }
        );
    }

    class appMainThread implements Runnable {

        private final static String COMMAND_SPLIT_REGEX = ":";
        private final static String DATA_SPLIT_REGEX = ",";
        private final static String LED_COMMAND = "LED";
        private final static String NOTHING_TO_REPORT = "NA";
        private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
        private final static String LOGIN_COMMAND = "LOG";
        private final static String CLAIM_COMMAND = "CLA";

        private final static int PACKETSIZE = 100;
        DatagramSocket socket, sendSocket;
        DatagramPacket packet;
        InetAddress ServerAddress, local;
        Stack<String> appQueue;

        @Override
        public void run() {
            //Test
            setup();

            while (true) {
                try {
                    receive();

                } catch (IOException e) {

                }
            }

        }

        public void setup() {
            try {
                appQueue = new Stack<>();
                ServerAddress = InetAddress.getByName("localhost");
                local = InetAddress.getByName("localhost");
                socket = new DatagramSocket(2000);
                socket.setSoTimeout(10000);
                sendSocket = new DatagramSocket();

            } catch (SocketException | UnknownHostException e) {
                System.out.println("socket bad");
            }

        }

        public void receive() throws IOException {

            DatagramPacket heartbeat = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
            socket.receive(heartbeat);
            if (new String(heartbeat.getData()).trim().equals("HBAPP")) {
                if (appQueue.isEmpty()) {
                    DatagramPacket heartbeatAck = new DatagramPacket(NOTHING_TO_REPORT.getBytes(), NOTHING_TO_REPORT.getBytes().length, ServerAddress, 1000);
                    sendSocket.send(heartbeatAck);
                } else {
                    String heartbeatRespond = appQueue.peek();
                    DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);
                    sendSocket.send(heartbeatAck);

                    if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(LOGIN_COMMAND)) {
                        DatagramPacket login = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(login);
                        sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));

                    } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(OCCUPANCY_UPDATE_COMMAND)) {
                        DatagramPacket lotOccupancyPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(lotOccupancyPacket);
                        sendSocket.send(new DatagramPacket((OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes(), (OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));

                    } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(CLAIM_COMMAND)) {
                        DatagramPacket ClaimPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(ClaimPacket);
                        sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "ACK").getBytes(), (CLAIM_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));

                    }
                }
            } else if (new String(heartbeat.getData()).trim().equals(LOGIN_COMMAND)) {
                String loginRequest[] = new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX);
                appQueue.add(loginRequest[1]);
                System.out.println("Succesfully added to queue");
                sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").length(), local, 3000));
            }

        }

    }

    public boolean verifyLogin(String username, String password) {
        try {
            DatagramSocket socket = new DatagramSocket(3000);
            socket.setSoTimeout(10000);
            DatagramSocket sendSocket = new DatagramSocket();

            local = InetAddress.getByName("localhost");
            String dataToSend = LOGIN_COMMAND + COMMAND_SPLIT_REGEX + username + DATA_SPLIT_REGEX + password;
            DatagramPacket loginPacket = new DatagramPacket(dataToSend.getBytes(), dataToSend.getBytes().length, local, 2000);
            DatagramPacket loginAck = new DatagramPacket(new byte[100], 100);

            sendSocket.send(loginPacket);
            socket.receive(loginAck);

        } catch (SocketTimeoutException ex) {
            System.err.println("Bad things happening");
        } catch (IOException e) {
            System.err.println(e + "heartbeat app failed");
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
