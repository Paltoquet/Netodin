package client.services;

import exceptions.ServiceException;
import org.json.JSONException;
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
    public void parseResult(JSONObject json) throws JSONException {
        if(!json.getString("response").equals("OK")){
            System.err.println("Erreur : " + json.getString("reason"));
            System.err.flush();
            return ;
        }

        System.out.println(name + " deleted");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("service", "delete");
        result.put("name", name);
        return result;
    }
}
