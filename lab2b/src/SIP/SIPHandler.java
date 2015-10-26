package SIP;

import SIP.State.State;
import SIP.State.StateException;
import SIP.State.StateWaiting;
import start.SIPthread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Julia on 2015-10-13.
 */
public class SIPHandler {
    public enum StateEvent {WAITING, ANSWERING, DIALING, TALKING, HANGINGUP}
    private AudioStreamUDP asu;
    private State currentState;

    public SIPHandler() throws IOException {
        currentState = new StateWaiting();
        asu=new AudioStreamUDP();
    }

    public StateEvent getState() {
        return currentState.getStateName();
    }

    /* public void incomingCall(BufferedReader in,PrintWriter out,InetAddress ip) throws IOException,StateException {
         currentState=currentState.toAnswer(in,out,ip);
     }
 */
    public void outgoingCall(BufferedReader b, PrintWriter out, InetAddress ip,Socket socket) throws StateException {
        System.out.println("to dial");
        currentState = currentState.toDial(out,asu);
        System.out.println("to talk");
        currentState = currentState.toTalk(b, out, ip,asu,socket);
//        currentState=currentState.toWait(b,out);
    }

    /*
        public void pickUpCall(BufferedReader b) throws StateException {
            currentState=currentState.toTalk(b);
        }
    */
    public void callAccepted(BufferedReader in, PrintWriter out) throws StateException {
//        currentState=currentState.toTalk(in,out);
        System.out.println("to wait");
        currentState = currentState.toWait(in, out, asu);
    }

    /*
        public void hangUp(BufferedReader b) throws StateException {
            currentState=currentState.toWait(b);
        }
    */
    public void hangUp(PrintWriter p) {
        System.out.println("to hang up");
        currentState = currentState.toHangUp(p);
    }

    public void forceWaiting() throws IOException {
        asu.close();
        asu=new AudioStreamUDP();
        currentState = new StateWaiting();

    }

    public void serverReady(BufferedReader b, PrintWriter p, InetAddress ip, SIPthread s) throws StateException {
        System.out.println("to answer");
        currentState = currentState.toWaitForAnswer(b, p, ip, asu,s);
       /* System.out.println("to talk");
        currentState = currentState.toTalk(b);*/
//        currentState=currentState.toWait(b,p);
    }

    public void notAnswer(BufferedReader b, PrintWriter p, Socket socket) throws StateException {
        System.out.println("to not answer");
        currentState = currentState.toNotAnswer(b, p, socket);
    }


    public void answer(BufferedReader b, PrintWriter p, InetAddress ip, SIPthread s) throws StateException {
        System.out.println("to answer");
        currentState = currentState.toAnswer(b, p, ip, asu,s);
        System.out.println("to talk");
        currentState = currentState.toTalk(b);
//        currentState=currentState.toWait(b,p);
    }



}

