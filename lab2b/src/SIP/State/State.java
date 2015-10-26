package SIP.State;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;
import start.SIPthread;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Julia on 2015-10-13.
 */
public abstract class State {
//    AudioStreamUDP asu;
//    PrintWriter out;
//    BufferedReader in;
//    InetAddress ip;
//    Interface face;
//    Socket s;

    public abstract SIPHandler.StateEvent getStateName();

    public State toDial(PrintWriter out,AudioStreamUDP asu) {
        return this;
    }
    public State toAnswer(BufferedReader in, PrintWriter out, InetAddress ip, AudioStreamUDP asu, SIPthread server) throws StateException {
        return this;
    }
    public State toNotAnswer(BufferedReader in, PrintWriter out, Socket socket) throws StateException {
        return this;
    }
    public State toWaitForAnswer(BufferedReader in, PrintWriter out, InetAddress ip,AudioStreamUDP asu, SIPthread server) throws StateException {
        return this;
    }

    public State toTalk(BufferedReader b, PrintWriter p, InetAddress ip, AudioStreamUDP asu, Socket socket) throws StateException {
        return this;
    }

    public State toTalk(BufferedReader b) throws StateException {
        return this;
    }

    public State toHangUp(PrintWriter p) {
        return this;
    }

    public State toWait(BufferedReader b, PrintWriter p,AudioStreamUDP asu) throws StateException {
        return this;
    }

//    public State toWait() {
//        return this;
//    }

}
