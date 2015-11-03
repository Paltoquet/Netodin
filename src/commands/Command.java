package commands;

import interfaces.ConvertJSON;

public abstract class Command implements ConvertJSON
{
    public String toString() {
        return toJSON().toString();
    }
}
