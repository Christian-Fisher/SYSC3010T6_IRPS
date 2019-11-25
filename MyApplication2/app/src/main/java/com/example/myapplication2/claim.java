package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class claim extends AppCompatActivity {


    EditText mTextlicencesnumber;
    Button mButtonsubmitclaim2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);


        mTextlicencesnumber  = (EditText) findViewById(R.id.edittext_platenumber);
        mButtonsubmitclaim2 = (Button)findViewById(R.id.button_submitclaim2);







    }
}
