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

    public Interface(SIPHandler sh) {
        this.sh = sh;
        // this.server = server;
    }

    @Override
    public void run() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        Socket s = null;
//        SIPthread trad =null;
        String ip = "";
        // boolean call = false;
        boolean isClient = false;
        SIPthread trad = null;

        do {
            showMessage("state: " + sh.getState());


            //TODO: vänta på att personen ska svara och vänta på att starta upp audiostream
            switch (sh.getState()) {
                case WAITING:
                    showMessage("type IP to call");
                    break;
                case TALKING:
                    showMessage("press 0 enter to hang up");
                    break;
                default:
                    break;
            }
            ip = scanner.nextLine();
            if(ip.equals("")){
                ip=" ";
            }
            try {
                //choice = Integer.parseInt(sentence);
                switch (sh.getState()) {

                    case WAITING:
                        // showMessage("Write which ip you want to call.");
                        // String invite_msg = scanner.nextLine();
                        try {
                            s = new Socket(ip, 4321);
                            trad = new SIPthread(sh, s, this, false);
                            trad.start();
                            isClient = true;
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
                        if (ip.equals("0")) {
                            showMessage("You have pressed hang up");
                            if (isClient) {
                                //TODO: tråden inte startad
                                trad.hangUp();//TODO BOOLEAN
                                isClient = false;
                            } else {
                                server.hangUp();
                            }
                        }
                        break;
                    case HANGINGUP:
                        break;
                }
            } catch (NumberFormatException e) {
                showMessage("You have to write 1 or 2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
        //TODO exit correctly
        // System.exit(0);
    }

    public synchronized void updateServer(SIPthread s) {
        server = s;
    }

    public synchronized void showMessage(String msg) {
        System.out.println(msg);
    }
}
