package rmi.rmi_services;

import exceptions.ServiceException;
import rmi.rmi_interfaces.Interfacelist;
import rmi.rmi_objets.Ajouter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Created by user on 03/12/2015.
 */
public abstract class Service {


    public abstract void parse(Scanner sc);

    public abstract void execute() throws RemoteException, MalformedURLException, NotBoundException;

    public static Service getService(String name) throws ServiceException {
        if(name.toLowerCase().equals("add")){
            return new Add_service();
        }
        if(name.toLowerCase().equals("list")){
            return new List_Service();
        }
        else throw new ServiceException("Service non reconu");
    }
}
