package SIP;

/**
 * Created by Julia on 2015-10-13.
 */
public abstract class State {
    public abstract SIPHandler.StateEvent getStateName();

    public State receivedBye() {
        return this;
    }

    public State receivedInvite() {
        return this;
    }

    public State receivedCall() {
        return this;
    }

    public State receivedEndCall() {
        return this;
    }



}
