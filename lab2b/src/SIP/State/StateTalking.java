package SIP.State;

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
    public State toWait(BufferedReader in, PrintWriter out) throws StateException {
        try {
            String msg = in.readLine();
            System.out.println(msg);
            if (msg.equals("BYE")) {
                asu.stopStreaming();
                out.println("200 OK");
                asu.close();
                return new StateWaiting();
            }

            throw new StateException(msg + ", NOT RECEIVED BYE, FROM STATE TALKING TO STATE WAITING");
        } catch (IOException e) {
            throw new StateException("IO execp, NOT RECEIVED BYE, FROM STATE TALKING TO STATE WAITING");

        }
    }

    @Override
    public State toHangUp(PrintWriter out) {
        out.println("BYE");
        return new StateHangingUp();
    }
}
