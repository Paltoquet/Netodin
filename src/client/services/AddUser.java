package client.services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AddUser extends Service
{
    /**
     * The name of the new user
     */
    private String name;

    /**
     * The aliases of the new user
     */
    private List<String> nicknames;

    /**
     * Initialize the list to
     * avoid null pointer exception
     */
    public AddUser() {
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

    public String getName() {
        return name;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("service", "add");
            json.put("name", name);
            json.put("nicknames", nicknames);
        } catch (JSONException ignored) {}
        return json;
    }
}
