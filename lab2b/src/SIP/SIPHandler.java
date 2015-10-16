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

    public String invokeReceivedInvite() {
        if(getState().equals(StateEvent.WAITING)) {
            currentState = currentState.receivedInvite();
            return "100 TRYING";
            //TODO start new audio thread here
        }
        return errorExit();

    }

    public String invokeReceivedBye() {
        if(getState().equals(StateEvent.RINGING)) {
            currentState = currentState.receivedBye();
            return "200 OK";
            //TODO call from audio thread here
        }
        return errorExit();

    }

    public String invokeReceivedCall() {
        if (getState().equals(StateEvent.INSESSION)) {
            currentState = currentState.receivedCall();

            return "180 RINGING";

            //i dont even
        }
        return errorExit();

    }

    public String invokeReceivedEndCall() {
        if (getState().equals(StateEvent.CLOSING)) {
            currentState = currentState.receivedEndCall();
            return "BYE";
            //TODO close audio thread
        }
        return errorExit();

    }

    public String errorExit(){
        currentState=currentState.receivedBye();
        return "418 i'm a teapot";
    }

}

