package util;

import java.util.ArrayList;

/**
 * We have implemented this class because
 * it is easier writing "Nicknames" than
 * "List<String>"
 *
 * Furthermore we have overwritten some
 * method to make the nicknames case
 * insensitive
 */
public class Nicknames extends ArrayList<String> {
    @Override
    public boolean contains(Object o) {
        if (!(o instanceof String)) {
            return false;
        }

        for (String s : this) {
            if (s.equalsIgnoreCase((String) o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Nicknames) || size() != ((Nicknames) o).size()) {
            return false;
        }

        Nicknames n = (Nicknames) o;
        for (int i = 0; i < size(); ++i) {
            if (!get(i).equalsIgnoreCase(n.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int val = 13;
        for (String s : this) {
            val += s.toLowerCase().hashCode();
        }

        return val;
    }
}
