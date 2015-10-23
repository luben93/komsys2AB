package SIP.State;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * Created by Julia on 2015-10-13.
 */
public class StateWaiting extends State {
    private AudioStreamUDP asu;

    public StateWaiting() {
        try {
            asu = new AudioStreamUDP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.WAITING;
    }

    @Override
    public State toAnswer(BufferedReader in, PrintWriter out, InetAddress ip) throws StateException {
        try {
            String msg = in.readLine();
            if (in.readLine().startsWith("INVITE")) {
                int port_peer = Integer.parseInt(msg.substring(7));
                int port = asu.getLocalPort();
                out.println("100 TRYING " + port);
                asu.connectTo(ip, port_peer);
                out.println("180 RINGING");
                asu.startStreaming();
                out.println("200 OK");
                return new StateAnswer();
            }
            throw new StateException(msg + ", NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
        } catch (IOException e) {
            e.printStackTrace();
            throw new StateException("IO error, NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
        }
    }


    @Override
    public State toDial(PrintWriter out) {
        out.println("INVITE " + asu.getLocalPort());
        return new StateDialing(asu);
    }

}
