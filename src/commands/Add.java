package commands;

import exceptions.CommandException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Add extends Command
{
    private String name;
    private List<String> nicknames;

    public Add() {
        nicknames = new ArrayList<>();
    }

    @Override
    public void parse(StringTokenizer tokenizer) throws CommandException {
        if(!tokenizer.hasMoreTokens()){
            throw new CommandException("manque un nom");
        }
        name=tokenizer.nextToken();
        nicknames.add(tokenizer.nextToken());
        while(tokenizer.hasMoreTokens()){
            nicknames.add(tokenizer.nextToken());
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.append("command", "add");
        json.append("name", name);
        json.append("nicknames", nicknames);
        return json;
    }
}
