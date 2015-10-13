package SIP;

import java.util.Scanner;

/**
 * Created by Julia on 2015-10-13.
 */
public class Interface implements Runnable {
    @Override
    public void run() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            String sentence = scanner.nextLine();
            choice = Integer.parseInt(sentence);
            System.out.println("0. Quit");
            System.out.println("1. Call");
            if(choice == 1){

            }
        } while (choice != 0);
        System.exit(0);
    }
}
