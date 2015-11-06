package services;

import exceptions.ServiceException;
import interfaces.ConvertJSON;
import org.json.JSONObject;

import java.util.StringTokenizer;

public abstract class Service implements ConvertJSON
{
    public abstract void initialize(StringTokenizer tokenizer) throws ServiceException;
    public abstract void initialize(JSONObject json) throws ServiceException;

    public String toString() {
        return toJSON().toString();
    }
}
