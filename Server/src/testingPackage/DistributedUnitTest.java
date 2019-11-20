package testingPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Christian Fisher
 */
public class DistributedUnitTest {

    private final static int PACKETSIZE = 100;
    DatagramSocket Outgoingsocket, Incomingsocket;
    DatagramPacket packet;
    InetAddress ParkingControllerAddress, AppAddress, ServerAddress;
    Queue<String> messageQueue;

    public static void main(String[] args) {
        DistributedUnitTest test = new DistributedUnitTest();
        
        test.setup();
        while (true) {
            test.receiveHeartBeat();
        }

    }

    public void setup() {
        try {
            messageQueue = new LinkedList();
            ParkingControllerAddress = InetAddress.getByName("localhost");  //Defines address of the parking controller
            AppAddress = InetAddress.getByName("localhost");    //Defines the address of the application
            ServerAddress = InetAddress.getByName("localhost");
            Incomingsocket = new DatagramSocket(1001);
            Outgoingsocket = new DatagramSocket();
            Incomingsocket.setSoTimeout(2000);
        } catch (Exception e) {
            System.out.println("socket bad");
        }

    }

    public void receiveHeartBeat() {
        try {
            DatagramPacket heartbeat = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
            Incomingsocket.receive(heartbeat);
            if(messageQueue.peek().equals(null)){
                DatagramPacket heartbeatAck = new DatagramPacket("NA".getBytes(), "NA".getBytes().length, ServerAddress, 1002);
                Outgoingsocket.send(heartbeatAck);
            }else{
                String heartbeatRespond = messageQueue.remove();
                DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1002);
                Outgoingsocket.send(heartbeatAck);
            }
        } catch (Exception e) {
            System.err.println("DICK" +e);
        }

    }

    public boolean ToggleLEDCorrect() {
        try {
            String message = "LED:A2,true";
            byte initMessageByteArray[] = message.getBytes();
            DatagramPacket packet = new DatagramPacket(initMessageByteArray, initMessageByteArray.length, ParkingControllerAddress, 1002);
            Outgoingsocket.send(packet);

            packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
            Incomingsocket.receive(packet);
            String MESSAGE = new String(packet.getData()).trim();
            System.out.println(MESSAGE);
            byte[] ackArray = "LEDack".getBytes();
            DatagramPacket ack = new DatagramPacket(ackArray, ackArray.length, packet.getAddress(), 1002);
            Outgoingsocket.send(ack);
            if (MESSAGE.equals(message)) {
                return true;
            }

        } catch (Exception e) {
            System.err.println("Test " + e);
        }
        return false;
    }

}
