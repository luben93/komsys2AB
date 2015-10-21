package com.client;

import com.inter.MessageBoard;
import com.inter.note;

import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by luben on 2015-10-07.
 */

public class client extends UnicastRemoteObject implements note {
    private MessageBoard board;

    public client(MessageBoard board) throws RemoteException {
        super();
        System.out.println("I AM CLIEEENT !!!!!!");
        this.board = board;

    }

    public void proto() throws RemoteException {

        try {

            Scanner scan = new Scanner(System.in);
            char ans;
            String msg = "";
            do {
                try {
                //System.out.println(board.recvMessage());
                //  ans = scan.nextLine().charAt(0);
                System.out.print(">");
                msg = scan.nextLine();
                if (msg.startsWith("/")) {
                    switch (msg) {
                        case "/help":
                            board.help(this);
                            break;
                        case "/who":
                            board.who(this);
                            break;
                        //case "/nick ": board.nick(this,msg.substring(6));break;
                        case "/quit":
                            break;
                        default:
                            if (msg.startsWith("/nick ")) {
                                board.nick(this, msg.substring(6));
                            }
                            break;
                    }
                } else {
                    board.putMessage(msg);
                }
                     /*
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
                     */
            } catch (ServerException e2){
                //System.out.println("Lucas ska göra serverutv med jullan nu");
                //e2.printStackTrace();
                board.checkConnected();
                    board.putMessage(msg);
                //System.out.println("its dead jim, ITS DEAD ");
                }
            } while (!msg.equals("/quit"));


        } catch (Exception e) {
           // e.printStackTrace();
        }finally {
            System.out.println("Client exits.");
            board.deRegister(this);
            System.exit(0);
        }
    }


    @Override
    synchronized public void notifyMsg(String msg) throws RemoteException {
        System.out.println("\n<" + msg);

    }

    private String nick = "user";

    @Override
    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String getNick() {
        return nick;
    }
}
