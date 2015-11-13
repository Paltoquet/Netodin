package client;

import exceptions.ConnectionException;

public class Main
{
    public static void main(String [] args){
        try {
            String host = "127.0.0.1";
            int port = 1337;

            if (args.length > 1) {
                host = args[0];
            }

            if (args.length > 2) {
                port = Integer.parseInt(args[1]);
            }

            Client client = new Client(host, port);
            client.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        }
    }

}