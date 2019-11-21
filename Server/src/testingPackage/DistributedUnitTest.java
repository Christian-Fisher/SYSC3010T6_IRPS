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

    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";
    private final static String LED_COMMAND = "LED";
    private final static String ARDUINO_COMMAND = "ARD";
    private final static String NOTHING_TO_REPORT = "NA";
    private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
    private final static String IR_COMMAND = "IR";
    private final static String LOGIN_COMMAND = "LOG";
    private final static String CLAIM_COMMAND = "CLA";

    private final static int PACKETSIZE = 100;
    DatagramSocket socket, sendSocket;
    DatagramPacket packet;
    InetAddress ServerAddress;
    boolean HB = false, IR = false, LED = false, PIN = false, OCC = false, LOG = false, CLAIM = false;
    Stack<String> parkingControllerQueue, appQueue;

    public static void main(String[] args) {
        DistributedUnitTest test = new DistributedUnitTest();   //Test
        test.setup();
        try {
            test.testHeartbeat();
            System.out.println("Heartbeat Test Passed");
            test.HB=true;
        } catch (IOException e) {
            System.out.println("Heartbeat test failed");
            return;
        }
        test.runAlLTests();
        while (true) {
            try {
                test.receive();
                if (test.HB && test.CLAIM && test.IR && test.LED && test.LOG && test.OCC && test.PIN) {
                    System.out.println("All Tests were successful");
                    return;
                }
            } catch (IOException e) {

            }
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

    public void receive() throws IOException {

        DatagramPacket heartbeat = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        socket.receive(heartbeat);
        if (new String(heartbeat.getData()).trim().equals("HB")) {

            if (parkingControllerQueue.isEmpty()) {
                DatagramPacket heartbeatAck = new DatagramPacket(NOTHING_TO_REPORT.getBytes(), NOTHING_TO_REPORT.getBytes().length, ServerAddress, 1000);
                sendSocket.send(heartbeatAck);
            } else {
                String heartbeatRespond = parkingControllerQueue.pop();
                DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);
                sendSocket.send(heartbeatAck);
                if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(ARDUINO_COMMAND)) {

                    DatagramPacket PinVerificationPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    socket.receive(PinVerificationPacket);
                    sendSocket.send(new DatagramPacket((ARDUINO_COMMAND + "ACK").getBytes(), (ARDUINO_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    if (new String(PinVerificationPacket.getData()).trim().equals("ARD:true")) {
                        System.out.println("Passed: PIN Verification test");
                        PIN = true;
                    }
                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(IR_COMMAND)) {
                    DatagramPacket IRPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    socket.receive(IRPacket);
                    if (new String(IRPacket.getData()).trim().equals("IRACK")) {
                        System.out.println("Passed: IR test");
                        IR=true;
                    }

                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(LED_COMMAND)) {
                    DatagramPacket LEDPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    socket.receive(LEDPacket);
                    sendSocket.send(new DatagramPacket((LED_COMMAND + "ACK").getBytes(), (LED_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    if (new String(LEDPacket.getData()).trim().equals("LED:A2,true")) {
                        System.out.println("Passed: LED Test");
                        LED=Boolean.TRUE;
                    }
                }
            }
        } else if (new String(heartbeat.getData()).trim().equals("HBAPP")) {
            if (appQueue.isEmpty()) {
                DatagramPacket heartbeatAck = new DatagramPacket(NOTHING_TO_REPORT.getBytes(), NOTHING_TO_REPORT.getBytes().length, ServerAddress, 1000);
                sendSocket.send(heartbeatAck);
            } else {
                String heartbeatRespond = appQueue.pop();
                DatagramPacket heartbeatAck = new DatagramPacket(heartbeatRespond.getBytes(), heartbeatRespond.getBytes().length, ServerAddress, 1000);
                sendSocket.send(heartbeatAck);

                if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(LOGIN_COMMAND)) {
                    DatagramPacket login = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    socket.receive(login);
                    sendSocket.send(new DatagramPacket((LOGIN_COMMAND + "ACK").getBytes(), (LOGIN_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    if (new String(login.getData()).trim().equals("LOG:true")) {
                        System.out.println("Passed: Login Verification Test");
                        LOG = true;
                    }

                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(OCCUPANCY_UPDATE_COMMAND)) {
                    DatagramPacket lotOccupancyPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    socket.receive(lotOccupancyPacket);
                    sendSocket.send(new DatagramPacket((OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes(), (OCCUPANCY_UPDATE_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    if (new String(lotOccupancyPacket.getData()).trim().equals("OCC:true,false,false,false,false,false,false,false,false")) {
                        System.out.println("Passed: Lot Occupancy test");
                        OCC = true;
                    }
                } else if (heartbeatRespond.split(COMMAND_SPLIT_REGEX)[0].equals(CLAIM_COMMAND)) {
                    DatagramPacket ClaimPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                    socket.receive(ClaimPacket);
                    sendSocket.send(new DatagramPacket((CLAIM_COMMAND + "ACK").getBytes(), (CLAIM_COMMAND + "ACK").getBytes().length, ServerAddress, 1000));
                    if (new String(ClaimPacket.getData()).trim().equals("CLA:true")) {
                        System.out.println("Passed: Claim Verification Test");
                        CLAIM = true;
                    }
                }
            }
        }

    }

    private void testIR() {
        System.out.println("Beginning Receive: IR Update test");
        parkingControllerQueue.add("IR:A2,false");
    }

    private void testPinVerification() {
        System.out.println("Beginning Receive: PIN Verification test");
        parkingControllerQueue.add("ARD:1234");
    }

    private void testToggleLED() {
        System.out.println("Beginning Send: LED Toggle test");
        parkingControllerQueue.add("LED:A2:true");
    }

    private void testLoginVerification() {
        System.out.println("Beginning Receive: Login Verification test");
        appQueue.add("LOG:User,Password");
    }

    private void testLotOccupancy() {
        System.out.println("Beginning Receive: Lot Occupancy test");
        appQueue.add("OCC:");
    }

    private void testClaims() {
        System.out.println("Beginning Receive: Claims Verification test");
        appQueue.add("CLA:ABCDE123");
    }

    private void testHeartbeat() throws IOException {
        System.out.println("Testing Heartbeat");
        receive();
    }

    public void runAlLTests() {
        testToggleLED();
        testClaims();
        testPinVerification();
        testLoginVerification();
        testIR();
        testLotOccupancy();

    }

}
