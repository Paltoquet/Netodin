package server;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Configuration {
    /**
     * Map containing users with their nicknames (aliases)
     *
     * We use a TreeMap, brcause it is an implementation
     * of the SortedMap and in this way, we will have
     * the keys sorted alphabetically
     * (useful for the list service)
     */
    private static Map<String, List<String>> users = new TreeMap<>();

    /**
     * Avoid the instanciation of the class
     */


    private Configuration() {}

    /**
     *
     * @param user the name of the user
     * @param nicknames the aliases of the user
     * @return true if the user was added, false otherwise
     */
    public static boolean addUser(String user, List<String> nicknames) {
        if (users.containsKey(user)) {
            return false;
        }

        users.put(user, nicknames);
        return true;
    }


    public static void load(JSONObject json){
        JSONArray tab=json.getJSONArray("users");
        for(int i=0;i<tab.length();i++){
            String name=tab.getJSONObject(i).getString("name");
            ArrayList<String> nicknames= new ArrayList<>();

            //iterate through nicknames
            for(int c=0;c<tab.getJSONObject(i).getJSONArray("nicknames").length();c++){
                 nicknames.add(tab.getJSONObject(i).getJSONArray("nicknames").getString(c));
            }
            addUser(name, nicknames);
        }
    }
    /**
     *
     * @param limit the maximum number of user to return
     * @param startWith starting pattern for the users
     * @return the list of matching users
     */
    public static Map<String, List<String>> listUsers(int limit, List<String> startWith) {
        Map<String, List<String>> matchingUsers = new TreeMap<>();

        for (Map.Entry<String, List<String>> user : users.entrySet()) {
            for (String value : startWith) {
                if (user.getKey().startsWith(value) && !matchingUsers.containsKey(user.getKey())) {
                    matchingUsers.put(user.getKey(), user.getValue());
                    break;
                }
            }

            if (matchingUsers.size() == limit) {
                break;
            }
        }

        return matchingUsers;
    }
}
