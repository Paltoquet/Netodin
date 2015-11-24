package client.udp;

import exceptions.ConnectionException;

public class Main
{
    public static void main(String [] args){
        try {
            String host = (args.length > 0 ? args[0] : "127.0.0.1");
            int port = (args.length > 1 ? Integer.parseInt(args[1]) : 1337);

            Client client = new Client(host, port);
            client.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Incorrect port : " + args[1]);
        }
    }

}