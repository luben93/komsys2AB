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
        client cli=null;
        int err=0;
        try {
            board = (MessageBoard)
                    Naming.lookup("rmi://" + server + "/message_board");
            cli=new client(board);
            int out=board.Register(cli);
            System.out.println(out+" active connections");
            cli.proto();

        } catch (NotBoundException e) {
            e.printStackTrace();
            err=-1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            err=-2;
        } catch (RemoteException e) {
            e.printStackTrace();
            err = -3;
        }finally {
            System.exit(err);
        }

    }
}
