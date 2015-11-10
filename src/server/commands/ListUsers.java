package server.commands;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import server.Configuration;
import util.Users;

import java.util.ArrayList;
import java.util.List;

public class ListUsers extends Command
{

    @Override
    public void execute(JSONObject json) {
        try {
            int limit = -1;
            if (json.has("limit") && json.getInt("limit") > 0) {
                limit = json.getInt("limit");
            }

            List<String> startWith = new ArrayList<>();
            if (json.has("startWith")) {
                JSONArray array = json.getJSONArray("startWith");
                for (int i = 0; i < array.length(); ++i) {
                    if (!startWith.contains(array.getString(i))) {
                        startWith.add(array.getString(i));
                    }
                }
            }

            Users users = Configuration.listUsers(limit, startWith);
            success = true;
            setResult(users);
        } catch (JSONException e) {
            setError("Bad formatted json " + e.getMessage());
        }
    }
}