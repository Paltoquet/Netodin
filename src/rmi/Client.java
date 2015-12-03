package rmi;


import exceptions.ServiceException;
import rmi.services.Service;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    private String host;

    public Client(String host) {
        this.host = host;
    }

    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            print();
            while (sc.hasNext()) {
                String service = sc.nextLine();
                Service command;
                try {
                    command = Service.getService(service);
                }
                catch (ServiceException e){
                    System.out.println(e.getMessage());
                    print();
                    continue;
                }

                command.parse(sc);
                command.execute(host);
                print();

            }

        } catch (RemoteException e) {
            System.err.println("RemoteException...");
        } catch (NotBoundException e) {
            System.err.println("NotBoundException...");
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException...");
        }

    }

    public void print(){
        System.out.println("Commades disponibles : List, Add");
        System.out.print("> ");
    }
}
