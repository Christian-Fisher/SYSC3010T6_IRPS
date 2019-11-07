
import java.net.*;
import java.util.Scanner;

public class UDPSender {
private final static int PACKETSIZE = 100 ;
    public static void main(String[] args) {
        
        // Check the arguments
        if (args.length != 3) {
            System.out.println("usage: java UDPSender2 host port #messages");
            return;
        }
        DatagramSocket socket = null;
        try {
            // Convert the arguments first, to ensure that they are valid
            InetAddress host = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            int numMessages = Integer.parseInt(args[2]);
            socket = new DatagramSocket();

            Scanner in;
            in = new Scanner(System.in);
            String message = null;
            while (true) {
                System.out.println("Enter text to be sent, ENTER to quit ");
                message = in.nextLine();
                if (message.length() == 0) {
                    break;
                }
                
                for (int x = 1; x <= numMessages; x++) {    
                    message += x;
                    System.out.println("Sending: "+message + " to "+host);
                    byte[] data = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
                    DatagramPacket packetAck = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    socket.send(packet);
                    socket.receive( packetAck ) ;
                    String messAck = new String(packetAck.getData()).trim();
                    System.out.println(messAck) ;
                    message = message.substring(0, message.length()-1);
                }

            }

            System.out.println("Closing down");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
