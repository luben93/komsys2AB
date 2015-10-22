package start;

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
    private Interface face;


    public SIPthread( SIPHandler sh) throws IOException {
        this.sh = sh;
    }

    public void init(Socket socket, Interface face, Boolean isServer){
        this.socket = socket;
        this.face = face;
        this.isServer = isServer;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            if (isServer) {
                server();
            } else {
                // client();
                call();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void hangUp() throws StateException {
        face.showMessage("hangUp start");
        // msg=in.readLine();
        switch (sh.getState()) {
            case HANGINGUP:
                out.println("BYE");

                try {
                    face.showMessage("hangUp try");
                    face.showMessage(msg = in.readLine());
                    sh.hangUp(msg);
                    out.close();
                    in.close();
                    socket.close();
                    face.showMessage("hangUp try done");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            default:
                System.err.println("i'm sorry dave i'm afraid i can't do that");
                throw new StateException("Can't end call now");
        }
    }

    public void call() throws Exception {
        face.showMessage("state: " + sh.getState());

        while (!sh.getState().equals(SIPHandler.StateEvent.TALKING)) {//???? or something else
            switch (sh.getState()) {
                case WAITING:
                    face.showMessage("sending invite");
                    //TODO start audio here, and get port number, using 57654 for now
                    //int localport = 57654;
                    out.println("INVITE 57654");// + localport);
                    face.showMessage("sending INVITE sent ");

                    sh.outgoingCall();

                    break;
                case DIALING:
                    face.showMessage(msg = in.readLine());
                    if (msg.contains("100 TRYING")) {
                        int port = Integer.parseInt(msg.substring(11));
                        //TODO connect to port
                        face.showMessage(msg = in.readLine());
                        if (msg.equals("180 RINGING")) {
                            face.showMessage(msg = in.readLine());
                            if (msg.equals("200 OK")) {
                                face.showMessage("Press enter to hang up");
                                sh.callAccepted("TRO");
                                out.println("ACK");
                                return;
                                //tryingToStartCall = false;
                            }
                        }
                    }
                    throw new Exception("SIP protocol ERROR");

                default:
                    throw new Exception("not waiting or dialing");

            }

        }
    }

    private void server() throws Exception {
        face.showMessage("server waiting for INVITE");
        while (true) {
            try {
                msg = in.readLine();
                face.showMessage(msg);

                switch (sh.getState()) {
                    case WAITING:
                        sh.incomingCall(msg);
                        //TODO start audio here, and get port number, using 65123 for now
                        int port = 65123;
                        out.println("100 TRYING " + port);
                        //TODO connect here
                        out.println("180 RINGING");
                        //TODO check all is correct
                        out.println("200 OK");
                        //TODO get out of loop here
                        face.showMessage("call connected, press enter to hang up");
                        break;
                    case ANSWERING:
                        sh.pickUpCall(msg);
                        break;
                    case TALKING:
                        sh.hangUp(msg);
                        //TODO quit server
                        face.showMessage("call ended by other party");
                        out.println("200 OK");
                        break;
                    default:
                        out.println("ERROR 500");
                        throw new Exception("ERROR 500");

                }
            } catch (StateException e) {
                //Visa meddelande till användare
                e.printStackTrace();
            }
        }
    }

}
