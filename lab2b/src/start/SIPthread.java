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


    public SIPthread(Socket socket,boolean isServer,SIPHandler sh) throws IOException{
        this.socket = socket;
        this.sh = sh;
        this.isServer=isServer;
    }

    public void run() {
        if (isServer) {
            server();
        }else {
            client();
        }
    }

    private void client(){
        //TODO start client protcol here
    }

    private void server(){
        PrintWriter out = null;
        BufferedReader in = null;
        String output = "BUSY";
        System.out.println("starting server at port: "+socket.getPort());
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
                       out.println(sh.invokeReceivedInvite());
                        //TODO new audio thread here
                        out.println(sh.invokeReceivedCall());
                        out.println("200 OK");//TODO cast from audio thread and state
                    }

                }
                //out.println(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
