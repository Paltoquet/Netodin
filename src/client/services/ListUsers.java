package client.services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ListUsers extends Service
{
    /**
     * Max number of user to receive
     */
    private int limit;

    /**
     * Return only users starting
     * with one of the following strings
     */
    private List<String> startWith;

    /**
     * Initialize the list to
     * avoid null pointer exception
     */
    public ListUsers() {
        this.startWith = new ArrayList<>();
    }

    @Override
    public void initialize(Scanner sc) throws ServiceException {
        System.out.print("Limit (optional) : ");
        try {
            limit = Integer.parseInt(sc.nextLine());
            if (limit < 0) {
                limit = 0;
            }
        } catch (NumberFormatException | NullPointerException ignore) {}

        System.out.print("Start with (spaced by spaces) : ");
        StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
        while (tokenizer.hasMoreTokens()) {
            startWith.add(tokenizer.nextToken());
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("service", "list");

            if (limit > 0) {
                json.put("limit", limit);
            }

            if (startWith.size() > 0) {
                json.put("startWith", startWith);
            }
        } catch (JSONException ignored) {}

        return json;
    }
}
