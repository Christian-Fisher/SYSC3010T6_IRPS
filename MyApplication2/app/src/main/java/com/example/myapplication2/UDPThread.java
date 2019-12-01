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

    public UDPThread(){

    }



        @Override
        public void run() {
            //Test
            setup();

            while (true) {
                try {
//                    System.out.println("Thread is working");
                    receive();

                } catch (IOException e) {
//                    System.out.println("Something is happening");
                }
            }


        }

        public void setup() {
            try {
                appQueue = new LinkedList<>();
                ServerAddress = InetAddress.getByName("192.168.0.180");
                local = InetAddress.getByName("192.168.0.181");
                socket = new DatagramSocket(2000);
                socket.setSoTimeout(300);
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

                        DatagramPacket login = new DatagramPacket(new byte[200], 200);

                        socket.receive(login);
                        sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                        if(new String(login.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].equals("true")){
                            sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").getBytes().length, local, 3000));
                        }else{
                            sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "NACK").getBytes(), (LOGIN_COMMAND + "NACK").getBytes().length, local, 3000));

                        }
                        appQueue.remove();


                    } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(OCCUPANCY_UPDATE_COMMAND)) {

                        DatagramPacket lotOccupancyPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        DatagramPacket occAck = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);

                        socket.receive(lotOccupancyPacket);
                        sendSocket.send(new DatagramPacket((OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes(), (OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));


                        sendSocket.send(new DatagramPacket(lotOccupancyPacket.getData(), lotOccupancyPacket.getData().length, local, 3001));
                        socket.receive(occAck);


                        appQueue.remove();

                    } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(CLAIM_COMMAND)) {

                        DatagramPacket claimPacket = new DatagramPacket(new byte[100], 100);
                        socket.receive(claimPacket);
                        sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "ACK").getBytes(), (CLAIM_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                        if (new String(claimPacket.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].equals("true")) {
                            sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "ACK").getBytes(), (CLAIM_COMMAND + "ACK").getBytes().length, local, 3005));
                        } else {
                            sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "NACK").getBytes(), (CLAIM_COMMAND + "NACK").getBytes().length, local, 3005));

                        }
                        appQueue.remove();
                    }else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(BOOKING_COMMAND)){
                        DatagramPacket bookPacket = new DatagramPacket(new byte[200], 200);
                        socket.receive(bookPacket);
                        sendSocket.send(new DatagramPacket((BOOKING_COMMAND + "ACK").getBytes(), (BOOKING_COMMAND+ "ACK").getBytes().length, ServerAddress, 1000));
                        if (new String(bookPacket.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].equals("true")) {
                            Log.i("UDPThread BOOK", "Great Success");
                            sendSocket.send(new DatagramPacket((BOOKING_COMMAND + "ACK").getBytes(), (BOOKING_COMMAND + "ACK").getBytes().length, local, 3001));
                        } else {
                            sendSocket.send(new DatagramPacket((BOOKING_COMMAND + "NACK").getBytes(), (BOOKING_COMMAND + "NACK").getBytes().length, local, 3001));
                            Log.i("UDPThread BOOK", "NO Success");

                        }
                        appQueue.remove();
                    }
                }
            }else {
                appQueue.add(new String(heartbeat.getData()).trim());
                if(new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX)[0].equals(LOGIN_COMMAND)){
                    user = new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX)[1].split(DATA_SPLIT_REGEX)[0];
                }
                System.out.println("Processing Request");
        }

    }

    }