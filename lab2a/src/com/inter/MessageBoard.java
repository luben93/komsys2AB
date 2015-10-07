package com.inter;

/**
 * Created by Julia on 2015-10-07.
 */
import java.rmi.*;

public interface MessageBoard extends Remote  {

    public void		putMessage(String msg)	throws RemoteException;
    public String	getLast()				throws RemoteException;
    public String	getAll()				throws RemoteException;
    public int     Register(note n)        throws RemoteException;
    public void     deRegister(note n)        throws RemoteException;
}


