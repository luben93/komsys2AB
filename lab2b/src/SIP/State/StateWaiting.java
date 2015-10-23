package SIP.State;

import SIP.AudioStreamUDP;
import SIP.SIPHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * Created by Julia on 2015-10-13.
 */
public class StateWaiting extends State {
    private AudioStreamUDP asu;

    public StateWaiting() {
        try {
            asu = new AudioStreamUDP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.WAITING;
    }

    @Override
    public State toAnswer(BufferedReader in, PrintWriter out, InetAddress ip) throws StateException {
        try {

            String msg = in.readLine();
            if (in.readLine().startsWith("INVITE")) {
                int port_peer = Integer.parseInt(msg.substring(7));
                int port = asu.getLocalPort();
                out.println("100 TRYING " + port);
                asu.connectTo(ip, port_peer);
                out.println("180 RINGING");
                asu.startStreaming();
                out.println("200 OK");
                return new StateAnswer();
            }
            throw new StateException(msg + ", NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
        } catch (IOException e) {
            e.printStackTrace();
            throw new StateException("IO error, NOT RECEIVED INVITE, FROM STATE WAITING TO STATE ANSWER");
        }
    }


    @Override
    public State toDial(PrintWriter out) {
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
                return new StateDialing(asu);
//            } catch (UnknownHostException e) {
//                System.err.println("The ip you entered is not correct");
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
    }

}
