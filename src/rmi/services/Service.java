package rmi.services;

import exceptions.ServiceException;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public abstract class Service
{
    public abstract void parse(Scanner sc);

    public abstract void execute(String host) throws RemoteException, MalformedURLException, NotBoundException;

    public static Service getService(String name) throws ServiceException {
        if(name.toLowerCase().equals("add")){
            return new AddUser();
        } else if(name.toLowerCase().equals("list")){
            return new ListUsers();
        } else {
            throw new ServiceException("Service non reconnu");
        }
    }
}
