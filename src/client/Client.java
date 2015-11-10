package client;

import enums.Services;
import exceptions.ConnectionException;
import client.services.Service;
import exceptions.ServiceException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Client {

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
            Service service;

            try {
                service = Services.getClientService(sc.nextLine());
                service.initialize(sc);
            } catch (ServiceException e) {
                System.err.println("Error : " + e.getMessage());
                System.err.flush();
                System.out.flush();
                System.out.print("Commande : ");
                continue;
            }

            System.out.println(service.toString());
            writer.println(service.toString());

            try {
                System.out.println(reader.readLine());
            } catch (IOException e) {
                System.err.println("Can't get the response from the server");
            }

            System.out.print("Commande : ");
        }

        writer.close();
    }
}