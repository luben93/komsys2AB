package SIP.State;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Julia on 2015-10-16.
 */
class StateTalking extends State {
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.TALKING;
    }

    @Override
    public State toWait(BufferedReader in, PrintWriter out, AudioStreamUDP asu) throws StateException {
        try {
            String msg = in.readLine();
            System.out.println(msg);
            if (msg.equals("BYE")) {
                asu.stopStreaming();
                out.println("200 OK");
//                asu.close();
                return new StateWaiting();
            } else if (msg.equals("200 OK")) {
                asu.stopStreaming();
                return new StateWaiting();
            }

            throw new StateException(msg + ", NOT RECEIVED BYE, FROM STATE TALKING TO STATE WAITING");
        } catch (IOException e) {
            System.err.println(e.getMessage() + "IO execp, NOT RECEIVED BYE, FROM STATE TALKING TO STATE WAITING");
            asu.stopStreaming();
//            throw new StateException("IO execp, NOT RECEIVED BYE, FROM STATE TALKING TO STATE WAITING");
            return new StateWaiting();
        }
    }

    @Override
    public State toHangUp(PrintWriter out) {
        out.println("BYE");
        return new StateHangingUp();
    }
}
