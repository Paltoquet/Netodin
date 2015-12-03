package rmi.services;

import rmi.interfaces.IListUsers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ListUsers extends Service{

    public void parse(Scanner sc) {}

    public void execute(String host) throws RemoteException, MalformedURLException, NotBoundException {
        IListUsers hello = (IListUsers) Naming.lookup("rmi://" + host + "/ListNames");
        System.out.println("Invocation de la méthode");
        String result = hello.list();
        System.out.println("Affichage du résultat :");
        System.out.println(result);
    }
}
