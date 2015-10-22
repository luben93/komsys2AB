package start;

import SIP.SIPHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Julia on 2015-10-13.
 */
public class Interface extends Thread {
    private SIPHandler sh;
    private SIPthread server;

    public Interface(SIPHandler sh, SIPthread server) {
        this.sh = sh;
        this.server = server;
    }

    @Override
    public void run() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        Socket s = null;
        SIPthread trad = server;
        String ip = "";
        // boolean call = false;
        do {
            showMessage("state: " + sh.getState());

            showMessage("type 0 to quit");
            //TODO: vänta på att personen ska svara och vänta på att starta upp audiostream
            switch (sh.getState()) {
                case WAITING:
                    showMessage("type IP to call");
                    break;
                case TALKING:
                    showMessage("press enter to hang up");
                    break;
                default:
                    break;
            }
            ip = scanner.nextLine();
            if (ip.equals("0")) {
                System.exit(0);
            }
            try {
                //choice = Integer.parseInt(sentence);
                switch (sh.getState()) {
                    case WAITING:
                        // showMessage("Write which ip you want to call.");
                        // String invite_msg = scanner.nextLine();
                        try {
                            s = new Socket(ip, 4321);
                            trad = new SIPthread(sh);
                            trad.init(s,this,false);
                            trad.start();
//                            trad.call();

                            showMessage("Calling ... ");
                            // sh.invokeReceivedInvite();

                            //call = true;
                        } catch (UnknownHostException e) {
                            showMessage("The ip you entered is not correct");
                            //  e.printStackTrace();
                        } catch (IOException e) {
                            showMessage("Could not connect to the ip adress");
                            //e.printStackTrace();
                        }
                        break;
                    case TALKING:
                        //TODO: tråden inte startad
                        sh.callEnded();
                        trad.hangUp();
                        trad=server;

                        break;
                }
            } catch (NumberFormatException e) {
                showMessage("You have to write 1 or 2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!ip.equals("0"));

        System.exit(0);
    }

    public synchronized void showMessage(String msg) {
        System.out.println(msg);
    }
}
