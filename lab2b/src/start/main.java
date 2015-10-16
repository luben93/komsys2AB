package start;
//TODO: package
/**
 * Created by Julia on 2015-10-12.
 */

import SIP.Interface;
import SIP.SIPHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class main {
    public static void main(String[] args) {
        SIPHandler sh = new SIPHandler();
        int choice = -1;
        Runnable interface_client = new Interface();
        Thread t = new Thread(interface_client);
        t.start();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4321);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + 4321);
            System.exit(1);
        }
        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");
       // PrintWriter out = null;
        //BufferedReader in = null;
        while (true) {
            String output = "error 418";
            try {

                clientSocket = serverSocket.accept();
                SIPthread trad=new SIPthread(clientSocket,true,sh);
                trad.start();
                System.out.println("thread started");
               /*
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (sh.getState() != SIPHandler.StateEvent.INSESSION) {

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
                                output = sh.invokeReceivedInvite();
                                break;
                            case 2:
                                output = sh.invokeReceivedCall();
                                break;
                            case 3:
                                output = sh.invokeReceivedEndCall();
                                break;
                            case 4:
                                output = sh.invokeReceivedBye();
                                break;
                            default:
                                output = sh.errorExit();
                                break;
                        }

                    } while (choice != 0);
                }*/
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //out.println(output);

            }


        }
    }
}
