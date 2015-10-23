package SIP.State;

import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Julia on 2015-10-16.
 */
class StateHangingUp extends State {
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.HANGINGUP;
    }

    @Override
    public State toWait(BufferedReader in,PrintWriter out) throws StateException {
        try {
            String msg = in.readLine();
            if (msg.equals("200 OK")) {
                asu.stopStreaming();
                asu.close();
                return new StateWaiting();
            }else if (msg.equals("BYE")) {
                asu.stopStreaming();
                asu.close();
                out.println("201 OK");//but its not ok
                return new StateWaiting();
            }if (msg.equals("201 OK")) {
                asu.stopStreaming();
                asu.close();
                return new StateWaiting();
            }
            throw new StateException(msg + ", NOT RECEIVED 200 OK, FROM STATE HANG UP TO STATE WAITING");
        } catch (IOException e) {
            throw new StateException("IO error, NOT RECEIVED 200 OK, FROM STATE HANG UP TO STATE WAITING");
        }
    }
}
