package parkingController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Christian Fisher
 */
public class ParkingControllerMain {

    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";
    private final static String LED_COMMAND = "LED";
    private final static String ARDUINO_COMMAND = "ARD";
    private final static String NOTHING_TO_REPORT = "NA";
    private final static String IR_COMMAND = "IR";

    private final static int PACKETSIZE = 100;
    DatagramSocket socket, sendSocket;
    DatagramPacket packet;
    InetAddress ServerAddress, ParkingControllerAddress;
    Queue<String> parkingControllerQueue;

    public static void main(String[] args) {
        ParkingControllerMain mainUDP = new ParkingControllerMain();   //Test
        mainUDP.setup();

        while (true) {
            try {
                mainUDP.receive();
            } catch (IOException e) {
                System.err.println("IO Exception on Parking Controller");
            }

        }
    }

    public void setup() {
        try {
            parkingControllerQueue = new LinkedList<>();
            ServerAddress = InetAddress.getByName("192.168.0.180");
            ParkingControllerAddress = InetAddress.getByName("localhost");
            socket = new DatagramSocket(2001);
            socket.setSoTimeout(10000);
            sendSocket = new DatagramSocket();

        } catch (SocketException | UnknownHostException e) {
            System.out.println("socket bad");
        }

    }

    public void receive() throws IOException {

        DatagramPacket heartbeat = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        try {
            socket.receive(heartbeat);
        } catch (Exception e) {

        }
        if (new String(heartbeat.getData()).trim().equals("HB")) {

            if (parkingControllerQueue.isEmpty()) {
                DatagramPacket heartbeatAck = new DatagramPacket(NOTHING_TO_REPORT.getBytes(), NOTHING_TO_REPORT.getBytes().length, ServerAddress, 1000);
                sendSocket.send(heartbeatAck);
            } else {
                String heartbeatRespond = parkingControllerQueue.peek();
                DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);

                sendSocket.send(heartbeatAck);

                if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(ARDUINO_COMMAND)) {

                    DatagramPacket PinVerificationPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(PinVerificationPacket);
                        sendSocket.send(new DatagramPacket((ARDUINO_COMMAND + "ACK").getBytes(), (ARDUINO_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                        PinVerificationPacket.setAddress(ParkingControllerAddress);
                        PinVerificationPacket.setPort(3003);
                        sendSocket.send(PinVerificationPacket);
                        socket.receive(new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE));
                        parkingControllerQueue.remove();

                    
                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(IR_COMMAND)) {
                   
                        DatagramPacket IRPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(IRPacket);
                        System.out.println("IR WAS RUN");
                        parkingControllerQueue.remove();
                    
                }
            }
        }else if (new String(heartbeat.getData()).trim().split(COMMAND_SPLIT_REGEX)[0].equals(LED_COMMAND)){
                        sendSocket.send(new DatagramPacket((LED_COMMAND + "ACK").getBytes(), (LED_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                        heartbeat.setAddress(ParkingControllerAddress);
                        heartbeat.setPort(3000);
                        sendSocket.send(heartbeat);
                        System.out.println("sending internally to LED ");
                        
        }else{
                parkingControllerQueue.add(new String(heartbeat.getData()).trim());
                if(parkingControllerQueue.peek().equals("")){
                    System.out.println("Null trying to go into queue");
                    parkingControllerQueue.remove();
                }
        }
    }

}
