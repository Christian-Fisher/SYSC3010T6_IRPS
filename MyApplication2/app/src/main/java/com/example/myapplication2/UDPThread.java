package com.example.myapplication2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Stack;

public class UDPThread extends Thread {

        private final static String COMMAND_SPLIT_REGEX = ":";
        private final static String DATA_SPLIT_REGEX = ",";
        private final static String LED_COMMAND = "LED";
        private final static String NOTHING_TO_REPORT = "NA";
        private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
        private final static String LOGIN_COMMAND = "LOG";
        private final static String CLAIM_COMMAND = "CLA";

        private final static int PACKETSIZE = 100;
        DatagramSocket socket, sendSocket;
        DatagramPacket packet;
        InetAddress ServerAddress, local;
        Stack<String> appQueue;

        @Override
        public void run() {
            //Test
            setup();

            while (true) {
                try {
                    System.out.println("Thread is working");
                    receive();

                } catch (IOException e) {

                }
            }

        }

        public void setup() {
            try {
                appQueue = new Stack<>();
                ServerAddress = InetAddress.getByName("localhost");
                local = InetAddress.getByName("localhost");
                socket = new DatagramSocket(2000);
                socket.setSoTimeout(10000);
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
                        DatagramPacket login = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(login);
                        sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));

                    } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(OCCUPANCY_UPDATE_COMMAND)) {
                        DatagramPacket lotOccupancyPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(lotOccupancyPacket);
                        sendSocket.send(new DatagramPacket((OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes(), (OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));

                    } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(CLAIM_COMMAND)) {
                        DatagramPacket ClaimPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(ClaimPacket);
                        sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "ACK").getBytes(), (CLAIM_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));

                    }
                }
            } else if (new String(heartbeat.getData()).trim().equals(LOGIN_COMMAND)) {
                String loginRequest[] = new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX);
                appQueue.add(loginRequest[1]);
                System.out.println("Succesfully added to queue");
                sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").length(), local, 3000));
            }

        }

    }