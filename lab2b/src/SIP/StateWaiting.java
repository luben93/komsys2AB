package SIP;

/**
 * Created by Julia on 2015-10-13.
 */
public class StateWaiting extends State{
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.WAITING;
    }

    @Override
    public State receivedInvite() {
        return new StateRinging();
    }
}
