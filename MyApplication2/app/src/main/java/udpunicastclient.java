import android.os.Build;

import androidx.annotation.RequiresApi;

import java.net.DatagramSocket;
import java.net.SocketException;

public class udpunicastclient implements Runnable  {

private final int port;

    public udpunicastclient(int port) {
        this.port = port;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        try(DatagramSocket clientSocket = new DatagramSocket(port)){


        }catch (SocketException e ){

            e.printStackTrace();
        }

    }
}
