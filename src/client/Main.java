package client;

import exceptions.ConnectionException;

public class Main
{
    public static void main(String [] args){
        try {
            Client client = new Client(args[0], Integer.parseInt(args[1]));
            client.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        }
    }

}