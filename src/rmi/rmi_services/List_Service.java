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
public class List_Service extends Service{

    public void parse(Scanner sc) {

    }

    public void execute() throws RemoteException, MalformedURLException, NotBoundException {
        Interfacelist hello =
                (Interfacelist) Naming.lookup("rmi://127.0.0.1/ListNames");
        System.out.println("Invocation de la méthode");
        String result = hello.list();
        System.out.println("Affichage du résultat :");
        System.out.println(result);
    }
}
