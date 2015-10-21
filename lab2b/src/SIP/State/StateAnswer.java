package SIP.State;

import SIP.SIPHandler;

/**
 * Created by Julia on 2015-10-16.
 */
public class StateAnswer extends State{
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.ANSWERING;
    }

    @Override
    public State toTalk(String msg){
        System.err.println("msg: "+msg);
        if(msg.contains("INVITE")){
            return new StateTalking();
        }
        return this;

    }
}
