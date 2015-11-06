package services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Add extends Service
{
    private String name;
    private List<String> nicknames;

    public Add() {
        nicknames = new ArrayList<>();
    }

    @Override
    public void initialize(StringTokenizer tokenizer) throws ServiceException {
        if (!tokenizer.hasMoreTokens()) {
            throw new ServiceException("You must supply a name\nExample : ADD name nickname1 nickname2 ...");
        }
        name = tokenizer.nextToken();

        while (tokenizer.hasMoreTokens()) {
            nicknames.add(tokenizer.nextToken());
        }

        if (nicknames.size() == 0) {
            throw new ServiceException("You must supply one or more nicknames\nExample : ADD name nickname1 nickname2 ...");
        }
    }

    @Override
    public void initialize(JSONObject json) throws ServiceException {

    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.append("command", "add");
            json.append("name", name);
            json.append("nicknames", nicknames);
        } catch (JSONException ignored) {}
        return json;
    }
}
