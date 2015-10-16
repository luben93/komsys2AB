package SIP;

import java.util.Scanner;

import SIP.Client.ClientHandler;

/**
 * Created by Julia on 2015-10-13.
 */
public class Interface extends Thread {
    public Interface() {

    }

    @Override
    public void run() {
        int choice = -1;
        ClientHandler ch = new ClientHandler();
        Scanner scanner = new Scanner(System.in);
        do {
            //
            showMessage("State: " + ch.getState());
            showMessage("0. Quit");
            //TODO: vänta på att personen ska svara och vänta på att starta upp audiostream
            showMessage("1. Call");
            //TODO: sent bye
            String sentence = scanner.nextLine();
            choice = Integer.parseInt(sentence);
            switch (choice) {
                case 1:
                    showMessage("2. Hang upp");
                    ch.invokeSentInvite(ch.getState());
                    showMessage("Write which ip you want to call.");
                    String invite_msg = scanner.nextLine();

                    break;
                case 2:
                    ch.invokeSentBye(ch.getState());
                    break;

            }
        } while (choice != 0);
        System.exit(0);
    }

    public synchronized void showMessage(String msg) {
        System.out.println(msg);
    }
}
