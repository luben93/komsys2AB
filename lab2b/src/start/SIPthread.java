package start;

import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by luben on 2015-10-13.
 */
public class SIPthread extends Thread {
    private Socket socket;
    private SIPHandler sh;
    private PrintWriter out;
    private BufferedReader in;
    private boolean server;


    public SIPthread(Socket socket, SIPHandler sh,boolean server) throws IOException {
        this.sh = sh;
        this.socket = socket;
        this.server=server;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            if(server) {
                sh.serverReady(in, out, socket.getInetAddress());
            }else{
                sh.outgoingCall(in, out,socket.getInetAddress());
            }
            sh.callAccepted(in, out);
            System.out.println("press 0 enter to hang up");
        } catch (NullPointerException e) {
            try {
                sh.forceWaiting();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void hangUp() {
        sh.hangUp(out);
        close();
    }

    public synchronized void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
