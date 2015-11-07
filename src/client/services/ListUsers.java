package client.services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ListUsers extends Service
{
    /**
     * Max number of user to receive
     */
    private int limit;

    /**
     * Return only users starting
     * with one of the following strings
     */
    private java.util.List<String> startWith;

    /**
     * Initialize the list to
     * avoid null pointer exception
     */
    public ListUsers() {
        this.startWith = new ArrayList<>();
    }

    @Override
    public void initialize(StringTokenizer tokenizer) throws ServiceException {

    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("service", "list");

            if (limit > 0) {
                json.put("limit", limit);
            }

            if (startWith.size() > 0) {
                json.put("startWith", startWith);
            }
        } catch (JSONException ignored) {}

        return json;
    }
}
