package SIP.Server;

import SIP.SIPHandler;

/**
 * Created by Julia on 2015-10-16.
 */
public class StateDialing extends State{
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.DIALING;
    }

    @Override
    public State toTalk(String msg){
        if(msg.equals("TRO")) {//TODO logic dont fit here
            return new StateTalking();
        }
        return this;
    }
}
