package SIP.Server;

import SIP.SIPHandler;

/**
 * Created by Julia on 2015-10-13.
 */
public abstract class State {
    public abstract SIPHandler.StateEvent getStateName();

    public State toDial() {
        return this;
    }

    public State toAnswer(String msg) {
        return this;
    }

    public State toTalk(String msg) {
        return this;
    }

    public State toHangUp() {
        return this;
    }

    public State toWait(String msg) {
        return this;
    }

//    public State toWait() {
//        return this;
//    }

}
