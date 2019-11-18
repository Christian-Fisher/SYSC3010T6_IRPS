package server;

import java.net.*;

public class DistUnitTestDummySystem {

    private final static int PACKETSIZE = 100;

    public static void main(String args[]) {
        // Check the arguments
        DatagramSocket socket2;

        try {
            socket2 = new DatagramSocket();
            // Convert the argument to ensure that is it valid

            // Construct the socket
            DatagramSocket socket = new DatagramSocket(1001);

            try {
                ServerUDP udp = new ServerUDP();
                udp.sendToLED("A2", true);

                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                socket.receive(packet);
                String MESSAGE = new String(packet.getData()).trim();
                System.out.println(MESSAGE + "Dist");
                byte[] ackArray = "LEDack".getBytes();
                DatagramPacket ack = new DatagramPacket(ackArray, ackArray.length, packet.getAddress(), 1002);
                socket.send(ack);
            } catch (Exception e) {
                System.err.println("TEst" + e);
            }
//
//                System.out.println("Receiving on port 1001");
//                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
//                socket.receive(packet);
//                String MESSAGE = new String(packet.getData()).trim();
//                byte[] data = MESSAGE.getBytes();
//                System.out.println(packet.getAddress() + " " + packet.getPort() + ": " + MESSAGE);
//                //Acknowledging 
//                String stringAck = "";
//                if (MESSAGE.contains("Arduino")) {
//                    stringAck = "Arduinoack";
//                } else {
//                    stringAck = "LEDack";
//                }
//                byte[] dataAck = stringAck.getBytes();
//                DatagramPacket packet2 = new DatagramPacket(dataAck, dataAck.length, packet.getAddress(), packet.getPort());
//                socket2.send(packet2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
