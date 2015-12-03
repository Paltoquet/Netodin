package rmi;

import rmi.rmi_interfaces.Interfacelist;
import rmi.rmi_objets.Ajouter;
import rmi.rmi_objets.ListNames;
import server.Configuration;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Created by user on 01/12/2015.
 */
public class Serveur {

    private String configFile;
    private String host;
    private int port;


    public Serveur(String hos,int por,String file) {
        configFile=file;
        host=hos;
        port=por;
    }

    public void run(){
        Configuration.load(configFile);
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            ListNames obj=new ListNames();
            Ajouter add=new Ajouter();
            String urllist = "rmi://"+"127.0.0.1"+"/ListNames";
            String urladd = "rmi://"+"127.0.0.1"+"/Ajouter";
            System.out.println("Enregistrement de l'objet avec l'url : " + urllist);
            Naming.rebind(urllist, obj);
            Naming.rebind(urladd,add);

            System.out.println("Serveur lancé");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
