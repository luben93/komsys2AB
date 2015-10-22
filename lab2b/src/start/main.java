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
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            SIPHandler sh = new SIPHandler();
            int choice = -1;
            SIPthread trad = null;

            Interface interface_client = new Interface(sh);
            interface_client.start();

            try {
                serverSocket = new ServerSocket(4321);
            } catch (IOException e) {
                System.err.println("Could not listen on port: " + 4321);
                System.exit(1);
            }

            System.out.println("Waiting for connection.....");

            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    trad = new SIPthread(sh, clientSocket, interface_client, true);
                    trad.start();
                    interface_client.updateServer(trad);

                    interface_client.showMessage("thread started to: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
