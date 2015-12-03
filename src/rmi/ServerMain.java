package rmi;

import exceptions.RMIException;


public class ServerMain
{
    public static void main(String[] args) {
        try {
            (new Server(args[0])).run();
        } catch (RMIException e) {
            System.err.println(e.getMessage());
        }
    }
}
