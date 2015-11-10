package server;

import exceptions.ConnectionException;
import server.Server;

public class Main
{
    public static void main(String [] args){
        try {
            Server server = new Server(Integer.parseInt(args[0]), args[1]);
            server.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        }
    }

}
