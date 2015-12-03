package rmi.rmi_services;

import rmi.rmi_interfaces.Interfacelist;

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
}
