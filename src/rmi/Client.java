package rmi;


import rmi.rmi_interfaces.Interfacelist;
import rmi.rmi_services.Add_service;
import rmi.rmi_services.List_Service;

import java.rmi.Naming;
import java.util.Scanner;

/**
 * Created by user on 01/12/2015.
 */
public class Client {

    private String host;

    public Client(String hoste){
        host=hoste;
    }
    public void run(){

        // generer le stub rmic ./src/rmi/rmi_objets/ListNames

        try {

            System.out.println("Commande valide: List, Add");
            System.out.println("Votre commande:");
            Scanner sc = new Scanner(System.in);

            while (sc.hasNext()) {
                String service = sc.nextLine();
                if (service.equals("List")) {
                    System.out.println("Recherche de l'objet serveur rmi://"+host+"/ListNames");
                    List_Service list=new List_Service();
                    list.execute();
                }
                if (service.equals("Add")) {
                    Add_service add=new Add_service();
                    add.parse(sc);
                    add.execute();
                }
                System.out.println("Commande valide: List, Add");
                System.out.println("Votre commande:");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
