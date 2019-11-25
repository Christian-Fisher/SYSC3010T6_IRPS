import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPunicast implements Runnable  {

private final int clientPort;

    public UDPunicast(int clientPort) {
        this.clientPort = clientPort;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {


        try(DatagramSocket serverSocket = new DatagramSocket(50000)){
             for(int i =0 ; i < 3 ;  i++){
                 String message = "message" + i;
                 DatagramPacket datagramPacket = new DatagramPacket(

                    message.getBytes(),
                    message.length(),
                         InetAddress.getLocalHost(),
                         clientPort



                 );
                 serverSocket.send(datagramPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }












}
