package rmi.rmi_interfaces;



import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 01/12/2015.
 */
public interface Interfacelist extends Remote {
    public String list() throws RemoteException;
}
