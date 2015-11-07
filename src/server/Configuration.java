package server;

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
