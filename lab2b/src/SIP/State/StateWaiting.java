package SIP.State;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Julia on 2015-10-13.
 */
public class StateWaiting extends State {


    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.WAITING;
    }

    @Override
    public State toAnswer(BufferedReader in, PrintWriter out, InetAddress ip,AudioStreamUDP asu) throws StateException {
        try {

            Scanner scanner = new Scanner(System.in);
            String msg = in.readLine();
            System.out.println(msg);
            if (msg.startsWith("INVITE")) {
                System.out.println("You have got an incoming call from " + ip.getHostName() + " do you want to answer? ");
                System.out.println("y or n ?");
                String s = scanner.nextLine();
                if(s.equals("y")){
                    int port_peer = Integer.parseInt(msg.substring(7));
                    int port = asu.getLocalPort();
                    out.println("100 TRYING " + port);
                    System.out.println("100");
                    asu.connectTo(ip, port_peer);
                    out.println("180 RINGING");
                    System.out.println("180");
                    asu.startStreaming();
                    out.println("200 OK");
                    System.out.println("200");
                    return new StateAnswer();
                }else{
                    in.close();
                    out.close();
                    System.out.println("type IP to call\nor 0 to exit");
                    return new StateWaiting();
                }
            }
            throw new StateException(msg + ", NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
        } catch (IOException e) {
            e.printStackTrace();
            throw new StateException("IO error, NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
        }
    }


    @Override
    public State toDial(PrintWriter out,AudioStreamUDP asu) {
//        while (true) {
//        Scanner scanner = new Scanner(System.in);
//            System.out.println("type the IP to call\n or 0 to exit");
//            String ip = scanner.nextLine();
//            if (ip.equals("")) {
//                ip = " ";
//            } else if (ip.equals("0")) {
//                System.exit(0);
//            }
//            try {
//                Socket cli = new Socket(ip, 4321);
//
//                PrintWriter out = new PrintWriter(cli.getOutputStream());
        out.println("INVITE " + asu.getLocalPort());
        return new StateDialing();
//            } catch (UnknownHostException e) {
//                System.err.println("The ip you entered is not correct");
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
    }

}
