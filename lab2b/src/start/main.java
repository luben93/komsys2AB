package start;
//TODO: package
/**
 * Created by Julia on 2015-10-12.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import SIP.Interface;
import SIP.SIPHandler;

public class main {
    public static void main(String[] args) {
        SIPHandler sh = new SIPHandler();
        int choice = -1;
        Interface i = new Interface();
        i.run();


        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + 1234);
            System.exit(1);
        }
        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");
        PrintWriter out = null;
        BufferedReader in = null;
        while (true) {

            try {
                clientSocket = serverSocket.accept();
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                do {
                    System.out.println("State: " + sh.getState());
                    System.out.println("0. Quit");
                    System.out.println("1. Call");
                    System.out.println("2. Pick up");
                    System.out.println("3. Hang up");
                    System.out.println("4. Accept up");
                    String input;
                    input = in.readLine();
                    choice = Integer.parseInt(input);

                    switch (choice) {
                        case 1:
                            if (sh.getState().equals(SIPHandler.StateEvent.WAITING)) {
                                sh.invokeReceivedInvite();
                            } else {
                                out.println("ERROR 418");
                            }
                            break;
                        case 2:
                            if (sh.getState().equals(SIPHandler.StateEvent.RINGING)) {
                                sh.invokeReceivedCall();
                            } else {
                                out.println("ERROR 418");
                            }
                            break;
                        case 3:
                            if (sh.getState().equals(SIPHandler.StateEvent.INSESSION)) {
                                sh.invokeReceivedEndCall();
                            } else {
                                out.println("ERROR 418");
                            }
                            break;
                        case 4:
                            if (sh.getState().equals(SIPHandler.StateEvent.CLOSING)) {
                                sh.invokeReceivedBye();
                            } else {
                                out.println("ERROR 418");
                            }
                            break;
                    }
                } while (choice != 0);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
