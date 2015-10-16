package SIP.Server;

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
    public State toWait(String msg){
        return new StateWaiting();
    }

    @Override
    public State toHangUp(){
        return new StateHangingUp();
    }
}
