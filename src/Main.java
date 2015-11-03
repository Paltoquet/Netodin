import commands.Add;
import commands.Command;

import java.util.Arrays;

/**
 * Created by user on 03/11/2015.
 */
public class Main {

    public static void main(String [] args){
        Client client=new Client("127.0.0.1",1337);
        client.run();
    }

}
