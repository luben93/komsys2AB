package SIP;

import SIP.State.State;
import SIP.State.StateException;
import SIP.State.StateWaiting;

/**
 * Created by Julia on 2015-10-13.
 */
public class SIPHandler {
    public enum StateEvent {WAITING, ANSWERING, DIALING, TALKING, HANGINGUP}

    private State currentState;

    public SIPHandler() {
        currentState = new StateWaiting();
    }

    public StateEvent getState() {
        return currentState.getStateName();
    }

    public void incomingCall(String msg) throws StateException {
        currentState=currentState.toAnswer(msg);
    }

    public void outgoingCall(){
        currentState=currentState.toDial();
    }

    public void pickUpCall(String msg) throws StateException {
        currentState=currentState.toTalk(msg);
    }

    public void callAccepted(String msg) throws StateException {
        currentState=currentState.toTalk(msg);
    }

    public void hangUp(String msg) throws StateException {
        currentState=currentState.toWait(msg);
    }

    public void callEnded(){
        currentState=currentState.toHangUp();
    }


}

