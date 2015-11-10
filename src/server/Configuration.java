package server;

import exceptions.ModifyException;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Nicknames;
import util.User;
import util.Users;

import java.util.List;
import java.util.Map;

public class Configuration
{
    /**
     * Map containing users with their nicknames (aliases)
     *
     * We use a TreeMap, because it is an implementation
     * of the SortedMap and in this way, we will have
     * the keys sorted alphabetically
     * (useful for the list service)
     */
    private static Users users = new Users();

    /**
     * Avoid the instantiation of the class
     */
    private Configuration() {}

    /**
     * Load the configuration of the server
     * a.k.a users with their nicknames
     *
     * @param json the json to format
     */
    public static void load(JSONObject json){
        JSONArray users = json.getJSONArray("users");
        for (int i = 0; i < users.length(); ++i) {
            User user = new User();
            user.setName(users.getJSONObject(i).getString("name"));

            //iterate through nicknames
            JSONArray nicknames = users.getJSONObject(i).getJSONArray("nicknames");
            for (int j = 0; j < nicknames.length(); ++j){
                user.getNicknames().add(nicknames.getString(j));
            }

            try {
                add(user);
            } catch (ModifyException e) {
                System.err.println("Cannot load user : " + user.getName() + "(" + e.getMessage() + ")");
            }
        }
    }

    /**
     *
     * @param user the instance object of the user
     * @throws ModifyException if the user is already present
     */
    public static void add(User user) throws ModifyException {
        if (users.containsKey(user.getName())) {
            throw new ModifyException("User already present");
        }

        verifyNicknames(user.getNicknames());
        users.put(user.getName(), user.getNicknames());
    }

    /**
     *
     * @param name the current username
     * @param user the instance object of the user
     * @throws ModifyException if the conditions are not respected
     */
    public static void update(String name, User user) throws ModifyException {
        if (!users.containsKey(name)) {
            throw new ModifyException("The user " + name + " does not exist");
        }

        if (user.getName().isEmpty() && user.getNicknames().size() == 0) {
            throw new ModifyException("You have to specify at least a new username or some new nicknames");
        }

        if (user.getNicknames().size() == 0) {
            user.setNicknames(users.get(name));
        }

        verifyNicknames(user.getNicknames());
        delete(name);
        add(user);
    }

    /**
     *
     * @param name the user to remove
     * @throws ModifyException if the user does not exist
     */
    public static void delete(String name) throws ModifyException {
        if (!users.containsKey(name)) {
            throw new ModifyException("The user " + name + " does not exist");
        }

        users.remove(name);
    }

    /**
     *
     * @param limit the maximum number of user to return
     * @param startWith starting pattern for the users
     * @return the list of matching users
     */
    public static Users listUsers(int limit, List<String> startWith) {
       Users matchingUsers = new Users();

        for (Map.Entry<String, Nicknames> user : users.entrySet()) {
            if (startWith.size() == 0) {
                matchingUsers.put(user.getKey(), user.getValue());
            } else {
                for (String value : startWith) {
                    if (user.getKey().toLowerCase().startsWith(value.toLowerCase()) && !matchingUsers.containsKey(user.getKey())) {
                        matchingUsers.put(user.getKey(), user.getValue());
                        break;
                    }
                }
            }

            if (matchingUsers.size() == limit) {
                break;
            }
        }

        return matchingUsers;
    }

    /**
     * Get the nicknames of one user
     *
     * @param name the user we want the nicknames
     * @return the nicknames
     * @throws ModifyException if the user does not exist
     */
    public static Nicknames getNicknames(String name) throws ModifyException {
        if (!users.containsKey(name)) {
            throw new ModifyException("The user " + name + " does not exist");
        }

        return users.get(name);
    }

    /**
     * Verify if the asked nicknames are available
     *
     * @param nicknames the nicknames to check
     * @throws ModifyException if one nickname is not available
     */
    private static void verifyNicknames(Nicknames nicknames) throws ModifyException {
        for(String nickname : nicknames) {
            if (!users.isNicknameAvailable(nickname)) {
                throw new ModifyException("The nickname " + nickname + " is already taken");
            }
        }
    }
}