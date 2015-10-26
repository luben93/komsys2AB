package start;

import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
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
        PrintWriter out = null;
        BufferedReader in = null;
        boolean isClient = false;
        SIPthread trad = null;

        do {
            System.out.println("state: " + sh.getState());
            switch (sh.getState()) {
                case WAITING:
                    System.out.println("type IP to call\nor 0 to exit");
                    break;
                case TALKING:
                    System.out.println("press 0 enter to hang up ff");
                    break;
                default:
                    System.out.println("press 0 enter to reset");
                    break;
            }
            System.out.println(" IP BEFORE ");
            ip = scanner.nextLine();
            System.out.println(" IPPPPP " + ip);
            if (ip.equals("")) {
                ip = " ";
            }
            try {
                switch (sh.getState()) {
                    case WAITING:
                        if (ip.equals("n")) {
                            server.no();
                        } else if (ip.equals("y")) {
                            server.yes();
                            System.out.println(" SSSSSSSSSSSSSS + " + sh.getState());
                        }else if (ip.equals("0")) {
                            System.exit(0);
                        }else{
                            try {
                                s = new Socket(ip, 4321);
                                trad = new SIPthread(s, sh, false);
                                trad.start();
                                isClient = true;
                            } catch (UnknownHostException e) {
                                System.out.println("The ip you entered is not correct");
                            } catch (IOException e) {
                                System.out.println("Could not connect to the ip adress");
                            }
                        }



                        System.out.println("break loop");
                        break;
                    case TALKING:
                        if (ip.equals("0")) {
                            System.out.println("You have pressed hang up");
                            if (isClient) {
                                trad.hangUp();
                                s.close();
                            } else {
                                server.hangUp();
                            }
                        }
                        break;
                    default:
                        if (ip.equals("0")) {
                            sh.forceWaiting();
                        }
                        break;
                }
                System.out.println("State: " + sh.getState());
            } catch (NumberFormatException e) {
                System.err.println("wrong selection");
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        while (true);
    }

    public synchronized void updateServer(SIPthread s) {
        server = s;
    }
}