package interfaces;

import org.json.JSONObject;

public interface ConvertJSON {

    /**
     * Convert an object to a JSON
     *
     * @return the JSONized object
     */
    JSONObject toJSON();
}
