package server.commands;

import exceptions.ModifyException;
import org.json.JSONException;
import org.json.JSONObject;
import server.Configuration;
import util.Nicknames;

public class GetNicknames extends Command
{
    @Override
    public void execute(JSONObject json) {
        try {
            if (!json.has("name") || json.getString("name").isEmpty()) {
                setError("You must specify a name");
                return ;
            }

            Nicknames nicknames = Configuration.getNicknames(json.getString("name"));
            success = true;
            setResult(nicknames);
        } catch (JSONException e) {
            setError("Bad formatted json");
        } catch (ModifyException e) {
            setError(e.getMessage());
        }
    }
}
