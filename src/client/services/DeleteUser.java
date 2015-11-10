package client.services;

import exceptions.ServiceException;
import org.json.JSONObject;

import java.util.Scanner;

public class DeleteUser extends Service
{
    /**
     * The username
     */
    String name;

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
        JSONObject result = new JSONObject();
        result.put("service", "delete");
        result.put("name", name);
        return result;
    }
}
