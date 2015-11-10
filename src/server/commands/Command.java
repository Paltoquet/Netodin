package server.commands;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Command
{
    protected boolean success = false;
    protected JSONObject result;
    protected JSONObject error;

    /**
     * Execute the command from a json object
     *
     * @param json the json to parse
     */
    public abstract void execute(JSONObject json);

    /**
     * Set the result of the command
     * when the command was successful
     * without additional data
     */
    protected void setResult() {
        setResult(null);
    }

    /**
     * Set the result of the command
     * when the command was successful
     * with additional data
     */
    protected void setResult(Object obj) {
        try {
            result = new JSONObject();
            result.put("response", "OK");
            if (obj != null) {
                result.put("data", obj);
            }
        } catch (JSONException ignore) {}
    }

    /**
     * If the command fails, this is
     * the method to call
     *
     * @param reason the error message
     */
    protected void setError(String reason) {
        try {
            error = new JSONObject();
            error.put("response", "NOK");
            error.put("reason", reason);
        } catch (JSONException ignore) {}
    }

    /**
     *
     * @return true if the method was executed correctly, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @return the result if no error, null otherwise
     */
    public JSONObject getResult() {
        return result;
    }

    /**
     *
     * @return the error if error, null otherwise
     */
    public JSONObject getError() {
        return error;
    }
}
