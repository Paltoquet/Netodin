package client.services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public class Quit extends Service
{
    @Override
    public void initialize(Scanner sc) throws ServiceException {}

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("service", "quit");
        } catch (JSONException ignored) {}
        return json;
    }
}
