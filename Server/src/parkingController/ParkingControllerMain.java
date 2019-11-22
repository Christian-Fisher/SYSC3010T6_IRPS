package parkingController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Stack;

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
    InetAddress ServerAddress;
    Stack<String> parkingControllerQueue, appQueue;

    public static void main(String[] args) {
        ParkingControllerMain mainUDP = new ParkingControllerMain();   //Test
        mainUDP.setup();

        while (true) {
            mainUDP.receive();
        }

    }

    public void setup() {
        try {
            parkingControllerQueue = new Stack<>();
            appQueue = new Stack<>();
            ServerAddress = InetAddress.getByName("localhost");
            socket = new DatagramSocket(2000);
            socket.setSoTimeout(10000);
            sendSocket = new DatagramSocket();

        } catch (SocketException | UnknownHostException e) {
            System.out.println("socket bad");
        }

    }

    public void receive() {

        DatagramPacket heartbeat = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        try {
            socket.receive(heartbeat);
        } catch (Exception e) {

        }
        if (new String(heartbeat.getData()).trim().equals("HB")) {

            if (parkingControllerQueue.isEmpty()) {
                DatagramPacket heartbeatAck = new DatagramPacket(NOTHING_TO_REPORT.getBytes(), NOTHING_TO_REPORT.getBytes().length, ServerAddress, 1000);
                try {
                    sendSocket.send(heartbeatAck);
                } catch (Exception e) {

                }
            } else {
                String heartbeatRespond = parkingControllerQueue.peek();
                DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);
                try {
                    sendSocket.send(heartbeatAck);
                } catch (Exception e) {

                }
                if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(ARDUINO_COMMAND)) {

                    DatagramPacket PinVerificationPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    try {
                        socket.receive(PinVerificationPacket);
                        sendSocket.send(new DatagramPacket((ARDUINO_COMMAND + "ACK").getBytes(), (ARDUINO_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    } catch (SocketTimeoutException e) {

                    } catch (IOException e) {
                        System.out.println("BAD PAIN" + e);
                    }
                    parkingControllerQueue.pop();

                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(IR_COMMAND)) {
                    try {
                        DatagramPacket IRPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                        socket.receive(IRPacket);

                    } catch (SocketTimeoutException e) {

                    } catch (IOException e) {

                    }
                    parkingControllerQueue.pop();
                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(LED_COMMAND)) {
                    DatagramPacket LEDPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    try {
                        socket.receive(LEDPacket);
                        sendSocket.send(new DatagramPacket((LED_COMMAND + "ACK").getBytes(), (LED_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    } catch (SocketTimeoutException e) {

                    } catch (IOException e) {
                        System.out.println("BAD LED" + e);

                    }
                    parkingControllerQueue.pop();
                }
            }
        }
    }

}
