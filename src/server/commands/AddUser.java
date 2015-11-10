package server.commands;

import exceptions.ModifyException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import server.Configuration;
import util.User;

public class AddUser extends Command
{
    @Override
    public void execute(JSONObject json) {
        try {
            if (!json.has("name") || json.getString("name").isEmpty()) {
                setError("You must specify a name");
                return ;
            }

            if (!json.has("nicknames") || json.getJSONArray("nicknames").length() == 0) {
                setError("You must specify at least one nickname");
                return ;
            }

            User user = new User();
            user.setName(json.getString("name"));

            JSONArray array = json.getJSONArray("nicknames");
            for (int i = 0; i < array.length(); ++i) {
                if (!user.getNicknames().contains(array.getString(i))) {
                    user.getNicknames().add(array.getString(i));
                }
            }

            Configuration.add(user);
            success = true;
            setResult();
        } catch (JSONException e) {
            setError("Bad formatted json");
        } catch (ModifyException e) {
            setError(e.getMessage());
        }
    }
}
