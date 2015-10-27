package SIP.State;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by Julia on 2015-10-13.
 */
public class StateWaiting extends State {


    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.WAITING;
    }

    @Override
    public State toAnswer(BufferedReader in, PrintWriter out, Socket s, AudioStreamUDP asu) throws StateException {
        while (true) {
            try {
                String msg = "";
                try {
                    msg = in.readLine();
                    System.out.println(msg);
                } catch (SocketTimeoutException e) {

                }

                if (msg.startsWith("INVITE")) {
                    int port_peer = Integer.parseInt(msg.substring(7));
                    int port = asu.getLocalPort();
                    out.println("100 TRYING " + port);
                    System.out.println("100");
                    asu.connectTo(s.getInetAddress(), port_peer);
                    out.println("180 RINGING");
                    System.out.println("180");
                    asu.startStreaming();
                    out.println("200 OK");
                    System.out.println("200");
                    return new StateAnswer();
                }
                throw new StateException(msg + ", NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
            } catch (IOException e) {
                e.printStackTrace();
                throw new StateException("IO error, NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
            }
        }
    }

    @Override
    public State toDial(PrintWriter out, AudioStreamUDP asu) {
        out.println("INVITE " + asu.getLocalPort());

        return new StateDialing();
    }

}
