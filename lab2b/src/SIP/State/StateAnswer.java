package SIP.State;

import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Julia on 2015-10-16.
 */
class StateAnswer extends State {
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.ANSWERING;
    }

    @Override
    public State toTalk(BufferedReader in) throws StateException {
        try {
            String msg = in.readLine();
            if (msg.equals("ACK")) {
                return new StateTalking();
            }
            throw new StateException(msg + ", NOT RECEIVED INVITE, FROM STATE ANSWER TO STATE TALKING");
        } catch (IOException e) {
            throw new StateException("IO error, NOT RECEIVED INVITE, FROM STATE ANSWER TO STATE TALKING");
        }
    }
}
