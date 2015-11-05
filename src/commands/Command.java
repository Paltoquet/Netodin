package commands;

import exceptions.CommandException;
import interfaces.ConvertJSON;

import java.util.StringTokenizer;

public abstract class Command implements ConvertJSON
{
    public abstract void parse(StringTokenizer tokenizer) throws CommandException;

    public String toString() {
        return toJSON().toString();
    }
}
