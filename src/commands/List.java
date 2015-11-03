package commands;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03/11/2015.
 */
public class List extends Command
{
    private String a;
    private List<String> v;

    public List() {
        this("", new ArrayList<>());
    }

    public List(String name, java.util.List<String> nicknames) {
        this.name = name;
        this.nicknames = nicknames;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.append("command", "add");
        json.append("name", name);
        json.append("nicknames", nicknames);
        return json;
    }
}
