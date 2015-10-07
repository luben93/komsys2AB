package com.luben;

/**
 * Created by Julia on 2015-10-07.
 */


//import com.inter.MessageBoard;

import com.inter.MessageBoard;
import com.inter.note;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
/** Because each client call is executed in a separate thread
 *	on the server side, methods manipulating the list of
 *	messages must be declared synchronized.
 */
public class MessageBoardImpl extends UnicastRemoteObject implements MessageBoard {

    private ArrayList<String> messages;
    private ArrayList<note> clients;

    public MessageBoardImpl() throws RemoteException {
        super();
        clients=new ArrayList<note>();
        messages = new ArrayList<String>();
    }

    synchronized public void putMessage(String msg) throws RemoteException {
        messages.add(msg);
    }
    synchronized public String getLast() throws RemoteException {
        int n = messages.size();
        if(n == 0) return "No messages";
        return messages.get(n-1);
    }
    synchronized public String getAll() throws RemoteException {
        StringBuffer buf = new StringBuffer("");
        for(String m : messages) {
            buf.append(m + "\n");
        }
        return buf.toString();
    }

    @Override
    synchronized public int Register(note n) throws RemoteException {
        clients.add(n);
        System.out.println("addededededded: "+n);
        return clients.size();
    }

    @Override
    synchronized public void deRegister(note n) throws RemoteException {
        clients.remove(n);
        System.out.println("dereg: "+n);

    }
}
