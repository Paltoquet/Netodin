package rmi.services;

import rmi.interfaces.IAddUser;
import util.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AddUser extends Service {

    private User user;

    public AddUser(){
        user = new User();
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

    public void execute(String host) throws RemoteException, MalformedURLException, NotBoundException {
        IAddUser hello = (IAddUser) Naming.lookup("rmi://" + host + "/Ajouter");
        System.out.println("Invocation de la méthode");
        String result = hello.add(user.getName(), user.getNicknames());
        System.out.println("Affichage du résultat :");
        System.out.println(result);
    }
}
