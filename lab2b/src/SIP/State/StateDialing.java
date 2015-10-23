package SIP.State;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * Created by Julia on 2015-10-16.
 */
class StateDialing extends State {
    private AudioStreamUDP asu;

    public StateDialing(AudioStreamUDP a) {
        asu=a;
    }
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.DIALING;
    }


    @Override
    public State toTalk(BufferedReader in,PrintWriter out, InetAddress ip) throws StateException {
        try {
            String msg = in.readLine();

            if (msg.contains("100 TRYING")) {

                int port = Integer.parseInt(msg.substring(11));
                asu.connectTo(ip, port);
                msg = in.readLine();
                if (msg.equals("180 RINGING")) {

                    msg = in.readLine();
                    if (msg.equals("200 OK")) {
                        System.out.println("Press 0 enter to hang up");
                        out.println("ACK");
                        asu.startStreaming();
                        return new StateTalking();

                    }
                }
            }
            throw new StateException(msg + " TRO failed");

        } catch (IOException e) {
            e.printStackTrace();
            throw new StateException("IO error");
        }
    }
}
