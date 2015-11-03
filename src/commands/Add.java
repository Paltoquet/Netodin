package commands;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Add extends Command
{
    private String name;
    private List<String> nicknames;

    public Add() {
        this("", new ArrayList<>());
    }

    public Add(String name, List<String> nicknames) {
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
