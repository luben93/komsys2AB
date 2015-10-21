package SIP;

import start.SIPthread;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Julia on 2015-10-13.
 */
public class Interface extends Thread {
    private SIPHandler sh;

    public Interface(SIPHandler sh) {
        this.sh = sh;
    }

    @Override
    public void run() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        boolean call = false;
        do {
            //TODO should be checking current state
            showMessage("0. Quit");
            //TODO: vänta på att personen ska svara och vänta på att starta upp audiostream
            showMessage("1. Call");
            if (call) {
                showMessage("2. Hang upp");
            }
            //TODO: sent bye
            String sentence = scanner.nextLine();
            try {
                choice = Integer.parseInt(sentence);
                switch (choice) {
                    case 1:
                        showMessage("Write which ip you want to call.");
                        String invite_msg = scanner.nextLine();
                        try {
                            Socket s = new Socket(invite_msg, 4321);
                            SIPthread trad = new SIPthread(s, false, sh);
                            trad.start();
                            showMessage("Calling ... ");
                           // sh.invokeReceivedInvite();
                            call = true;
                        } catch (UnknownHostException e) {
                            showMessage("The ip you entered is not correct");
                            //  e.printStackTrace();
                        } catch (IOException e) {
                            showMessage("Could not connect to the ip adress");
                            //e.printStackTrace();
                        }
                        break;
                    case 2:
                        if (call) {
                            //sh.invokeReceivedEndCall();
                            call = false;
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                showMessage("You have to write 1 or 2");
            }
        } while (choice != 0);
        System.exit(0);
    }

    public synchronized void showMessage(String msg) {
        System.out.println(msg);
    }
}
