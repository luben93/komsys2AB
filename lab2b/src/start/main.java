package start;
//TODO: package
/**
 * Created by Julia on 2015-10-12.
 */

import SIP.SIPHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class main {
    public static void main(String[] args) {
        SIPHandler sh = new SIPHandler();
        int choice = -1;
        Interface interface_client = new Interface(sh);
        interface_client.start();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4321);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + 4321);
            System.exit(1);
        }
        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                SIPthread trad=new SIPthread(clientSocket,true,sh,interface_client);
                trad.start();
                interface_client.showMessage("thread started to: "+clientSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //out.println(output);

            }


        }
    }
}
