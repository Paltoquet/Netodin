package server.commands;

import org.json.JSONObject;

public class Quit extends Command
{
    @Override
    public void execute(JSONObject json) {
        success = true;
        setResult();
    }
}
