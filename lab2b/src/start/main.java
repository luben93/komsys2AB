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
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        SIPHandler sh = new SIPHandler();
        int choice = -1;
        Scanner scan = new Scanner(System.in);

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
                            sh.invokeReceivedInvite(sh.getState()); break;
                        case 2:
                            sh.invokeReceivedCall(sh.getState()); break;
                        case 3:
                            sh.invokeReceivedEndCall(sh.getState()); break;
                        case 4:
                            sh.invokeReceivedBye(sh.getState()); break;
                        default:
                            out.println(sh.errorExit()); break;
                    }
                } while (choice != 0);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
