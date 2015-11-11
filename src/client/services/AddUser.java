package client.services;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Scanner;
import java.util.StringTokenizer;

import util.User;

public class AddUser extends Service
{
    /**
     * The user
     */
    private User user;

    /**
     * Initialize the user to
     * avoid null pointer exception
     */
    public AddUser() {
        user = new User();
    }

    @Override
    public void initialize(Scanner sc) throws ServiceException {
        System.out.print("Name : ");
        user.setName(sc.nextLine());
        if (user.getName().isEmpty()) {
            throw new ServiceException("You must supply a name");
        }

        System.out.print("Nicknames (spaced by spaces) : ");
        StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
        while (tokenizer.hasMoreTokens()) {
            user.getNicknames().add(tokenizer.nextToken());
        }

        if (user.getNicknames().size() == 0) {
            throw new ServiceException("You must supply at least one nickname");
        }
    }

    @Override
    public void parseResult(JSONObject json) throws JSONException {
        String result=json.getString("response");
        if(result.equals("NOK")){
            System.out.println("Error: "+ json.getString("reason"));
        }
        else{
            System.out.println(user.getName()+" added with success");
        }

    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("service", "add");
            json.put("name", user.getName());
            json.put("nicknames", user.getNicknames());
        } catch (JSONException ignored) {}
        return json;
    }
}
