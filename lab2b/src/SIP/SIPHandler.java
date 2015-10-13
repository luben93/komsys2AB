package SIP;

import SIP.Server.State;
import SIP.Server.StateWaiting;

/**
 * Created by Julia on 2015-10-13.
 */
public class SIPHandler {
    public enum StateEvent {WAITING, RINGING, INSESSION, CLOSING}


    private State currentState;

    public SIPHandler() {
        currentState = new StateWaiting();
    }

    public StateEvent getState() {
        return currentState.getStateName();
    }

    public String invokeReceivedInvite(StateEvent s) {
        if(s.equals(StateEvent.WAITING)) {
            currentState = currentState.receivedInvite();
            return "100 trying";
        }
        return errorExit();

    }

    public String invokeReceivedBye(StateEvent s) {
        if(s.equals(StateEvent.RINGING)) {
            currentState = currentState.receivedBye();
            return "180 ringing";
        }
        return errorExit();

    }

    public String invokeReceivedCall(StateEvent s) {
        if (s.equals(StateEvent.INSESSION)) {
            currentState = currentState.receivedCall();
            return "200 OK";
        }
        return errorExit();

    }

    public String invokeReceivedEndCall(StateEvent s) {
        if (s.equals(StateEvent.CLOSING)) {
            currentState = currentState.receivedEndCall();
            return "ACK bye";
        }
        return errorExit();

    }

    public String errorExit(){
        currentState=currentState.receivedBye();
        return "418 i'm a teapot";
    }

}

