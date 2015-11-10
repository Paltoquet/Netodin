package client.services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    public void initialize(Scanner sc) throws ServiceException {
        System.out.print("Name : ");
        name = sc.nextLine();
        if (name.isEmpty()) {
            throw new ServiceException("You must supply a name");
        }

        System.out.print("Nicknames (spaced by spaces) : ");
        StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
        while (tokenizer.hasMoreTokens()) {
            nicknames.add(tokenizer.nextToken());
        }

        if (nicknames.size() == 0) {
            throw new ServiceException("You must supply at least one nickname");
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
