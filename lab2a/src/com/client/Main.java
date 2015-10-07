package com.client;

import com.inter.MessageBoard;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by luben on 2015-10-07.
 */
public class Main {
    public static void main(String[] args){
        String server="130.229.174.158";//julias ip
        server="localhost";
        MessageBoard board = null;
        try {
            board = (MessageBoard)
                    Naming.lookup("rmi://" + server + "/message_board");
            client cli=new client(board);
            int out=board.Register(cli);
            System.out.println("n: "+out);
            cli.proto();

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
