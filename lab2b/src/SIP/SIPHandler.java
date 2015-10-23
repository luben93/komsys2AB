package SIP;

import SIP.State.State;
import SIP.State.StateException;
import SIP.State.StateWaiting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * Created by Julia on 2015-10-13.
 */
public class SIPHandler {
    public enum StateEvent {WAITING, ANSWERING, DIALING, TALKING, HANGINGUP}

    private State currentState;

    public SIPHandler() throws IOException {
        currentState = new StateWaiting();
    }

    public StateEvent getState() {
        return currentState.getStateName();
    }

    /* public void incomingCall(BufferedReader in,PrintWriter out,InetAddress ip) throws IOException,StateException {
         currentState=currentState.toAnswer(in,out,ip);
     }
 */
    public void outgoingCall(BufferedReader b, PrintWriter out, InetAddress ip) throws StateException {
        currentState = currentState.toDial(out);
        currentState = currentState.toTalk(b, out, ip);
//        currentState=currentState.toWait(b,out);
    }

    /*
        public void pickUpCall(BufferedReader b) throws StateException {
            currentState=currentState.toTalk(b);
        }
    */
    public void callAccepted(BufferedReader in, PrintWriter out) throws StateException {
//        currentState=currentState.toTalk(in,out);
        currentState = currentState.toWait(in, out);
    }

    /*
        public void hangUp(BufferedReader b) throws StateException {
            currentState=currentState.toWait(b);
        }
    */
    public void hangUp(PrintWriter p) {
        currentState = currentState.toHangUp(p);
    }

    public void forceWaiting() {
        currentState = new StateWaiting();
    }

    public void serverReady(BufferedReader b, PrintWriter p, InetAddress ip) throws StateException {
        currentState = currentState.toAnswer(b, p, ip);
        currentState = currentState.toTalk(b);
//        currentState=currentState.toWait(b,p);
    }

}

