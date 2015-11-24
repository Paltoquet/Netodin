package server.tcp;

import exceptions.ConnectionException;

public class Main
{
    public static void main(String [] args){
        if (args.length != 2) {
            System.err.println("Usage : ./server.jar port configFile");
            return ;
        }

        try {
            Server server = new Server(Integer.parseInt(args[0]), args[1]);
            server.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Incorrect port : " + args[0]);
        }
    }

}
