package client.services;

import exceptions.ServiceException;
import org.json.JSONObject;

import java.util.Scanner;

/**
 * Created by user on 10/11/2015.
 */
public class Delete extends Service {

    String name;

    public Delete(){

    }
    @Override
    public void initialize(Scanner sc) throws ServiceException {
        System.out.print("Name : ");
        name = sc.nextLine();
        if (name.isEmpty()) {
            throw new ServiceException("You must supply a name");
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result=new JSONObject();
        result.put("name",name);
        return result;
    }
}
