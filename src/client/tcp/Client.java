package client.tcp;

import enums.Services;
import exceptions.ConnectionException;
import client.services.Service;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client
{
    /**
     * Writing to the server
     */
    private PrintWriter writer;

    /**
     * Reader from the server
     */
    private BufferedReader reader;

    /**
     *
     * @param host the machine to contact
     * @param port the port of the TCP connection
     * @throws ConnectionException if the server is unreachable
     */
    public Client(String host, int port) throws ConnectionException {
        try {
            Socket connection = new Socket(host, port);
            writer = new PrintWriter(connection.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
           throw new ConnectionException("Unable to establish the connection");
        }
    }

    /**
     * Run the interaction with the client
     */
    public void run() {
        System.out.print("Commande : ");
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String service = sc.nextLine();
            Service command;

            try {
                command = Services.getClientService(service);
                command.initialize(sc);
            } catch (ServiceException e) {
                System.err.println("Error : " + e.getMessage());
                System.err.flush();
                System.out.print("Commande : ");
                continue;
            }

            writer.println(command.toString());

            try {
                command.parseResult(new JSONObject(reader.readLine()));
            } catch(JSONException e) {
                System.err.println("Bad formatted json");
                System.err.flush();
            } catch (IOException e) {
                System.err.println("Can't get the response from the server");
                System.err.flush();
            }

            if (service.equalsIgnoreCase(String.valueOf(Services.QUIT))) {
                System.out.println("Bye !");
                break;
            }

            System.out.print("Commande : ");
        }

        writer.close();
    }
}