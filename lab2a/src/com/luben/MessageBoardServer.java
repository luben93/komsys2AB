package com.luben;

/**
 * Created by Julia on 2015-10-07.
 */

import java.rmi.Naming;
import java.rmi.RemoteException;


public class MessageBoardServer {

    public static void main(String[] args)  {

        try {
            //LocateRegistry.createRegistry(1742);
            MessageBoardImpl board = new MessageBoardImpl();
            Naming.rebind("message_board", board);  // set the handle

            System.out.println(
                    "MessageBoardServer started and awaiting connections.");
        }
        catch (RemoteException er)  {
            System.out.println(
                    "Exception in MessageBoardServer.main: " + er);
        }
        catch (Exception err)   {
            System.out.println("Exception occurred: " + err);
        }
    }
}

