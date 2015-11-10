package util;

import java.util.TreeMap;

/**
 * We have implemented this class because
 * it is easier writing "Users()" than
 * "TreeMap<String, Nicknames>(String.Case_INSENSITIVE_ORDER)"
 *
 * Furthermore we have overwritten some
 * method to make the users case
 * insensitive
 *
 * We use a TreeMap because it has a constructor
 * which allows to specify a comparator, thus
 * we don't have to redefine the methods
 * contains, equal, etc...
 */
public class Users extends TreeMap<String, Nicknames> {

    /**
     * Case insensitive for the key
     */
    public Users() {
        super(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Check if a nickname is available
     *
     * @param nicknameToTest the nickname to test with
     * @return true if the nickname is available, false otherwise
     */
    public boolean isNicknameAvailable(String nicknameToTest) {
        for (Nicknames nicknames : values()) {
            for (String nickname : nicknames) {
                if (nicknameToTest.equalsIgnoreCase(nickname)) {
                    return false;
                }
            }
        }

        return true;
    }
}
