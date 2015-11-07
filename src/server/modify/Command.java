package server.modify;

import exceptions.ServiceException;
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
     * @throws ServiceException if the command is not valid
     */
    public abstract void execute(JSONObject json);

    /**
     * Set the result of the command
     * when the command was successful
     */
    protected void setResult() {
        try {
            result = new JSONObject();
            result.put("response", "OK");
        } catch (JSONException e) {}
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
        } catch (JSONException e) {}
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
