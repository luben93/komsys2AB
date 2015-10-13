package SIP;

/**
 * Created by Julia on 2015-10-13.
 */
public class SIPHandler {
    public enum StateEvent {WAITING, RINGING, INSESSION, CLOSING}

    ;

    private State currentState;

    public SIPHandler() {
        currentState = new StateWaiting();
    }

    public StateEvent getState() {
        return currentState.getStateName();
    }

    public void invokeReceivedInvite() {
        currentState = currentState.receivedInvite();
    }

    public void invokeReceivedBye() {
        currentState = currentState.receivedBye();
    }

    public void invokeReceivedCall() {
        currentState = currentState.receivedCall();
    }

    public void invokeReceivedEndCall() {
        currentState = currentState.receivedEndCall();
    }

}

