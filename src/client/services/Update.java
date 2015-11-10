package client.services;

import exceptions.ServiceException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by user on 10/11/2015.
 */
public class Update extends Service {

    String oldName;
    String newName;
    List nickname;

    public Update(){
        nickname=new ArrayList<>();
    }
    @Override
    public void initialize(Scanner sc) throws ServiceException {
        System.out.print("Name : ");
        oldName = sc.nextLine();
        if (oldName.isEmpty()) {
            throw new ServiceException("You must supply a name");
        }
        System.out.println("New name(optional) : ");
        newName=sc.nextLine();
        System.out.print("Nicknames (optional spaced by spaces) : ");
        StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
        while (tokenizer.hasMoreTokens()) {
            nickname.add(tokenizer.nextToken());
        };
    }

    @Override
    public JSONObject toJSON() {
        JSONObject result=new JSONObject();
        result.put("name",oldName);
        if(!newName.isEmpty()){
            result.put("newName",newName);
        }
        if(!nickname.isEmpty()){
            result.put("newNicknames",nickname);
        }
        return result;
    }
}
