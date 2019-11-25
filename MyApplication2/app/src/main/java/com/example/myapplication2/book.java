package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class book extends AppCompatActivity {



    EditText mTextspotnumber;    // variables for buttons and text spaces
    Button mButtonbookspot;
    TextView mTextavaiablespot;
    Button mButtonsubmitclaim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        mButtonsubmitclaim = (Button)findViewById(R.id.button_submitclaim);
        mButtonsubmitclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent claimIntent= new Intent(book.this,claim.class);
                startActivity(claimIntent);
            }
        });

        mTextspotnumber  = (EditText) findViewById(R.id.edittext_spotnumber);  // linking the text spaces and buttons to their IDs
        mButtonbookspot = (Button)findViewById(R.id.button_bookspot);
        mTextavaiablespot = (TextView)findViewById(R.id.textview_Availablespots);
        mButtonbookspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent finalpageIntent= new Intent(book.this,finalpage.class); // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }
        });

    }



}
