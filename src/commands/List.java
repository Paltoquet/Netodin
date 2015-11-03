package commands;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 03/11/2015.
 */
public class List extends Command
{
    private int limit;
    private String startWith;

    public List() {
        limit = 0;
        this.startWith = null;
    }

    public List(int limite,String start) {
        limit = limite;
        this.startWith = start;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.append("command", "list");
        if (limit > 0) {
            json.append("limit",limit);
        }
        if (startWith!=null) {
            json.append("startwith",startWith );
        }
        return json;
    }
}
