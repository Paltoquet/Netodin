package rmi.objets;

import rmi.interfaces.Interfacelist;
import server.Configuration;
import util.Users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by user on 01/12/2015.
 */
public class ListUsers extends UnicastRemoteObject implements Interfacelist {

    public ListUsers() throws RemoteException{
        super();
    }
    @Override
    public String list() throws RemoteException {
        Users users = Configuration.listUsers(-1, new ArrayList<>());
        return users.toString();
    }
}
