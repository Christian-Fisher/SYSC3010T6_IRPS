package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Date;

import java.util.Calendar;
public class book<date> extends AppCompatActivity {



    EditText mTextspotnumber;    // variables for buttons and text spaces

    TextView mTextavaiablespot;

    Button mButtonsubmitclaim;
    Button A1;
    Button A2;
    Button A3;
    Button B1;
    Button B2;
    Button B3;
    Button C1;
    Button C2;
    Button C3;
    boolean []flags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        A1= (Button)findViewById(R.id.A1);
        A2 = (Button)findViewById(R.id.A2);
        A3= (Button)findViewById(R.id.A3);
        B1=(Button)findViewById(R.id.B1);
        B1=(Button)findViewById(R.id.B2);
        B1=(Button)findViewById(R.id.B3);
        C1=(Button)findViewById(R.id.C1);
        C2=(Button)findViewById(R.id.C2);
        C3=(Button)findViewById(R.id.C3);


        mButtonsubmitclaim = (Button)findViewById(R.id.button_submitclaim);
        mButtonsubmitclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent claimIntent= new Intent(book.this,claim.class);
                startActivity(claimIntent);
            }
        });




         // we should  have the input of the user and send it to database with the time

         //get the avilable spots


        mTextavaiablespot = (TextView)findViewById(R.id.textview_Availablespots);


        A1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }

           // if(avilablespots[0]= "true"){}

            //A2.setVisibility(View.VISIBLE);

            // }


        } );

        A2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }



        } );



       // if (avilable spots [1]= "true"){

            //A2.setVisibility(View.VISIBLE);

       // }

        A3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }

        // if (avilable spots [2]= "true"){

                    //A2.setVisibility(View.VISIBLE);

                    // }


        } );


        B1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }
            // if (avilable spots [3]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }



        } );



        B2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }
            // if (avilable spots [4]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }



        } );


        B3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }
            // if (avilable spots [5]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }



        } );



        C1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }

            // if (avilable spots [6]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }


        } );

        C2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }

            // if (avilable spots [7]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }


        } );


        C3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent finalpageIntent= new Intent(book.this,finalpage.class);

                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);
            }


            // if (avilable spots [8]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }

        } );








    }



}
