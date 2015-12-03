package rmi;

import exceptions.RMIException;
import rmi.objets.AddUser;
import rmi.objets.ListUsers;
import server.Configuration;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server
{
    public Server(String configFile) throws RMIException {
        Configuration.load(configFile);

        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            throw new RMIException("Unable to create the registry");
        }
    }

    public void run() throws RMIException {
        try {
            ListUsers obj = new ListUsers();
            AddUser add = new AddUser();
            String urllist = "rmi://127.0.0.1" + "/ListNames";
            String urladd = "rmi://127.0.0.1" + "/Ajouter";
            System.out.println("Enregistrement de l'objet avec l'url : " + urllist);
            System.out.println("Enregistrement de l'objet avec l'url : " + urladd);
            Naming.rebind(urllist, obj);
            Naming.rebind(urladd,add);

            System.out.println("Serveur lancé");
        } catch (RemoteException e) {
            throw new RMIException("Can't reach host...");
        } catch (MalformedURLException e) {
            throw new RMIException("Malformed url...");
        }
    }
}
