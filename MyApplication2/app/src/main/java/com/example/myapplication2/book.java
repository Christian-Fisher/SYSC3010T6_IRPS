package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;






public class book extends AppCompatActivity {

    TextView mTextavaiablespot;

    Button mButtonsubmitclaim;
    Button lot[] = new Button[9];
    boolean []flags = new boolean[9];



    private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";
    private final static String BOOKING_COMMAND = "BOO";
    InetAddress local;
    DatagramSocket sendSocket, socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        try {
            socket = new DatagramSocket(3001);
            socket.setSoTimeout(1000);
            sendSocket = new DatagramSocket();
            local = InetAddress.getByName("localhost");

        }catch (SocketException e) {
            System.out.println(e + "Oh boy");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        lot[0]=(Button)findViewById(R.id.A1);
        lot[1]=(Button)findViewById(R.id.A2);
        lot[2]=(Button)findViewById(R.id.A3);
        lot[3]=(Button)findViewById(R.id.B1);
        lot[4]=(Button)findViewById(R.id.B2);
        lot[5]=(Button)findViewById(R.id.B3);
        lot[6]=(Button)findViewById(R.id.C1);
        lot[7]=(Button)findViewById(R.id.C2);
        lot[8]=(Button)findViewById(R.id.C3);


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




        lot[0].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (bookSpot("1")){
                Intent finalpageIntent= new Intent(book.this,finalpage.class);
                // after booking we go to another page with telss the user's booking is successfuly  done
                startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }

           // if(avilablespots[0]= "true"){}

            //A2.setVisibility(View.VISIBLE);

            // }


        } );

        lot[1].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (bookSpot("2")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }



        } );



       // if (avilable spots [1]= "true"){

            //A2.setVisibility(View.VISIBLE);

       // }

        lot[2].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (bookSpot("3")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }

        // if (avilable spots [2]= "true"){

                    //A2.setVisibility(View.VISIBLE);

                    // }


        } );

        lot[3].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (bookSpot("4")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }
            // if (avilable spots [3]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }



        } );



        lot[4].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (bookSpot("5")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }
            // if (avilable spots [4]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }



        } );


        lot[5].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (bookSpot("6")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }
            // if (avilable spots [5]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }



        } );



        lot[6].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (bookSpot("7")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }

            // if (avilable spots [6]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }


        } );

        lot[7].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (bookSpot("8")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }

            // if (avilable spots [7]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }


        } );


        lot[8].setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (bookSpot("9")){
                    Intent finalpageIntent= new Intent(book.this,finalpage.class);
                    // after booking we go to another page with telss the user's booking is successfuly  done
                    startActivity(finalpageIntent);}
                else{
                    Toast.makeText(book.this, "Booking Failed", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
                }
            }


            // if (avilable spots [8]= "true"){

            //A2.setVisibility(View.VISIBLE);

            // }

        } );
    }
    protected void onResume() { //This method is run on the start of the app, and when the app is returned to. This allows the occupancy to update whenever the user enters the page
        super.onResume();
        getOccupancy();
    }
/*
books a given spot for a user
 */
    public boolean bookSpot(String spot){


        try {
            DatagramPacket bookAck = new DatagramPacket(new byte[100], 100);

            String dataToSend = BOOKING_COMMAND+ COMMAND_SPLIT_REGEX + spot;
            DatagramPacket bookPacket = new DatagramPacket(dataToSend.getBytes(), dataToSend.getBytes().length, local, 2000);

            sendSocket.send(bookPacket);//Sends the booking request. The UDPThread will handle adding the username to the message

            socket.receive(bookAck);//receive response
            return (new String(bookAck.getData()).trim().equals(BOOKING_COMMAND+"ACK"));

        } catch (SocketTimeoutException ex) {
            Toast.makeText(book.this, "Connection Error", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
        } catch (IOException e) {
            Toast.makeText(book.this, e.getMessage(), Toast.LENGTH_SHORT).show(); // if not give the user an error msg
        }
        return false;
    }
/*
Retrieves the occupancy of the lot, and updates the buttons accordingly
 */
    public void getOccupancy(){



            try {
                DatagramPacket occAck = new DatagramPacket(new byte[100], 100);

                String dataToSend = OCCUPANCY_UPDATE_COMMAND+ COMMAND_SPLIT_REGEX ;
                DatagramPacket occPacket = new DatagramPacket(dataToSend.getBytes(), dataToSend.getBytes().length, local, 2000);
                sendSocket.send(occPacket); //Send request
                socket.receive(occAck);//receive response
                String receivedData[] = new String(occAck.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].split(DATA_SPLIT_REGEX);//Format the data into an array of strings only containing true or false
                sendSocket.send(new DatagramPacket((OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes(), (OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes().length, local, 2000));//send acknowledgement
                for(int i =0; i<flags.length; i++){//Sets the flags to the new values
                    if(receivedData[i].equals("true")){
                        flags[i]=true;
                    }else{
                        flags[i]=false;
                    }
                }
                for(int x = 0; x<flags.length; x++){    //based on flags, update the UI
                    if(!flags[x]) {
                        lot[x].setVisibility(View.VISIBLE); //Enable spot x's button
                    }else{
                        lot[x].setVisibility(View.INVISIBLE);//Disable spot x's button
                    }
                }

            } catch (SocketTimeoutException ex) {
                Toast.makeText(book.this, "Connection Error", Toast.LENGTH_SHORT).show(); // if not give the user an error msg
            } catch (IOException e) {
                Toast.makeText(book.this, e.getMessage(), Toast.LENGTH_SHORT).show(); // if not give the user an error msg
            }
        }

    @Override
    public void onBackPressed(){    //Override onBackPressed, so the user cannot go back to the login screen. This is to prevent socket bugs on the main page.

    }




}
