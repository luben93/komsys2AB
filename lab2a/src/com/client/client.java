package com.client;

import com.inter.MessageBoard;
import com.inter.note;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by luben on 2015-10-07.
 */

public class client extends UnicastRemoteObject implements note{
    private MessageBoard board;

    public  client(MessageBoard board)throws RemoteException{
        super();
        System.out.println("I AM CLIEEENT !!!!!!");
        this.board=board;

    }
         public void proto(){

             try {

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
                 board.deRegister(this);
                 System.exit(0);
             }
             catch(Exception e) {
                 e.printStackTrace();
             }
         }


    @Override
    public void notifyMsg(String msg) throws RemoteException {
        System.out.println("out"+msg);
    }

}
