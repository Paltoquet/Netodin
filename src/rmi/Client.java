package rmi;


import exceptions.ServiceException;
import rmi.rmi_interfaces.Interfacelist;
import rmi.rmi_services.Add_service;
import rmi.rmi_services.List_Service;
import rmi.rmi_services.Service;

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

            Scanner sc = new Scanner(System.in);
            print();
            while (sc.hasNext()) {
                String service = sc.nextLine();
                Service commande;
                try {
                    commande = Service.getService(service);
                }
                catch (ServiceException e){
                    System.out.println(e.getMessage());
                    print();
                    continue;
                }

                commande.parse(sc);
                commande.execute();
                print();

            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void print(){
        System.out.println("Commande valide: List, Add");
        System.out.println("Votre commande:");
    }


}
