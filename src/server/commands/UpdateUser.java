package server.commands;

import exceptions.ModifyException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import server.Configuration;
import util.User;

public class UpdateUser extends Command
{
    @Override
    public void execute(JSONObject json) {
        try {
            if (!json.has("name") || json.getString("name").isEmpty()) {
                setError("You must specify a name");
                return ;
            }

            User updatedUser = new User();
            if (json.has("newName") && !json.getString("newName").isEmpty()) {
                updatedUser.setName(json.getString("newName"));
            }

            if (json.has("newNicknames") && json.getJSONArray("newNicknames").length() > 0) {
                JSONArray array = json.getJSONArray("newNicknames");
                for (int i = 0; i < array.length(); ++i) {
                    if (!updatedUser.getNicknames().contains(array.getString(i))) {
                        updatedUser.getNicknames().add(array.getString(i));
                    }
                }
            }

            Configuration.update(json.getString("name"), updatedUser);
            success = true;
            setResult();
        } catch (JSONException e) {
            setError("Bad formatted json");
        } catch (ModifyException e) {
            setError(e.getMessage());
        }
    }
}
