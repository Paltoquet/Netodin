package client.services;

import exceptions.ServiceException;
import interfaces.ConvertJSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public abstract class Service implements ConvertJSON
{
    /**
     * Initialize the service object from
     * the command line
     *
     * @param sc the list of word from the command line
     * @throws ServiceException if the command is not valid
     */
    public abstract void initialize(Scanner sc) throws ServiceException;

    /**
     * Parse and show the response from
     * the server
     *
     * @param json the json object containing the data
     * @throws JSONException if the json is not valid
     */
    public abstract void parseResult(JSONObject json) throws JSONException;

    /**
     *
     * @return the converted object to string
     */
    public String toString() {
        return toJSON().toString();
    }
}
