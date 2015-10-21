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

    private void client() {
        PrintWriter out = null;
        BufferedReader in = null;
        String msg="";
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            //TODO start audio here, and get port number, using 57654 for now
            int localport=57654;
            out.println("INVITE " + localport);
            sh.outgoingCall();
           // boolean tryingToStartCall = true;
            while (sh.getState().equals(SIPHandler.StateEvent.DIALING)) {//?????
                try {
//                    System.out.println(in.readLine());
//                    System.out.println(in.readLine());
//                    System.out.println(in.readLine());
                   //*
                    msg=in.readLine();
                    if (msg.contains("100 TRYING")) {
                        int port=Integer.parseInt(msg.substring(11));
                        //TODO connect to port
                        msg=in.readLine();
                        if (msg.equals("180 RINGING")) {
                            msg=in.readLine();
                            if (msg.equals("200 OK")) {
                                System.out.println("it worked!!!");
                                sh.callAccepted("TRO");
                                out.println("ACK");
                                //tryingToStartCall = false;
                            }
                        }
                    }//*/
                }catch (NumberFormatException e){
                    e.getMessage();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            while (sh.getState().equals(SIPHandler.StateEvent.WAITING)) {//??????????
                String msg = in.readLine();
                sh.incomingCall(msg);
                //TODO start audio here, and get port number, using 65123 for now
                int port=65123;
                out.println("100 TRYING "+port);
                //TODO connect here
                out.println("180 RINGING");
                //TODO check all is correct
                out.println("200 OK");
                sh.pickUpCall(msg);
                //TODO get out of loop here
               // if (sh.getState() == SIPHandler.StateEvent.WAITING) {

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
                /*
                    if (choice.contains("INVITE")) {
                    //   out.println(sh.invokeReceivedInvite());
                        //TODO new audio thread here
                      //  out.println(sh.invokeReceivedCall());
                        out.println("200 OK");//TODO cast from audio thread and state
                    }

                }//*/
                //out.println(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
