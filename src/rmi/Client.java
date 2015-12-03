package rmi;


import rmi.rmi_interfaces.Interfacelist;

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

            System.out.println("Commande valide: List");
            Scanner sc = new Scanner(System.in);

            while (sc.hasNext()) {
                String service = sc.nextLine();
                if (service.equals("List")) {
                    System.out.println("Recherche de l'objet serveur rmi://"+host+"/ListNames");
                    Interfacelist hello =
                            (Interfacelist) Naming.lookup("rmi://127.0.0.1/ListNames");
                    System.out.println("Invocation de la méthode");
                    String result = hello.list();
                    System.out.println("Affichage du résultat :");
                    System.out.println(result);
                    System.exit(0);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
