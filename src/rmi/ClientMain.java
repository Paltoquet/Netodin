package rmi;


public class ClientMain
{
    public static void main(String[] args) {
        String host = "127.0.0.1";
        if (args.length == 1) {
            host = args[0];
        } else if (args.length > 1) {
            System.err.println("Usage : ./client.jar host");
        }

        (new Client(host)).run();
    }
}
