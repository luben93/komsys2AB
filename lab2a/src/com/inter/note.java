package com.inter;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by luben on 2015-10-07.
 */
public interface note extends Remote {
    public void notifyMsg(String msg) throws RemoteException;
    public void setNick(String nick) throws RemoteException;
    public String getNick() throws RemoteException;
}
