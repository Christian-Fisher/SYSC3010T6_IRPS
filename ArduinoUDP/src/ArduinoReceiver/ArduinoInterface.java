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
import java.util.Arrays;

/**
 *
 * @author Christian Fisher
 */
public class ArduinoInterface {

    public static void main(String[] args) {
        try {
            SerialPort port = SerialPort.getCommPort("0");
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 2000, 2000);
            port.setBaudRate(9600);
            DatagramSocket socket = new DatagramSocket(3003);
            DatagramSocket sendSocket = new DatagramSocket();
            InetAddress Server = InetAddress.getByName("localhost");

            while (true) {

                byte[] readBuffer = new byte[1024];
                port.readBytes(readBuffer, readBuffer.length);
                System.out.println(Arrays.toString(readBuffer));
                if (!new String(readBuffer).equals("")) {
                    DatagramPacket pinAck = new DatagramPacket(new byte[100], 100);
                    sendSocket.send(new DatagramPacket(("ARD:" + new String(readBuffer)).getBytes(), ("ARD:" + new String(readBuffer)).getBytes().length, Server, 2001));
                    socket.receive(pinAck);
                    if (new String(pinAck.getData()).trim().split(":")[1].equals("true")) {
                        port.writeBytes("1".getBytes(), "1".getBytes().length);
                    }else{
                        port.writeBytes("0".getBytes(), "0".getBytes().length);
                    }
                }
            }
        } catch (SerialPortInvalidPortException | IOException e) {
            System.err.println(e);
        }

    }
}
