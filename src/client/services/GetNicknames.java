package client.services;

import exceptions.ServiceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Scanner;

public class GetNicknames extends Service
{
    /**
     * The name of the user
     */
    private String name;

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
        if (!json.getString("response").equals("OK")){
            System.err.println("Error: " + json.getString("reason"));
            System.err.flush();
            return ;
        }

        System.out.print("Nicknames of " + name + " : ");
        JSONArray nicknames = json.getJSONArray("data");
        for(int i = 0; i < nicknames.length(); ++i){
            System.out.print(nicknames.getString(i));
        }
        System.out.println();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("service", "getnicknames");
            json.put("name", name);
        } catch (JSONException ignored) {}
        return json;
    }
}
