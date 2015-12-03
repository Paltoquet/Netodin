package rmi.rmi_objets;

import rmi.rmi_interfaces.Interfacelist;
import server.Configuration;
import server.commands.Command;
import server.commands.ListUsers;
import util.Users;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by user on 01/12/2015.
 */
public class ListNames extends UnicastRemoteObject implements Interfacelist {

    public ListNames() throws RemoteException{
        super();
    }
    @Override
    public String list() throws RemoteException {
        Users users = Configuration.listUsers(-1, new ArrayList<>());
        return users.toString();
    }
}
