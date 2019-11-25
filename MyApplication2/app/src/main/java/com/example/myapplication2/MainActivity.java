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

import java.net.DatagramPacket;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {

    EditText mTextUsername;    // this is the space were the user write his user name or student number
    EditText mTextpassword;   // password or code
    Button mButtonLogin;      // button that takes the user to the other page to book if the login information were right


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // byte[] sendData= new byte [1024];

      //  DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length); //udp packet

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        mTextUsername = (EditText) findViewById(R.id.edittext_username);  // adding the id to the text space so we can use it in our methods
        mTextpassword = (EditText) findViewById(R.id.edittext_password);// adding the id to the text space so we can use it in our methods
        mButtonLogin = (Button)findViewById(R.id.button_login);   // adding the id to the button so we can use it in our methods
       // mButtonLogin.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View view) {
           //     String user = mTextUsername.getText().toString().trim();       // taking user input and making it to string and storing it
             //   String pwd = mTextpassword.getText().toString().trim();
                                                                                 // this shloud be as UDP message
               // Boolean res = db.checkUser(user, pwd);                          //checking it with the database if the user name and code are right
               // if(res == true)
              //  {
                 //   Intent HomePage = new Intent(MainActivity.this,book.class); // if the log in information is right we proceed to the booking page
                 //   startActivity(HomePage);
               // }
               // else
               // {
                    //Toast.makeText(MainActivity.this,"Login Error",Toast.LENGTH_SHORT).show(); // if not give the user an error msg
              //  }
         //   }
     // }
      // );






              mButtonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent LoginIntent = new Intent(MainActivity.this,book.class);
                    startActivity(LoginIntent);
                }
            });
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

