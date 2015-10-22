package SIP.State;

import SIP.SIPHandler;

/**
 * Created by Julia on 2015-10-13.
 */
public class StateWaiting extends State {
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.WAITING;
    }

    @Override
    public State toAnswer(String msg) throws StateException {
        if(msg.equals("INVITE")) {
            return new StateAnswer();
        }
        throw new StateException("NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
    }

    @Override
    public State toDial() {
        return new StateDialing();
    }

}
