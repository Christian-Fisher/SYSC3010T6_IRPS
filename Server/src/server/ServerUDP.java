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

    private final static int PACKET_SIZE = 100; // Defines the default packetsize used for receiving packets.
    InetAddress ParkingControllerAddress, AppAddress;
    DatagramSocket socket, sendSocket;
    private final static byte[] HEARTBEAT_MESSAGE = "HB".getBytes();
    private final static byte[] HEARTBEAT_MESSAGE_APP = "HBAPP".getBytes();
    private final static String COMMAND_SPLIT_REGEX = ":";
    private final static String DATA_SPLIT_REGEX = ",";
    private final static String LED_COMMAND = "LED";
    private final static String ARDUINO_COMMAND = "ARD";
    private final static String NOTHING_TO_REPORT = "NA";
    private final static String OCCUPANCY_UPDATE_COMMAND = "OCC";
    private final static String IR_COMMAND = "IR";
    private final static String LOGIN_COMMAND = "LOG";
    private final static String CLAIM_COMMAND = "CLA";
    private final static int LOT_SIZE = 9;

    public ServerUDP() {
        try {

            socket = new DatagramSocket(1000); // Creates new socket for any outgoing packets
            sendSocket = new DatagramSocket();

            ParkingControllerAddress = InetAddress.getByName("localhost"); // Defines address of the parking controller
            AppAddress = InetAddress.getByName("localhost"); // Defines the address of the application
//           socket.setSoTimeout(7000); // Sets the timeout time to 2 seconds so the incoming socket will throw
            // an exception every 2 seconds, to check for other commands.

        } catch (Exception e) { // If the creation fails, the cause will probably be the InetAddress creation.
            // This will be output, and the address will be fixed.
            System.err.println(e);
        }
    }

    /*
	 * sendToLED is a method which is designed to toggle the state of a LED related
	 * to a specific spot. Inputs: String spot and Boolean Occupancy: This defines
	 * which spot's LED is going to be toggled and what value it will become.
	 * Outputs: Void: The method will not return anything, but will send data
	 * packets to the parking controller.
     */
    public void sendToLED(String spot, Boolean Occupancy) {
        String data = LED_COMMAND + COMMAND_SPLIT_REGEX; // Prefix the data being sent with "LED:" to indicate the command
        // relates to the LED controller program
        data += spot + DATA_SPLIT_REGEX; // Add the spot identifier to the message
        if (Occupancy) { // Test for the occupancy of the spot, and add this occupancy to the message
            data += "true";
        } else {
            data += "false";
        }
        byte[] byteArray = data.getBytes(); // Cast the message string into an array of bytes to be sent.
        try {
            DatagramPacket ack = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE); // create a packet to receive the
            // acknowledgement signal
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, ParkingControllerAddress, 2000); // Create
//            System.out.println(new String(packet.getData()).trim());
            sendSocket.send(packet); // Send the packet
            socket.receive(ack); // Wait for a response from the Parking Controller. If the receive timesout, it
            // will throw an exception, which will be caught, and the message will be
            // retransmitted.
            String messAck = new String(ack.getData()).trim(); // Convertt the response to a usable format.
        } catch (Exception e) {
            System.err.println(e); // The message did not get sent properly, and the message should be
            // retransmitted.
        }
    }

    /*
	 * sendToArduino method will tell the arduino if the inputted pin is correct or
	 * incorrect. This method will send this data through a UDP packet to the
	 * parking controller. Inputs: Boolean PinCorrect: a boolean which states wether
	 * the pin inputted by the user was valid. Outputs: Void: The methodes returns
	 * no values, but does communicate with the Parking controller through UDP
	 * packets.
     */
    public void sendToArduino(Boolean PinCorrect) {
        String data = ARDUINO_COMMAND + COMMAND_SPLIT_REGEX; // Prefix the message with "Arduino:" to signal the message is
        // meant for the arduino
        data += PinCorrect; // Add the pinCorrect boolean to the messgae
        byte[] byteArray = data.getBytes(); // Convert the message to an array of bytes to add to the packet.
        try {
            DatagramPacket ack = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE); // create a packet which will use
            // used to store the ack message
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, ParkingControllerAddress, 2000);
            sendSocket.send(packet); // Send this packet
            socket.receive(ack); // Receive the acknowldgement, if this timesout, the exception will be caught,
            // and the message will be retransmitted.
            String messAck = new String(ack.getData()).trim(); // Convert the ack message into a usable format.
            if ("ARDACK".equals(messAck)) { // Read the ack message.
            } else {
                System.out.println("Arduino format error (Nack)"); // The message was sent properly, but there was an
                // error in teh format of the message
            }
        } catch (IOException e) {
            System.out.println("Arduino send failed"); // The message failed to send, and will be retransmitted.
        }
    }

    public void sendToIR(String IRMessage[]) {
        String data = "IRACK";
        try {
            DatagramPacket LoginAck = new DatagramPacket(data.getBytes(), data.getBytes().length, ParkingControllerAddress, 2000);
            sendSocket.send(LoginAck);

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public void sendToAppLogin(String loginMessage[]) {
        String data = "LOG:";
        try {
            if (loginMessage[0].equals("User") && loginMessage[1].equals("Password")) {
                DatagramPacket loginRequest = new DatagramPacket((data + "true").getBytes(), (data + "true").getBytes().length, AppAddress, 2000);
                sendSocket.send(loginRequest);
            } else {
                DatagramPacket loginRequest = new DatagramPacket((data + "false").getBytes(), (data + "false").getBytes().length, AppAddress, 2000);
                sendSocket.send(loginRequest);
            }
            DatagramPacket LoginAck = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
            socket.receive(LoginAck);

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public void sendToAppClaim(String ClaimMessage) {
        try {
            String ClaimResponse = CLAIM_COMMAND + COMMAND_SPLIT_REGEX + "false";
            if (ClaimMessage.equals("ABCDE123")) {
                ClaimResponse = CLAIM_COMMAND + COMMAND_SPLIT_REGEX + "true";
            }

            DatagramPacket ClaimPacket = new DatagramPacket(ClaimResponse.getBytes(), ClaimResponse.getBytes().length, AppAddress, 2000);
            DatagramPacket ClaimAck = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
            sendSocket.send(ClaimPacket);
            socket.receive(ClaimAck);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public void sendToAppOcccupancy() {
        try {
            String occupancyMessage = OCCUPANCY_UPDATE_COMMAND + COMMAND_SPLIT_REGEX + "true";
            for (int x = 1; x < LOT_SIZE; x++) {
                occupancyMessage += DATA_SPLIT_REGEX + false;
            }
            DatagramPacket OccupancyUpdate = new DatagramPacket(occupancyMessage.getBytes(), occupancyMessage.getBytes().length, AppAddress, 2000);
            DatagramPacket OccAck = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
            sendSocket.send(OccupancyUpdate);
            socket.receive(OccAck);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public String heartbeatParking() {
        DatagramPacket heartBeat = new DatagramPacket(HEARTBEAT_MESSAGE, HEARTBEAT_MESSAGE.length, ParkingControllerAddress, 2000);
        DatagramPacket heartAck = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
        try {
            sendSocket.send(heartBeat);
            socket.receive(heartAck);

        } catch (IOException e) {
            System.err.println(e + "heartbeat parking failed");
        }
        return (new String(heartAck.getData()).trim());
    }

    public String heartbeatApp() {
        DatagramPacket heartBeat = new DatagramPacket(HEARTBEAT_MESSAGE_APP, HEARTBEAT_MESSAGE_APP.length, AppAddress, 2000);
        DatagramPacket heartAck = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
        try {
            sendSocket.send(heartBeat);
            socket.receive(heartAck);

        } catch (IOException e) {
            System.err.println(e + "heartbeat app failed");
        }
        return (new String(heartAck.getData()).trim());
    }

    public static void main(String[] args) {
        ServerUDP udp = new ServerUDP();
        /*
        INCOMING MESSAGE FORM: XXX:YYY,ZZZ 
        X=LED or APP 
        Y=Data 
        Z=Data
         */
        while (true) {
            try {
                String heartbeatParkingResponse = udp.heartbeatParking();
                if (!heartbeatParkingResponse.equals(NOTHING_TO_REPORT)) {

                    String message = new String(heartbeatParkingResponse.getBytes()).trim();
                    String[] split1String = message.split(COMMAND_SPLIT_REGEX);

                    if (split1String[0].equals(IR_COMMAND)) {
                        udp.sendToIR(split1String[1].split(DATA_SPLIT_REGEX));

                    } else if (split1String[0].equals(ARDUINO_COMMAND)) {
                        udp.sendToArduino(split1String[1].equals("1234"));

                    }else if(split1String[0].equals(LED_COMMAND)){
                        udp.sendToLED("A2", Boolean.TRUE);
                    }
                }
                String heartbeatAppResponse = udp.heartbeatApp();
                if (!heartbeatAppResponse.equals(NOTHING_TO_REPORT)) {
                    String message = new String(heartbeatAppResponse.getBytes()).trim();
                    String[] split1String = message.split(COMMAND_SPLIT_REGEX);

                    if (split1String[0].equals(OCCUPANCY_UPDATE_COMMAND)) {
                        udp.sendToAppOcccupancy();

                    } else if (split1String[0].equals(LOGIN_COMMAND)) {
                        udp.sendToAppLogin(split1String[1].split(DATA_SPLIT_REGEX));

                    } else if (split1String[0].equals(CLAIM_COMMAND)) {
                        udp.sendToAppClaim(split1String[1]);
                    }
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

    }
}
