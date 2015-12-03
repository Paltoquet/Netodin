package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IListUsers extends Remote
{
    String list() throws RemoteException;
}
