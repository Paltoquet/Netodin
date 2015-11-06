import exceptions.ConnectionException;
import net.Server;

public class MainServer {

    public static void main(String [] args){
        try {
            Server server = new Server(1337);
            server.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        }
    }

}
