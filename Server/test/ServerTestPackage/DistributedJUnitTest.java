package ServerTestPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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

    public DistributedJUnitTest() {
        try{
        Incomingsocket = new DatagramSocket();
        Outgoingsocket = new DatagramSocket();
        Incomingsocket.setSoTimeout(2000);
        } catch(Exception e){
            System.out.println("socket bad");
        }
    }

    public void testSendToLEDCorrect() {
        try {
            ServerUDP udp = new ServerUDP();
            udp.sendToLED("A2", true);

            packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
            Incomingsocket.receive(packet);
            String MESSAGE = new String(packet.getData()).trim();
            byte[] ackArray = "LEDack".getBytes();
            DatagramPacket ack = new DatagramPacket(ackArray, ackArray.length, packet.getAddress(), packet.getPort());
            Outgoingsocket.send(ack);
            Assert.assertEquals("LED:A2,true", MESSAGE);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
