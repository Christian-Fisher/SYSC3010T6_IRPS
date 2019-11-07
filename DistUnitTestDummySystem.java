
import java.net.*;

public class DistUnitTestDummySystem {

    private final static int PACKETSIZE = 100;

    public static void main(String args[]) {
        // Check the arguments
        DatagramSocket socket2;

        if (args.length != 1) {
            System.out.println("usage: UDPReceiver port");
            return;
        }
        try {
            socket2 = new DatagramSocket();
            // Convert the argument to ensure that is it valid
            int port = Integer.parseInt(args[0]);

            // Construct the socket
            DatagramSocket socket = new DatagramSocket(port);

            for (;;) {
                System.out.println("Receiving on port " + port);
                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                socket.receive(packet);
                String MESSAGE = new String(packet.getData()).trim();
                byte[] data = MESSAGE.getBytes();
                System.out.println(packet.getAddress() + " " + packet.getPort() + ": " + MESSAGE);
                //Acknowledging 
                String stringAck = "";
                if (MESSAGE.contains("Arduino")) {
                    stringAck = "Arduinoack";
                } else {
                    stringAck = "LEDack";
                }
                byte[] dataAck = stringAck.getBytes();
                DatagramPacket packet2 = new DatagramPacket(dataAck, dataAck.length, packet.getAddress(), packet.getPort());
                socket2.send(packet2);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
