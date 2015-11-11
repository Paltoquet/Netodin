package client.services;

import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;
import util.User;

import java.util.Scanner;
import java.util.StringTokenizer;

public class UpdateUser extends Service
{
    /**
     * The current username
     */
    private String name;

    /**
     * The modified user
     */
    private User newUser;

    /**
     * Initialization of an User
     * to avoid null pointer exception
     */
    public UpdateUser(){
        newUser = new User();
    }

    @Override
    public void initialize(Scanner sc) throws ServiceException {
        System.out.print("Name : ");
        name = sc.nextLine();
        if (name.isEmpty()) {
            throw new ServiceException("You must supply a name");
        }

        System.out.print("New name(optional) : ");
        newUser.setName(sc.nextLine());

        System.out.print("New nicknames (optional spaced by spaces) : ");
        StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
        while (tokenizer.hasMoreTokens()) {
            newUser.getNicknames().add(tokenizer.nextToken());
        }

        if (newUser.getName().isEmpty() && newUser.getNicknames().size() == 0) {
            throw new ServiceException("You must supply a new name or new nicknames, otherwise it will do nothing");
        }
    }

    @Override
    public void parseResult(JSONObject json) throws JSONException {
        if (!json.getString("response").equals("OK")) {
            System.err.println("Error: " + json.getString("reason"));
            System.err.flush();
            return ;
        }
        System.out.println(name + " updated");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("service", "update");
        result.put("name", name);

        if(!newUser.getName().isEmpty()){
            result.put("newName", newUser.getName());
        }

        if(!newUser.getNicknames().isEmpty()){
            result.put("newNicknames", newUser.getNicknames());
        }
        return result;
    }
}