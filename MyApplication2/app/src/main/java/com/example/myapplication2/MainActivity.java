package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.*;

public class MainActivity extends AppCompatActivity {

    EditText mTextUsername;    // this is the space were the user write his user name or student number
    EditText mTextpassword;   // password or code
    Button mButtonLogin;      // button that takes the user to the other page to book if the login information were right
    InetAddress local;
    DatagramSocket sendSocket, socket;
    private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
    private final static String LOGIN_COMMAND = "LOG";
    private final static String CLAIM_COMMAND = "CLA";
    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        UDPThread udpThread = new UDPThread();
        try {
            socket = new DatagramSocket(3000);
            socket.setSoTimeout(1000);
            sendSocket = new DatagramSocket();
            local = InetAddress.getByName("192.168.0.181");

        }catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        udpThread.start();
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
            public void onClick(View view) {
                if (verifyLogin(mTextUsername.getText().toString().trim(), mTextpassword.getText().toString().trim())) {
                    Log.i("Main", "Good login");
                    Intent LoginIntent = new Intent(MainActivity.this, book.class);
                    startActivity(LoginIntent);
                } else {
                    Log.i("Main", "Bad login");
                    Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }
        }
        );
    }

    

    public boolean verifyLogin(String username, String password) {

        try {
            DatagramPacket loginAck = new DatagramPacket(new byte[100], 100);

            String dataToSend = LOGIN_COMMAND + COMMAND_SPLIT_REGEX + username + DATA_SPLIT_REGEX + password;
            DatagramPacket loginPacket = new DatagramPacket(dataToSend.getBytes(), dataToSend.getBytes().length, local, 2000);

            sendSocket.send(loginPacket);

            socket.receive(loginAck);
            System.out.println(new String(loginAck.getData()).trim());
            return (new String(loginAck.getData()).trim().equals("LOGACK"));

        } catch (SocketTimeoutException ex) {
            Toast.makeText(MainActivity.this, "Socket bad pls help", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "IO Bad wtf", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
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
