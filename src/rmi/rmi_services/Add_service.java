package rmi.rmi_services;

import exceptions.ServiceException;
import rmi.rmi_interfaces.InterfaceAjouter;
import rmi.rmi_interfaces.Interfacelist;
import util.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by user on 03/12/2015.
 */
public class Add_service extends Service {

    User user;
    public Add_service(){
        user=new User();
    }

    public void parse(Scanner sc){
        System.out.print("Name : ");
        user.setName(sc.nextLine());
        if (user.getName().isEmpty()) {
            System.out.println("You must supply a name");
        }
        System.out.print("Nicknames (spaced by spaces) : ");
        StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
        while (tokenizer.hasMoreTokens()) {
            user.getNicknames().add(tokenizer.nextToken());
        }
    }

    public void execute() throws RemoteException, MalformedURLException, NotBoundException {
        InterfaceAjouter hello =
                (InterfaceAjouter) Naming.lookup("rmi://127.0.0.1/Ajouter");
        System.out.println("Invocation de la méthode");
        String result = hello.ajouter(user.getName(), user.getNicknames());
        System.out.println("Affichage du résultat :");
        System.out.println(result);
    }
}
