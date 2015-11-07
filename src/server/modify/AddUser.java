package server.modify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import server.Configuration;

import java.util.ArrayList;
import java.util.List;

public class AddUser extends Command
{
    @Override
    public void execute(JSONObject json) {
        try {
            if (!json.has("name") || json.getString("name").isEmpty()) {
                setError("Missing field name");
                return ;
            }

            if (!json.has("nicknames")) {
                setError("Missing nicknames");
                return ;
            }

            String name = json.getString("name");
            List<String> nicknames = new ArrayList<>();

            JSONArray array = json.getJSONArray("nicknames");
            for (int i = 0; i < array.length(); ++i) {
                if (!nicknames.contains(array.getString(i))) {
                    nicknames.add(array.getString(i));
                }
            }

            if (Configuration.addUser(name, nicknames)) {
                success = true;
                setResult();
            } else {
                setError("User already present");
            }
        } catch (JSONException e) {
            setError("Bad formatted json");
        }
    }
}
