package rmi.rmi_interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by user on 03/12/2015.
 */
public interface InterfaceAjouter extends Remote {
    public String ajouter(String name,ArrayList<String> list) throws RemoteException;
}
