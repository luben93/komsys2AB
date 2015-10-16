package SIP.Server;

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
        return new StateAnswer();
    }

    @Override
    public State toDial() {
        return new StateDialing();
    }

}
