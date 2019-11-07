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
   InetAddress ParkingControllerAddress, AppAddress;
    DatagramSocket ParkingSocket = null;
    
    public ServerUDP(){
        try{
            ParkingSocket = new DatagramSocket();
            ParkingControllerAddress = InetAddress.getByName("localhost");
            AppAddress = InetAddress.getByName("localhost");
            
        }catch(Exception e){
            System.out.println(e);
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
        DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length,ParkingControllerAddress, 1001);
        ParkingSocket.send(packet);
        ParkingSocket.receive(ack);
        String messAck = new String(ack.getData()).trim();
        if("LEDack".equals(messAck)){
            System.out.println("LED sucessfully Accessed");
        }else{
            System.out.println("LED Access FAILED");
        }
        }catch(Exception e){
            System.out.println("LED send failed");
        }
    }
    public void sendToArduino(Boolean PinCorrect){
        String data = "Arduino:";
        data+= PinCorrect.toString();
        byte[] byteArray = data.getBytes();
        try{
        DatagramPacket ack = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length,ParkingControllerAddress, 1001);
        ParkingSocket.send(packet);
        ParkingSocket.receive(ack);
        String messAck = new String(ack.getData()).trim();
        if("Arduinoack".equals(messAck)){
            System.out.println("Arduino Received packet");
        }else{
            System.out.println("Arduino format error (Nack)");
        }
        }catch(Exception e){
            System.out.println("Arduino send failed");
        }
    }
    
    public void sendToApp(){
         String data = "Arduino:";
        
        byte[] byteArray = data.getBytes();
        try{
        DatagramPacket ack = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
        DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length,AppAddress, 1001);
        ParkingSocket.send(packet);
        ParkingSocket.receive(ack);
        String messAck = new String(ack.getData()).trim();
        if("Arduinoack".equals(messAck)){
            System.out.println("Arduino Received packet");
        }else{
            System.out.println("Arduino format error (Nack)");
        }
        }catch(Exception e){
            System.out.println("Arduino send failed");
        }
        
    }
    
    public static void main(String[] args){
        Spot pSpot[] = new Spot[9];
        for(int i =0; i<9; i++){
            pSpot[i]=new Spot();
            pSpot[i].setLabel(Integer.toString(i+1));
            System.out.println(pSpot[i].getLabel());
        }
        ServerUDP udp = new ServerUDP();
        udp.sendToLED(pSpot[1]);
        udp.sendToArduino(true);
        
    }
    
    
}