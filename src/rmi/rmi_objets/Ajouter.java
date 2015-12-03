package rmi.rmi_objets;

import exceptions.ModifyException;
import rmi.rmi_interfaces.InterfaceAjouter;
import server.Configuration;
import util.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by user on 03/12/2015.
 */
public class Ajouter extends UnicastRemoteObject implements InterfaceAjouter {

    public Ajouter() throws RemoteException{
        super();
    }
    @Override
    public String ajouter(String name,ArrayList<String> list) throws RemoteException {
        User user = new User();
        user.setName(name);
        for(String surnom:list){
            user.getNicknames().add(surnom);
        }
        try {
            Configuration.add(user);
        } catch (ModifyException e) {
            e.printStackTrace();
        }
        return "ajout réalisé \n";
    }
}
