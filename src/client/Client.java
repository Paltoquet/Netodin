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
        showPrompt();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
            Service service;

            try {
                service = Services.getClientService(tokenizer.nextToken());
                service.initialize(tokenizer);
            } catch (ServiceException e) {
                System.out.println("Error : " + e.getMessage());
                showPrompt();
                continue;
            }

            writer.println(service.toString());

            try {
                System.out.println(reader.readLine());
            } catch (IOException e) {
                System.err.println("Can't get the response from the server");
            }

            showPrompt();
        }

        writer.close();
    }

    /**
     * After the result of the last command
     * show a new prompt to the user
     */
    private void showPrompt() {
        System.out.print(" > ");
        System.out.flush();
    }
}