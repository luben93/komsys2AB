package start;

import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by luben on 2015-10-13.
 */
public class SIPthread extends Thread {
    private Socket socket;
    boolean isServer;
    SIPHandler sh;


    public SIPthread(Socket socket, boolean isServer) throws IOException {
        this.socket = socket;
        sh = new SIPHandler();
        this.isServer = isServer;
    }

    public void run() {
        if (isServer) {
            server();
        } else {
            client();
        }
    }

    private void client() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out.println("INVITE");
            System.out.println("input: " + in.readLine());
            boolean tryingToStartCall = true;
            while (tryingToStartCall) {
                try {
                    if (in.readLine().equals("100 TRYING")) {
                        if (in.readLine().equals("180 RINGING")) {
                            if (in.readLine().equals("200 OK")) {
                                System.out.println("it worked!!!");
                                out.println("ACK");
                                tryingToStartCall = false;
                            }
                        }
                    }
                }catch (NumberFormatException e){
                    e.getMessage();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void server() {
        PrintWriter out = null;
        BufferedReader in = null;
        String output = "BUSY";
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (true) {
                String choice = in.readLine();
                if (sh.getState() == SIPHandler.StateEvent.WAITING) {

                /*switch (choice) {
                    case "INVITE":
                        output = sh.invokeReceivedInvite();
                        break;
                    case "ACK":
                        output = sh.invokeReceivedCall();
                        break;
                    case "BYE":
                        output = sh.invokeReceivedEndCall();
                        break;
                    case "HANGUP"://TODO revoke from outside
                        output = sh.invokeReceivedBye();
                        break;
                    default:
                        output = sh.errorExit();
                        break;
                }*/
                    if (choice.contains("INVITE")) {
                        output = sh.invokeReceivedInvite();
                    }

                }
                out.println(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}