package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
public class claim extends AppCompatActivity {


    EditText mTextlicencesnumber;
    Button mButtonsubmitclaim2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);


        mTextlicencesnumber  = (EditText) findViewById(R.id.edittext_platenumber);
        mButtonsubmitclaim2 = (Button)findViewById(R.id.button_submitclaim2);


      // save the plate that the user inputed and send it to database to get checked

        // get an avilable spot  from database and output it to the user




         mButtonsubmitclaim2.setOnClickListener(new View.OnClickListener() {
                      @Override
                       public void onClick(View view) {
                           Intent LoginIntent = new Intent(claim.this,finalpage.class);

                           startActivity(LoginIntent);
                      }
                    });





    }
}
