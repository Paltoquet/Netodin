package client;

import client.services.Service;
import enums.Services;
import exceptions.CommunicationException;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public abstract class AbstractClient
{
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

                send(command.toString());

                command.parseResult(new JSONObject(receive()));

                if (service.equalsIgnoreCase(String.valueOf(Services.QUIT))) {
                    System.out.println("Bye !");
                    break;
                }
            } catch (CommunicationException | JSONException | ServiceException e) {
                System.err.println("Error : " + e.getMessage());
                System.err.flush();
            }

            System.out.print("Commande : ");
        }
    }

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
}
