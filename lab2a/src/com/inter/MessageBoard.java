package com.inter;

/**
 * Created by Julia on 2015-10-07.
 */
import java.rmi.*;

public interface MessageBoard extends Remote  {

    public void 	putMessage(String msg)	throws RemoteException;
    public void     who(note n)                   throws RemoteException;
    public void     nick(note n, String nick)throws RemoteException;
    public void     help(note n)                  throws RemoteException;
    public void     quit(note n)                  throws RemoteException;
    public int      Register(note n)        throws RemoteException;
    public void     deRegister(note n)      throws RemoteException;
    public String   recvMessage()           throws RemoteException;
}


