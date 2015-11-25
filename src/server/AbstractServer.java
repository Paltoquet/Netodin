package server;

import enums.Services;
import exceptions.CommunicationException;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;
import server.commands.Command;

public abstract class AbstractServer
{
    /**
     * Send a message to the server/client
     *
     * @param message the message to send
     * @throws CommunicationException if there is an error during the communication
     */
    protected abstract void send(String message) throws CommunicationException;

    /**
     * Get the response from the server
     *
     * @return a string which contains the response from the server
     * @throws CommunicationException if there is an error during the communication
     */
    protected abstract String receive() throws CommunicationException;

    /**
     * Execute a command from a JSONObject
     *
     * @param json the json to parse
     * @return the response of the command
     */
    protected JSONObject executeCommand(JSONObject json) {
        try {
            Command command = Services.getServerService(json.getString("service"));
            command.execute(json);

            if (command.isSuccess()) {
                return command.getResult();
            }
            return command.getError();

        } catch (JSONException e) {
            return getJSONError("Service not provided");
        } catch (ServiceException e) {
            return getJSONError(e.getMessage());
        }
    }

    /**
     * Create a json error to
     * send to the client
     *
     * @param reason the reason of the error
     */
    protected JSONObject getJSONError(String reason) {
        try {
            JSONObject json = new JSONObject();
            json.put("result", "NOK");
            json.put("reason", reason);

            return json;
        } catch (JSONException ignored) {
            return new JSONObject();
        }
    }
}
