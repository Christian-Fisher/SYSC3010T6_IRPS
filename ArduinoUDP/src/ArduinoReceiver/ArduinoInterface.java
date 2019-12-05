/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArduinoReceiver;

import com.fazecast.jSerialComm.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian Fisher
 */
public class ArduinoInterface {

    public static void main(String[] args) {

        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println(Arrays.toString(ports));
        SerialPort port = ports[0];
        port.openPort();
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 2000, 0);
        port.setBaudRate(9600);

        byte[] readBuffer;
        boolean run = true;
        String pin = "";

        try {
            DatagramSocket socket = new DatagramSocket(3003);
            DatagramSocket sendSocket = new DatagramSocket();
            InetAddress ParkingController = InetAddress.getByName("localhost");
            while (true) {
                int x = 0;
                while (run) {

                    if (x == 4) {
                        run = false;
                    }
                    readBuffer = new byte[1];
                    port.readBytes(readBuffer, 1);
                    if (readBuffer[0] != 0) {
                        if (readBuffer[0] != 13) {
                            if (readBuffer[0] != 10) {
                                System.out.println(Arrays.toString(readBuffer));
                                pin += new String(readBuffer);
                                x++;
                            }
                        }
                    }
                    Thread.sleep(100);
                }
                System.out.println("Test " + pin);
                DatagramPacket ack = new DatagramPacket(new byte[20], 20);
                sendSocket.send(new DatagramPacket(pin.getBytes(), pin.getBytes().length, ParkingController, 2001));
                socket.receive(ack);
                if (new String(ack.getData()).trim().split(":")[1].equals("true")) {
                byte response[] = new byte[1];
                port.writeBytes(response, 1);
                } else {
                    byte response[] = new byte[0];
                    port.writeBytes(response, 0);
                }
                port.closePort();
                run = true;
            }
        } catch (SerialPortInvalidPortException e) {
            System.err.println(e);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ArduinoInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(ArduinoInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArduinoInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ArduinoInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
