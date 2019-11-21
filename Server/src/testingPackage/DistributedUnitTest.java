package testingPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Stack;

/**
 *
 * @author Christian Fisher
 */
public class DistributedUnitTest {

    private final static int PACKETSIZE = 100;
    DatagramSocket socket, sendSocket;
    DatagramPacket packet;
    InetAddress ParkingControllerAddress, AppAddress, ServerAddress;

    Stack<String> parkingControllerQueue, appQueue;

    public static void main(String[] args) {
        DistributedUnitTest test = new DistributedUnitTest();
        test.setup();
        test.testPinVerification();
        test.testIR();
        test.testLoginVerification();
        test.testLotOccupancy();
        while (true) {
            test.receive();
        }

    }

    public void setup() {
        try {
            parkingControllerQueue = new Stack<>();
            appQueue = new Stack<>();
            ParkingControllerAddress = InetAddress.getByName("localhost");  //Defines address of the parking controller
            AppAddress = InetAddress.getByName("localhost");    //Defines the address of the application
            ServerAddress = InetAddress.getByName("localhost");
            socket = new DatagramSocket(2000);
            sendSocket = new DatagramSocket();
        } catch (SocketException | UnknownHostException e) {
            System.out.println("socket bad");
        }

    }

    public void receive() {
        try {
            DatagramPacket heartbeat = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
            System.out.println("Ready");
            socket.receive(heartbeat);
            if (new String(heartbeat.getData()).trim().equals("HB")) {

                if (parkingControllerQueue.isEmpty()) {
                    DatagramPacket heartbeatAck = new DatagramPacket("NA".getBytes(), "NA".getBytes().length, ServerAddress, 1000);
                    sendSocket.send(heartbeatAck);
                    System.out.println("NA");
                } else {
                    String heartbeatRespond = parkingControllerQueue.pop();
                    DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);
                    sendSocket.send(heartbeatAck);
                    if (heartbeatRespond.split(":")[0].equals("Arduino")) {

                        DatagramPacket PinVerificationPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(PinVerificationPacket);
                        System.out.println(new String(PinVerificationPacket.getData()).trim());
                        sendSocket.send(new DatagramPacket("ArduinoACK".getBytes(), "ArduinoACK".getBytes().length, ServerAddress, 1000));

                    } else if (heartbeatRespond.split(":")[0].equals("IR")) {
                        DatagramPacket IRPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(IRPacket);
                        System.out.println(new String(IRPacket.getData()).trim());

                    }
                }
            } else if (new String(heartbeat.getData()).trim().equals("HBAPP")) {
                if (appQueue.isEmpty()) {
                    DatagramPacket heartbeatAck = new DatagramPacket("NA".getBytes(), "NA".getBytes().length, ServerAddress, 1000);
                    sendSocket.send(heartbeatAck);
                    System.out.println("NA");
                } else {
                    String heartbeatRespond = appQueue.pop();
                    DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);
                    sendSocket.send(heartbeatAck);

                    if (heartbeatRespond.split(":")[0].equals("LOG")) {
                        DatagramPacket login = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(login);
                        System.out.println(new String(login.getData()).trim());
                        sendSocket.send(new DatagramPacket("LOGACK".getBytes(), "LOGACK".getBytes().length, ServerAddress, 1000));

                    } else if (heartbeatRespond.split(":")[0].equals("OCC")) {
                        DatagramPacket lotOccupancyPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(lotOccupancyPacket);
                        System.out.println(new String(lotOccupancyPacket.getData()).trim());
                        sendSocket.send(new DatagramPacket("OCCACK".getBytes(), "OCCACK".getBytes().length, ServerAddress, 1000));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public void testIR() {
        parkingControllerQueue.add("IR:A2,false");
    }

    public void testPinVerification() {
        parkingControllerQueue.add("Arduino:1234");
    }

    public void testToggleLEDCorrect() {
        parkingControllerQueue.add("LED:A2:false");
    }

    public void testLoginVerification() {
        appQueue.add("LOG:User,Password!");
    }

    public void testLotOccupancy() {
        appQueue.add("OCC:");
    }

}
