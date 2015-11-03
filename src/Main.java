import commands.Add;
import commands.Command;

import java.util.Arrays;

/**
 * Created by user on 03/11/2015.
 */
public class Main {

    public static void main(String [] args){

        Command c = new Add("fdgdf", Arrays.asList("dfgdf", "dfgdf", "aze"));
        System.out.println(c.toString());
    }

}
