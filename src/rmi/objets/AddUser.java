package rmi.objets;

import exceptions.ModifyException;
import rmi.interfaces.IAddUser;
import server.Configuration;
import util.Nicknames;
import util.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddUser extends UnicastRemoteObject implements IAddUser {

    public AddUser() throws RemoteException{
        super();
    }

    @Override
    public String add(String name, Nicknames nicknames) throws RemoteException {
        User user = new User();
        user.setName(name);
        for(String nickname : nicknames){
            user.getNicknames().add(nickname);
        }

        try {
            Configuration.add(user);
        } catch (ModifyException e) {
            return e.getMessage();
        }

        return "ajout réalisé.";
    }
}
