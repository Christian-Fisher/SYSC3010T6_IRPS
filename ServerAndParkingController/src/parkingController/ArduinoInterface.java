/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingController;

import com.fazecast.jSerialComm.*;
import java.util.Arrays;

/**
 *
 * @author Christian Fisher
 */
public class ArduinoInterface {

    public static void main(String[] args) {

        SerialPort port = SerialPort.getCommPort("ENTER PORT NAME HERE");
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 200, 200);
        port.setBaudRate(9600);

        try {
            while (true) {
                byte[] readBuffer = new byte[1024];
                port.readBytes(readBuffer, readBuffer.length);
                System.out.println(Arrays.toString(readBuffer));
            }
        }catch(Exception e){
            System.err.println(e);
        }
            

    }
}
