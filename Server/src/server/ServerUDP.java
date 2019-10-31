/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.net.*;

/**
 *
 * @author Christian FisherSYSC3010 T6
 */
public class ServerUDP {
    private final static int PACKETSIZE = 100;
    private static InetAddress ParkingController;
    DatagramSocket socket = null;
    
    public ServerUDP(){
        try{
            socket = new DatagramSocket();
            ParkingController = InetAddress.getByName("192.168.0.801");

            
        }catch(Exception e){
            System.out.println("Error, shit's done fucked");
        }
        
        
    }
    public void sendToLED(Spot spot){
        String data = "LED:";
        data+= spot.getLabel()+",";
        if(spot.getOccupancy()){
            data +="true";
        }else{
            data+="false";
        }
        byte[] byteArray = data.getBytes();
        try{
        DatagramPacket ack = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length,ParkingController, 1001);
        socket.send(packet);
        socket.receive(ack);
        String messAck = new String(ack.getData()).trim();
        if("ACK".equals(messAck)){
            System.out.println("LED sucessfully toggled");
        }else{
            System.out.println("LED toggling FAILED");
        }
        }catch(Exception e){
            System.out.println("LED send failed");
        }
    }
    
}