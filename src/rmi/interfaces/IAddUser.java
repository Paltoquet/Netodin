package rmi.interfaces;

import util.Nicknames;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAddUser extends Remote
{
    String add(String name, Nicknames list) throws RemoteException;
}
