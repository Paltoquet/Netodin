package rmi;

/**
 * Created by user on 03/12/2015.
 */
public class ClientMain {

    public static void main(String[] args) {
        Client c=new Client(args[0]);
        c.run();
    }
}
