package SIP;

import java.util.Scanner;

import SIP.Client.ClientHandler;

/**
 * Created by Julia on 2015-10-13.
 */
public class Interface extends Thread{
    public Interface(){

    }
    @Override
    public void run() {
        int choice = -1;
        ClientHandler ch = new ClientHandler();
        Scanner scanner = new Scanner(System.in);
        //Current state = sentBye
        System.out.println("FEED ME");
        do {
            String sentence = scanner.nextLine();
            choice = Integer.parseInt(sentence);
            //
            System.out.println("0. Quit");
            //TODO: vänta på att personen ska svara och vänta på att starta upp audiostream
            System.out.println("1. Call");
            //TODO: sent bye
            System.out.println("2. Hang upp");
            if(choice == 1){

            }
        } while (choice != 0);
        System.exit(0);
    }
}
