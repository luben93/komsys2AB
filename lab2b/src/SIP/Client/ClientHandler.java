package SIP.Client;

import SIP.Server.StateWaiting;

/**
 * Created by Julia on 2015-10-13.
 */
public class ClientHandler {
    public enum StateEvent {INVITE, BYE}

    private StateClient currentState;

    public ClientHandler() {
        currentState = new sentBye();
    }

    public StateEvent getState() {
        return currentState.getStateName();
    }

    public String invokeSentInvite(StateEvent s) {
        if (s.equals(StateEvent.BYE)) {
            currentState = currentState.sentInvite();
            return "Invite";
            //TODO start new audio thread here
        }
        return errorExit();

    }

    public String invokeSentBye(StateEvent s) {
        if (s.equals(StateEvent.INVITE)) {
            currentState = currentState.sentEnd();
            return "Bye";
            //TODO start new audio thread here
        }
        return errorExit();

    }

    public String errorExit() {
        currentState = currentState.sentEnd();
        return "418 i'm a teapot";
    }

}
