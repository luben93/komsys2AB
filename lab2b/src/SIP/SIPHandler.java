package SIP;

import SIP.Server.State;
import SIP.Server.StateWaiting;

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

    public void incomingCall(String msg){
        currentState=currentState.toAnswer(msg);
    }

    public void outgoingCall(){
        currentState=currentState.toDial();
    }

    public void pickUpCall(String msg){
        currentState=currentState.toTalk(msg);
    }

    public void callAccepted(String msg){
        currentState=currentState.toTalk(msg);
    }

    public void hangUp(String msg){
        currentState=currentState.toWait(msg);
    }

    public void callEndedRemotely(){
        currentState=currentState.toHangUp();
    }

    public void callEndedLocaly(String msg){
        currentState=currentState.toWait(msg);
    }


}

