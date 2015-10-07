package com.luben;

/**
 * Created by Julia on 2015-10-07.
 */
import java.net.*;
import java.rmi.*;

public class MessageBoardServer {

    public static void main(String[] args)  {

        try {
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

