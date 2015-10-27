package SIP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import SIP.State.StateException;
import start.SIPthread;

/**
 * Created by Julia on 2015-10-27.
 */
public class waitForBye extends Thread {
    SIPHandler sip;
    BufferedReader b;
    PrintWriter p;

    public waitForBye(SIPHandler sip, BufferedReader b, PrintWriter p) {
        this.sip = sip;
        this.b = b;
        this.p = p;
    }

    public void run() {
        try {
            sip.callAccepted(b, p);
        } catch (StateException e) {
            e.printStackTrace();
        }
    }

    public synchronized void hangUp() {
        try {
            sip.hangUp(p,b);
        } catch (StateException e) {
            e.printStackTrace();
        }
        System.out.println("CLOSE??");
     //   close();
    }

    public synchronized void close() {
        try {
            System.out.println("closing");
            b.close();
            p.close();
         //   socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
