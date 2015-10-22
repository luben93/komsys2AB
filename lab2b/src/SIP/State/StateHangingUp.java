package SIP.State;

import SIP.SIPHandler;

/**
 * Created by Julia on 2015-10-16.
 */
public class StateHangingUp extends State{
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.HANGINGUP;
    }

    @Override
    public State toWait(String msg) throws StateException {
        if(msg.equals("200 OK")){
            return new StateWaiting();
        }
        throw new StateException("NOT RECEIVED 200 OK, FROM STATE HANG UP TO STATE WAITING");
    }
}
