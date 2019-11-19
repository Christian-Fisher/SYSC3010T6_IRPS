package ServerTestPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import server.ServerUDP;

/**
 *
 * @author Christian Fisher
 */
public class DistributedJUnitTest extends TestCase {

    private final static int PACKETSIZE = 100;
    DatagramSocket Outgoingsocket, Incomingsocket;
    DatagramPacket packet;
    InetAddress ParkingControllerAddress, AppAddress;

    public DistributedJUnitTest() {
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

    @Test
    public void testToggleLEDCOrrect() {
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
            Assert.assertEquals("LED:A2,true", MESSAGE);
        } catch (Exception e) {
            System.err.println("TEst " + e);
        }

    }

}
