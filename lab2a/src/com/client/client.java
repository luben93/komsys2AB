package com.client;

import com.inter.MessageBoard;
import java.util.Scanner;

import java.rmi.Naming;

/**
 * Created by luben on 2015-10-07.
 */

public class client {
    public static void main(String[] args){
        System.out.println("I AM CLIEEENT !!!!!!");
        if(args.length != 1) {
            System.out.println(
                    "Usage: java MessageBoardClient <server host name>");
            System.exit(0);
        }

        try {
            MessageBoard board = (MessageBoard)
                    Naming.lookup("rmi://" + args[0] + "/message_board");

            Scanner scan = new Scanner(System.in);
            char ans;
            do {
                System.out.println("1 Show last 2 Show all 3 Put msg 0 Exit");
                ans = scan.nextLine().charAt(0);
                switch(ans) {
                    case '1': System.out.println(board.getLast()); break;
                    case '2': System.out.println(board.getAll()); break;
                    case '3': System.out.println("Enter new message:");
                        String msg = scan.nextLine();
                        board.putMessage(msg);
                        break;
                    case '0': break;
                    default: System.out.println("Huh?");
                }

            } while(ans != '0');

            System.out.println("Client exits.");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
