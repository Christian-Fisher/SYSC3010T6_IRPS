/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;

/**
 *This class will be the main server UDP controller, which will accept UDP packet inputs, and will also send any messages the database requires..
 * @author Christian FisherSYSC3010 T6
 */
public class ServerUDP {

    private final static int PACKETSIZE = 100;      //Defines the default packetsize used for receiving packets.
    InetAddress ParkingControllerAddress, AppAddress;   
    DatagramSocket OutgoingSocket, IncomingSocket;   

    public ServerUDP() {
        try {
            OutgoingSocket = new DatagramSocket(1001);   //Creates new socket for any outgoing packets
            IncomingSocket = new DatagramSocket(1002);  //Creates socket for incoming packets
            ParkingControllerAddress = InetAddress.getByName("localhost");  //Defines address of the parking controller
            AppAddress = InetAddress.getByName("localhost");    //Defines the address of the application 

        } catch (Exception e) {     //If the creation fails, the cause will probably be the InetAddress creation. This will be output, and the address will be fixed.
            System.out.println(e);
        }
    }
/*
    sendToLED is a method which is designed to toggle the state of a LED related to a specific spot.
    Inputs: Spot spot: This defines which spot's LED is going to be toggled.
    Outputs: Void: The method will not return anything, but will send data packets to teh parking controller.
    */
    public void sendToLED(Spot spot) {
        String data = "LED:";       //Prefix the data being sent with "LED:" to indicate the command relates to the LED controller program
        data += spot.getLabel() + ",";  //Add the spot identifier to the message
        if (spot.getOccupancy()) {      //Test for the occupancy of the spot, and add this occupancy to the message
            data += "true";
        } else {
            data += "false";
        }
        byte[] byteArray = data.getBytes();     //Cast the message string into an array of bytes to be sent.
        try {       
            DatagramPacket ack = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);      //create a packet to receive the acknowledgement signal
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, ParkingControllerAddress, 1001);    //Create a packet with the message, and the address of the parking controller, along with port 1001
            OutgoingSocket.send(packet);    //Send the packet
            OutgoingSocket.receive(ack);    //Wait for a response from the Parking Controller. If the receive timesout, it will throw an exception, which will be caught, and the message will be retransmitted.
            String messAck = new String(ack.getData()).trim();  //Convertt the response to a usable format.
            if ("LEDack".equals(messAck)) { //If the toggling was successful, the message will read "LEDack"
                System.out.println("LED sucessfully Accessed");
            } else {
                System.out.println("LED Access FAILED");    //The message was sucessfully sent, but the toggle failed.
            }
        } catch (Exception e) {
            System.out.println("LED send failed");      //The message did not get sent properly, and the message should be retransmitted.
        }
    }
/*
    sendToArdouni method will tell the arduino if the inputted pin is correct or incorrect. This method will send this data through a UDP packet to the parking controller.
    Inputs: Boolean PinCorrect: a boolean which states wether the pin inputted by the user was valid.
    Outputs: Void: The methodes returns no values, but does communicate with the Parking controller through UDP packets.
    */
    public void sendToArduino(Boolean PinCorrect) {
        String data = "Arduino:";   //Prefix the message with "Arduino:" to signal the message is meant for the arduino
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

    public static void main(String[] args) {
        Spot pSpot[] = new Spot[9];
        boolean occupancyOfSpotToSend = false;
        String spotToSend;
        DatagramPacket incomingPacket = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        for (int i = 0; i < 9; i++) {
            pSpot[i] = new Spot();
            pSpot[i].setLabel(Integer.toString(i + 1));
            System.out.println(pSpot[i].getLabel());
        }
        ServerUDP udp = new ServerUDP();

//        udp.sendToLED(pSpot[1]);
//        udp.sendToArduino(true);
        boolean run = true;
        while (run) {
            try {
                udp.IncomingSocket.setSoTimeout(1000);
                udp.IncomingSocket.receive(incomingPacket);
                String message = new String(incomingPacket.getData()).trim();
                String[] split1String = message.split(":");
                if (split1String[0].equals("IR")) {
                    spotToSend = split1String[1].split(",")[0];
                    System.out.println(spotToSend);
                    if (split1String[1].split(",")[1].equals("true")) {
                        occupancyOfSpotToSend = true;
                        System.out.println("true");
                    }
                    if (split1String[1].split(",")[1].equals("false")) {
                        System.out.println("true");
                        occupancyOfSpotToSend = false;
                    }
                    System.out.println(spotToSend + " Boolean " + occupancyOfSpotToSend);

                } else if (true) {

                }
                byte[] ackArray = (split1String[0] + "ack").getBytes();
                DatagramPacket ack = new DatagramPacket(ackArray, ackArray.length, incomingPacket.getAddress(), incomingPacket.getPort());
                udp.IncomingSocket.send(ack);
            } catch (SocketTimeoutException e) {

            } catch (Exception e) {

            }

        }
    }
}
