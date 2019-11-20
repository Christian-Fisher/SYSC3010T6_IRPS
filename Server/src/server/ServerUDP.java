package server;

import java.io.IOException;
import java.net.*;

/**
 * This class will be the main server UDP controller, which will accept UDP
 * packet inputs, and will also send any messages the database requires..
 *
 * @author Christian FisherSYSC3010 T6
 */
public class ServerUDP {

    private final static int PACKETSIZE = 100;      //Defines the default packetsize used for receiving packets.
    InetAddress ParkingControllerAddress, AppAddress;
    DatagramSocket OutgoingSocket, IncomingSocket;
    private final static byte[] HEARTBEATMESSAGE = "HB".getBytes();
    private final static String COMMANDSPLITREGEX = ":";
    private final static String DATASPLITREGEX = ",";
    private final static String LEDCOMMAND = "LED";
    private final static String ARDUINOCOMMAND = "Arduino";
    private final static String NOTHINGTOREPORT = "NA";

    public ServerUDP() {
        try {
            OutgoingSocket = new DatagramSocket();   //Creates new socket for any outgoing packets
            IncomingSocket = new DatagramSocket(1002);  //Creates socket for incoming packets
            ParkingControllerAddress = InetAddress.getByName("localhost");  //Defines address of the parking controller
            AppAddress = InetAddress.getByName("localhost");    //Defines the address of the application 
            IncomingSocket.setSoTimeout(7000);          //Sets the timeout time to 2 seconds so the incoming socket will throw an exception every 2 seconds, to check for other commands.

        } catch (Exception e) {     //If the creation fails, the cause will probably be the InetAddress creation. This will be output, and the address will be fixed.
            System.out.println(e);
        }
    }

    /*
    sendToLED is a method which is designed to toggle the state of a LED related to a specific spot.
    Inputs: String spot and Boolean Occupancy: This defines which spot's LED is going to be toggled and what value it will become.
    Outputs: Void: The method will not return anything, but will send data packets to teh parking controller.
     */
    public void sendToLED(String spot, Boolean Occupancy) {
        String data = LEDCOMMAND + COMMANDSPLITREGEX;       //Prefix the data being sent with "LED:" to indicate the command relates to the LED controller program
        data += spot + DATASPLITREGEX;  //Add the spot identifier to the message
        if (Occupancy) {      //Test for the occupancy of the spot, and add this occupancy to the message
            data += "true";
        } else {
            data += "false";
        }
        byte[] byteArray = data.getBytes();     //Cast the message string into an array of bytes to be sent.
        try {
            DatagramPacket ack = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);      //create a packet to receive the acknowledgement signal
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, ParkingControllerAddress, 1001);    //Create a packet with the message, and the address of the parking controller, along with port 1001
            OutgoingSocket.send(packet);    //Send the packet
            IncomingSocket.receive(ack);    //Wait for a response from the Parking Controller. If the receive timesout, it will throw an exception, which will be caught, and the message will be retransmitted.
            String messAck = new String(ack.getData()).trim();  //Convertt the response to a usable format.
            if ("LEDack".equals(messAck)) { //If the toggling was successful, the message will read "LEDack"
                System.out.println("LED sucessfully Accessed");
            } else {
                System.out.println("LED Access FAILED");    //The message was sucessfully sent, but the toggle failed.
            }
        } catch (Exception e) {
            System.err.println(e);      //The message did not get sent properly, and the message should be retransmitted.
        }
    }

    /*
    sendToArdouni method will tell the arduino if the inputted pin is correct or incorrect. This method will send this data through a UDP packet to the parking controller.
    Inputs: Boolean PinCorrect: a boolean which states wether the pin inputted by the user was valid.
    Outputs: Void: The methodes returns no values, but does communicate with the Parking controller through UDP packets.
     */
    public void sendToArduino(Boolean PinCorrect) {
        String data = "Arduino" + COMMANDSPLITREGEX;   //Prefix the message with "Arduino:" to signal the message is meant for the arduino
        data += PinCorrect.toString();  //Add the pinCorrect boolean to the messgae
        byte[] byteArray = data.getBytes(); //Convert the message to an array of bytes to add to the packet.
        try {
            DatagramPacket ack = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);  //create a packet which will use used to store the ack message
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, ParkingControllerAddress, 1001);    //Create a packet which will contains the message, and is addresssed to the parking controller on port 1001
            OutgoingSocket.send(packet);    //Send this packet
            OutgoingSocket.receive(ack);    //Receive the acknowldgement, if this timesout, the exception will be caught, and the message will be retransmitted.
            String messAck = new String(ack.getData()).trim();  //Convert the ack message into a usable format.
            if ("Arduinoack".equals(messAck)) {     //Read the ack message.
                System.out.println("Arduino Received packet");  //The operation was successful
            } else {
                System.out.println("Arduino format error (Nack)");  //The message was sent properly, but there was an error in teh format of the message
            }
        } catch (Exception e) {
            System.out.println("Arduino send failed");  //The message failed to send, and will be retransmitted.
        }
    }

    public void sendToApp() {
        String data = "Arduino:";

        byte[] byteArray = data.getBytes();
        try {
            DatagramPacket ack = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, AppAddress, 1001);
            OutgoingSocket.send(packet);
            OutgoingSocket.receive(ack);
            String messAck = new String(ack.getData()).trim();
            if ("Arduinoack".equals(messAck)) {
                System.out.println("Arduino Received packet");
            } else {
                System.out.println("Arduino format error (Nack)");
            }
        } catch (Exception e) {
            System.out.println("Arduino send failed");
        }

    }

    public String heartbeatParking() {
        DatagramPacket heartBeat = new DatagramPacket(HEARTBEATMESSAGE, HEARTBEATMESSAGE.length, ParkingControllerAddress, 1001);
        DatagramPacket heartAck = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        try {
            OutgoingSocket.send(heartBeat);
            IncomingSocket.receive(heartAck);

        } catch (IOException e) {
            System.err.println(e + "heartbeat parking failed");
        }
        return (new String(heartAck.getData()).trim());
    }

    public String heartbeatApp() {
        DatagramPacket heartBeat = new DatagramPacket(HEARTBEATMESSAGE, HEARTBEATMESSAGE.length, AppAddress, 1001);
        DatagramPacket heartAck = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        try {
            OutgoingSocket.send(heartBeat);
            IncomingSocket.receive(heartAck);

        } catch (IOException e) {
            System.err.println(e + "heartbeat app failed");
        }
        return (new String(heartAck.getData()).trim());
    }

    public static void main(String[] args) {
        boolean occupancyOfSpotToSend = false;
        String spotToUpdate;
        DatagramPacket incomingPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        ServerUDP udp = new ServerUDP();
        udp.sendToLED("A2", true);
        udp.sendToArduino(true);
        boolean run = true;
        /*
        INCOMING MESSAGE FORM:
        XXX:YYY,ZZZ
        X=LED or APP
        Y=Data
        Z=Data
         */
        while (run) {
            try {
                String heartbeatParkingResponse = udp.heartbeatParking();

                if (!heartbeatParkingResponse.equals(NOTHINGTOREPORT)) {

                    String message = new String(heartbeatParkingResponse.getBytes()).trim();
                    String[] split1String = message.split(":");
                    if (split1String[0].equals("IR")) {
                        spotToUpdate = split1String[1].split(",")[0];
                        System.out.println(spotToUpdate);
                        if (split1String[1].split(",")[1].equals("true")) {
                            occupancyOfSpotToSend = true;
                            System.out.println("true");
                        }
                        if (split1String[1].split(",")[1].equals("false")) {
                            System.out.println("true");
                            occupancyOfSpotToSend = false;
                        }
                        System.out.println(spotToUpdate + " Boolean " + occupancyOfSpotToSend);

                    } else if (split1String[0].equals("LED")) {
                        String LEDMessage[] = split1String[1].split(",");
                        if (LEDMessage[1].equals("true")) {
                            udp.sendToLED(LEDMessage[0], true);
                        } else if (LEDMessage[1].equals("false")) {
                            udp.sendToLED(LEDMessage[0], false);
                        }
                    }
                }
                String heartbeatAppResponse = udp.heartbeatApp();
                if (heartbeatAppResponse.equals(NOTHINGTOREPORT)) {

                }

            } catch (Exception e) {
                System.out.println("Exception");

            }
        }
    }
}
