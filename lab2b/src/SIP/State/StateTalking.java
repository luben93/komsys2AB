package SIP.State;

import SIP.SIPHandler;

/**
 * Created by Julia on 2015-10-16.
 */
public class StateTalking extends State{
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.TALKING;
    }

    @Override
    public State toWait(String msg) throws StateException {
        if(msg.equals("BYE")) {
            return new StateWaiting();
        }
        throw new StateException("NOT RECEIVED BYE, FROM STATE TALKING TO STATE WAITING");
    }

    @Override
    public State toHangUp(){
        return new StateHangingUp();
    }
}
