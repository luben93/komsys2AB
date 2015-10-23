package start;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;
import SIP.State.StateException;

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
    private boolean isServer;
    private PrintWriter out;
    private BufferedReader in;
    private SIPHandler sh;
    private String msg;
  //  private Interface face;
    private AudioStreamUDP asu;


    public SIPthread(Socket socket,SIPHandler sh) throws IOException {
        this.sh = sh;
        this.socket = socket;
//        this.face = face;
        this.isServer = isServer;
    }


    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            //asu = new AudioStreamUDP();
//            if (isServer) {
//                server();
//            } else {
//                 client();
//                call();
//
//            }
            sh.serverReady(in,out,socket.getInetAddress());
        } catch (NullPointerException e) {
//            face.showMessage("Something went wrong reseting");
            sh.forceWaiting();
//            asu.stopStreaming();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void hangUp() throws IOException, StateException {

        switch (sh.getState()) {
            case TALKING:
//                out.println("BYE");
                sh.callEnded();
                break;
            default:
                //TODO error handling
                throw new StateException("wrong state: " + sh.getState());
        }
    }

    private void hangUp(String inmsg) throws StateException {
//        face.showMessage(inmsg);
        switch (sh.getState()) {
            case TALKING:
                sh.hangUp(inmsg);
//                out.println("200 OK");
                //TODO turn off audio
                break;
            case HANGINGUP:
                sh.hangUp(inmsg);
                //TODO turn off audio
                //exit thread
                break;
            default:
                throw new StateException("wrong state: " + sh.getState() + "\n msg: " + inmsg);
        }
        try {
//            asu.stopStreaming();
//            asu.close();
//            out.close();
//            in.close();
            socket.close();
//            face.showMessage("\nCall ended\nType IP to call");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void call() throws Exception {
        while (true) {//???? or something else
//            face.showMessage("state: " + sh.getState());

            switch (sh.getState()) {
                case WAITING:
//                    out.println("INVITE " + asu.getLocalPort());
                    sh.outgoingCall();
                    break;
                case DIALING:
//                    msg = in.readLine();
//                    if (msg.contains("100 TRYING")) {
//                        int port = Integer.parseInt(msg.substring(11));
//                        asu.connectTo(socket.getInetAddress(), port);
//                        TODO connect to port
//                        msg = in.readLine();
//                        if (msg.equals("180 RINGING")) {
//
//                            msg = in.readLine();
//                            if (msg.equals("200 OK")) {
//                                face.showMessage("Press 0 enter to hang up");
                                sh.callAccepted("TRO");
//                                out.println("ACK");
//                                asu.startStreaming();
                                break;
                                //tryingToStartCall = false;
//                            }
//                        }
//                    }
//                    throw new Exception("SIP protocol ERROR");

                default:
                    hangUp();//in.readLine());
                    return;
            }

        }
    }


    private void server() throws Exception {
        while (true) {
            try {
//                msg = in.readLine();
                switch (sh.getState()) {
                    case WAITING:
                        sh.incomingCall(msg);
//                        int port_peer = Integer.parseInt(msg.substring(7));
//                        int port = asu.getLocalPort();
//                        out.println("100 TRYING " + port);
//                        asu.connectTo(socket.getInetAddress(), port_peer);
//                        out.println("180 RINGING");
//                        asu.startStreaming();
//                        out.println("200 OK");
                        //TODO check all is correct
//                        face.showMessage("call connected, press 0 enter to hang up");
                        break;
                    case ANSWERING:
                        sh.pickUpCall(msg);
                        break;

                    default:
                        hangUp(msg);
                        return;

                }
            } catch (StateException e) {
                //Visa meddelande till användare
                e.printStackTrace();
            }
        }
    }

}
