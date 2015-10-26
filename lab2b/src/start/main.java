package start;
//TODO: package
/**
 * Created by Julia on 2015-10-12.
 */

import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class main {
    public static void main(String[] args) {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        SIPthread trad = null;

        try {
            SIPHandler sh = new SIPHandler();
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
                    if (sh.getState().equals(SIPHandler.StateEvent.WAITING)) {
                        trad = new SIPthread(clientSocket, sh, true);
                        trad.start();
                        interface_client.updateServer(trad);
                    } else {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("BUSY");
                        out.close();
                        clientSocket.close();
                    }

//                    interface_client.showMessage("thread started to: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                trad.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
