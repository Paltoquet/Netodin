package server;

import exceptions.ConnectionException;
import server.Server;

public class Main {

    public static void main(String [] args){
        try {
            Server server = new Server(1337, "D:\\Netodine\\src\\config\\config.json");
            server.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        }
    }

}
