package services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;

public class List extends Service
{
    private int limit;
    private java.util.List<String> startWith;

    public List() {
        this.limit = 0;
    }

    @Override
    public void initialize(StringTokenizer tokenizer) throws ServiceException {

    }

    @Override
    public void initialize(JSONObject json) throws ServiceException {

    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.append("command", "list");

            if (limit > 0) {
                json.append("limit", limit);
            }

            if (startWith != null) {
                json.append("startWith", startWith);
            }
        } catch (JSONException ignored) {}

        return json;
    }
}
