import java.sql.*;
import java.util.*;

import javax.sound.sampled.Port;

import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress; 
import java.net.SocketException;

import LEDController.java;
import ParkingApp.java;

public class DatabaseManager
{
    //Inputs
    private String driverName;
    private String urlStr;
    // A utility method to convert the byte array 
    // data into a string representation. 
    public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret; 
    } 

    public void receiveKeypad(int fourDigitPIN,String query)
    {
        try
        {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet update = statement.executeQuery(query);
            if (fourDigitPIN != query)
            {
                error ("Please enter a valid PIN");
            } else {
                System.out.println("Valid PIN, allow entry");
            }


        } catch (SQLException ex) {
            SimpleOutput.showError("Trouble with the database " + 
                                   urlStr);
            ex.printStackTrace();
        }
         


    }
    public void LEDController()
    {

    }

    //Receive data from the parking app and updates the database
    public void Booking(String spot)
    {
        DatagramSocket appds = new DatagramSocket(Port); 
        byte[] receive = new byte[65535];
        DatagramPacket AppReceive = null;
        for(;;)
        {
            AppReceive = new DatagramPacket(buf, length);
            appds.receive(AppReceive);
            if (data(receive).toString().equals("close")) 
            { 
                System.out.println(" "); 
                break; 
            } 
            receive = new byte[65535];
        }
    }

    /**
    * Constructor that takes the driver name and url
    * @param driver the class that communicates with the 
    * database
    * @param url the url of the database as a string
    */
    public DatabaseManager(String driver, String url)
    {
        this.driverName = driver;
        this.urlStr = url;
    
        // try the following
        try {
      
         // load the driver class
         Class.forName(driver);
      
        } catch (ClassNotFoundException ex) {
            SimpleOutput.showError("Can't find the driver class " + 
                             driver + ", check the classpath");
        }
    }
    public void main(String args[])throws IOException
    {
        //Socket creation 
        DatagramSocket ds = new DatagramSocket(Port); 
        byte[] receive = new byte[65535];

        // create the database manager for an Access database
        DatabaseManager dbManager = 
        new DatabaseManager("sun.jdbc.odbc.JdbcOdbcDriver","jdbc:odbc:person");

        DatagramPacket DpReceive = null; 
        while (true) 
        {   
            //Create a DatgramPacket to receive the data. 
            DpReceive = new DatagramPacket(receive, receive.length); 
  
            //Recieve the data in byte buffer. 
            ds.receive(DpReceive); 
  
            System.out.println("Client:-" + data(receive)); 
  
            // Exit the server if the client sends "bye" 
            if (data(receive).toString().equals("bye")) 
            { 
                System.out.println("Client sent bye.....EXITING"); 
                break; 
            } 
  
            // Clear the buffer after every message. 
            receive = new byte[65535]; 
        }
    }

}