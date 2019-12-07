package com.example.myapplication2;


import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class UDPThread extends Thread {
    /*
    All static final Strings are used to distinguish commands
     */
    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";
    private final static String NOTHING_TO_REPORT = "NA";
    private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
    private final static String LOGIN_COMMAND = "LOG";
    private final static String CLAIM_COMMAND = "CLA";
    private final static String BOOKING_COMMAND = "BOO";
    private String user;
    private final static int PACKETSIZE = 100;
    DatagramSocket socket, sendSocket;
    InetAddress ServerAddress, local;
    Queue<String> appQueue;

    public UDPThread() {

    }


    @Override
    public void run() { //When this thiread is run, this method is called
        //Test
        setup();    //setup the sockets

        while (true) {  //Main loop of thread
            try {
                receive();

            } catch (IOException e) {   //Error will be handled by Queue
            }
        }


    }
/*
Creates the sockets and queues needed for operation
 */
    public void setup() {
        try {
            appQueue = new LinkedList<>();
            ServerAddress = InetAddress.getByName("192.168.0.180");
            local = InetAddress.getByName("localhost");
            socket = new DatagramSocket(2000);
            socket.setSoTimeout(2000);
            sendSocket = new DatagramSocket();

        } catch (SocketException | UnknownHostException e) {
            System.out.println("Cannot connect to server");
        }
    }
/*
Main method of the thread. receives the heartbeat, and responds with the any commands waiting in the queue.
Can also receive commands from the various activities of the app
 */
    public void receive() throws IOException {
        DatagramPacket heartbeat = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        socket.receive(heartbeat);//Receive a packet
        if (new String(heartbeat.getData()).trim().equals("HBAPP")) {//if the packet was a heartbeat
            if (appQueue.isEmpty()) {
                DatagramPacket heartbeatAck = new DatagramPacket(NOTHING_TO_REPORT.getBytes(), NOTHING_TO_REPORT.getBytes().length, ServerAddress, 1000);
                sendSocket.send(heartbeatAck);
            } else {
                String heartbeatRespond = appQueue.peek();//add the current command to the data to send
                if(!heartbeatRespond.equals("OCCACK")) {    //ensure acknowledgements do not enter the queue
                    DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);
                    sendSocket.send(heartbeatAck);
                }else{
                    appQueue.remove();
                }
                if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(LOGIN_COMMAND)) {//If a login command was sent

                    DatagramPacket login = new DatagramPacket(new byte[200], 200);

                    socket.receive(login);  //receive response from server
                    sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    if (new String(login.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].equals("true")) {  //if the login was valid, inform the MainActivity
                        sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").getBytes().length, local, 3000));
                    } else {
                        sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "NACK").getBytes(), (LOGIN_COMMAND + "NACK").getBytes().length, local, 3000));

                    }
                    appQueue.remove();//if all packets are acknowledgeed properly, remove the command from the queue


                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(OCCUPANCY_UPDATE_COMMAND)) {   //If an occupancy command was sent

                    DatagramPacket lotOccupancyPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    DatagramPacket occAck = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);

                    socket.receive(lotOccupancyPacket);//Receive the server's response
                    sendSocket.send(new DatagramPacket((OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes(), (OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));  //acknowledge teh server


                    sendSocket.send(new DatagramPacket(lotOccupancyPacket.getData(), lotOccupancyPacket.getData().length, local, 3001)); //Inform the booking activity
                    socket.receive(occAck);


                    appQueue.remove();//if all packets are acknowledgeed properly, remove the command from the queue

                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(CLAIM_COMMAND)) {//If the command sent was a claim

                    DatagramPacket claimPacket = new DatagramPacket(new byte[100], 100);
                    socket.receive(claimPacket);//receive response
                    sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "ACK").getBytes(), (CLAIM_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));//acknowledge
                    if (new String(claimPacket.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].equals("true")) {    //if the claim was valid
                        sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "ACK").getBytes(), (CLAIM_COMMAND + "ACK").getBytes().length, local, 3005));//inform claim activity
                    } else {
                        sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "NACK").getBytes(), (CLAIM_COMMAND + "NACK").getBytes().length, local, 3005));//inform claim activity of failure

                    }
                    appQueue.remove();//if all packets are acknowledgeed properly, remove the command from the queue
                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(BOOKING_COMMAND)) {//If the command sent was a booking command
                    DatagramPacket bookPacket = new DatagramPacket(new byte[200], 200);
                    socket.receive(bookPacket);//receive the response
                    sendSocket.send(new DatagramPacket((BOOKING_COMMAND + "ACK").getBytes(), (BOOKING_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));//acknowledge
                    if (new String(bookPacket.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].equals("true")) { //If the booking was successful
                        sendSocket.send(new DatagramPacket((BOOKING_COMMAND + "ACK").getBytes(), (BOOKING_COMMAND + "ACK").getBytes().length, local, 3001));//Inform booking activity
                    } else {
                        sendSocket.send(new DatagramPacket((BOOKING_COMMAND + "NACK").getBytes(), (BOOKING_COMMAND + "NACK").getBytes().length, local, 3001));

                    }
                    appQueue.remove();//if all packets are acknowledgeed properly, remove the command from the queue
                }
            }
        } else {
            if ((new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX)[0].equals(BOOKING_COMMAND))) {//If the command to be entered into the queue is a booking command, add it with the username appened
                appQueue.add(new String(heartbeat.getData()).trim() + DATA_SPLIT_REGEX + user);
            } else if (new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX)[0].equals(LOGIN_COMMAND)) {//If a login attempt is made, record username for later booking
                user = new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].split(DATA_SPLIT_REGEX)[0];
                appQueue.add(new String(heartbeat.getData()).trim());
            }else {
                appQueue.add(new String(heartbeat.getData()).trim());
            }

        }

    }

}