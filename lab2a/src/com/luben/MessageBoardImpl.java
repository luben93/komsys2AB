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

/**
 * Because each client call is executed in a separate thread
 * on the server side, methods manipulating the list of
 * messages must be declared synchronized.
 */
public class MessageBoardImpl extends UnicastRemoteObject implements MessageBoard {

    private ArrayList<String> messages;
    private ArrayList<note> clients;

    public MessageBoardImpl() throws RemoteException {
        super();
        clients = new ArrayList<note>();
        messages = new ArrayList<String>();

    }

    synchronized public void sendToAll(String msg) throws RemoteException {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).notifyMsg(msg);
        }
    }
/*
    synchronized public String putMessage(String msg, note currentClient) throws RemoteException {
        String out = "";
        messages.add(msg);
        if (msg.startsWith("/nick")) {
            return msg;
        } else if (msg.equals("/quit")) {
            //TODO sockettimeout
        } else if (msg.equals("/who")) {

        } else if (msg.equals("/help")||msg.startsWith("/")) {
            out = "you can write:\n /quit \n /who \n /nick <nickname> \n /help\n or send a message";
        } else {
            sendToAll(msg);
        }
        return out;
    }
*/

    @Override
    public void putMessage(String msg) throws RemoteException {
        sendToAll(msg);
    }

    @Override
    public void who(note n) throws RemoteException {
        StringBuilder out=new StringBuilder(100);
        for(note cli :clients){
            out.append(cli.getNick()+", ");
        }
        n.notifyMsg(out.toString());
    }

    @Override
    public void nick(note n, String nick) throws RemoteException {
        n.setNick(nick);
    }

    @Override
    public void help(note n) throws RemoteException {
        n.notifyMsg("you can write:\n /quit \n /who \n /nick <nickname> \n /help\n or send a message");
    }

    @Override
    public void quit(note n) throws RemoteException {
        //TODO sockettimeout
    }

    @Override
    synchronized public int Register(note n) throws RemoteException {
        clients.add(n);
        System.out.println("addededededded: " + n);
        return clients.size();
    }

    @Override
    synchronized public void deRegister(note n) throws RemoteException {
        clients.remove(n);
        System.out.println("dereg: " + n);

    }

    @Override
    public String recvMessage() throws RemoteException {
        return "Welcome";
    }
}
