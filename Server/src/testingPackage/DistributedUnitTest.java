package testingPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Christian Fisher
 */
public class DistributedUnitTest {

    private final static int PACKETSIZE = 100;
    DatagramSocket Outgoingsocket, Incomingsocket;
    DatagramPacket packet;
    InetAddress ParkingControllerAddress, AppAddress;

    public static void main(String[] args) {
        DistributedUnitTest test = new DistributedUnitTest();
        test.setup();
        test.ToggleLEDCorrect();
    }

    public void setup() {
        try {

            ParkingControllerAddress = InetAddress.getByName("localhost");  //Defines address of the parking controller
            AppAddress = InetAddress.getByName("localhost");    //Defines the address of the application
            Incomingsocket = new DatagramSocket(1001);
            Outgoingsocket = new DatagramSocket();
            Incomingsocket.setSoTimeout(2000);
        } catch (Exception e) {
            System.out.println("socket bad");
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
            System.err.println("TEst " + e);
        }
        return false;
    }

}
