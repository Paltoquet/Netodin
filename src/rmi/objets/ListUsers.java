package rmi.objets;

import rmi.interfaces.IListUsers;
import server.Configuration;
import util.Users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ListUsers extends UnicastRemoteObject implements IListUsers {

    public ListUsers() throws RemoteException{
        super();
    }

    @Override
    public String list() throws RemoteException {
        Users users = Configuration.listUsers(-1, new ArrayList<>());
        return users.toString();
    }
}
