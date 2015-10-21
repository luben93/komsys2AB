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
    public State toAnswer(String msg) {
        if(msg.equals("INVITE")) {
            return new StateAnswer();
        }
        return this;

    }

    @Override
    public State toDial() {
        return new StateDialing();
    }

}
