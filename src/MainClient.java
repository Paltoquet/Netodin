import exceptions.ConnectionException;
import net.Client;

public class MainClient {

    public static void main(String [] args){
        try {
            Client client = new Client("127.0.0.1", 1337);
            client.run();
        } catch (ConnectionException e) {
            System.err.println(e.getMessage());
        }
    }

}
