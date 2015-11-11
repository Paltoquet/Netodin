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
        String result=json.getString("response");
        if(result.equals("NOK")){
            System.out.println("Error: "+ json.getString("reason"));
        }
        else{
            System.out.println("nicknames of " + name + ":");
            JSONArray nicknames=json.getJSONArray("data");
            for(int i=0;i<nicknames.length();i++){
                System.out.println(nicknames.getString(i));
            }
        }


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
