package start;

import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Julia on 2015-10-13.
 */
public class Interface extends Thread {
    private SIPHandler sh;
    private SIPthread server;

    public Interface(SIPHandler sh) {
        this.sh = sh;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Socket s = null;
        String ip = "";
        PrintWriter out=null;
        boolean isClient = false;
//        SIPthread trad = null;

        do {
//            Scanner scanner = new Scanner(System.in);

            System.out.println("state: " + sh.getState());
            switch (sh.getState()) {
                case WAITING:
                    System.out.println("type IP to call\nor 0 to exit");
                    break;
                case TALKING:
                    System.out.println("press 0 enter to hang up");
                    break;
                default:
                    System.out.println("press 0 enter to reset");
                    break;
            }
            ip = scanner.nextLine();
            if(ip.equals("")){
                ip=" ";
            }
            try {
                switch (sh.getState()) {
                    case WAITING:
                        if(ip.equals("0")){
                            System.exit(0);
                        }
                        try {
                            s = new Socket(ip, 4321);
//                            trad = new SIPthread(s, sh);
//                            trad.start();
//                            isClient = true;
                            BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                            out=new PrintWriter(s.getOutputStream(),true);
                            sh.outgoingCall(in,out,s.getInetAddress());
                            sh.callAccepted(in,out);
                            System.out.println("Calling ... ");
                            isClient=true;
//                            System.out.println("press 0 enter to hang up");
//                            while(!ip.equals("0")){
//                                ip = scanner.nextLine();
//                                sh.hangUp(out);
//                            }
                        } catch (UnknownHostException e) {
                            System.out.println("The ip you entered is not correct");
                        } catch (IOException e) {
                            System.out.println("Could not connect to the ip adress");
                        }
                        break;
                    case TALKING:
                        if (ip.equals("0")) {
                            System.out.println("You have pressed hang up");
                            if (isClient) {
                                sh.hangUp(out);
                                isClient = false;
                            } else {
                                server.hangUp();
                            }
                        }
                        break;
                    default:
                        if (ip.equals("0")) {
                            sh.forceWaiting();
                        }
                }
            } catch (NumberFormatException e) {
                System.err.println("wrong selection");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }
//
    public synchronized void updateServer(SIPthread s) {
        server = s;
    }
//
//    public synchronized void showMessage(String msg) {
//        System.out.println(msg);
//    }
}
