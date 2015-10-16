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


    public SIPthread(Socket socket,boolean isServer) throws IOException{
        this.socket = socket;
        sh = new SIPHandler();
        this.isServer=isServer;
    }

    public void run() {
        if (isServer) {
        start();
        }else {
            //TODO start client protcol here
        }
    }

    private void server(){
        PrintWriter out = null;
        BufferedReader in = null;
        String output = "BUSY";
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (true) {
                if (sh.getState() == SIPHandler.StateEvent.WAITING) {
                    String choice = in.readLine();
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
