package net;

import enums.Services;
import exceptions.ConnectionException;
import services.Add;
import services.List;
import services.Service;
import exceptions.ServiceException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

import static enums.Services.*;

public class Client
{
    private Socket connexion;
    private PrintWriter writer;
    private BufferedInputStream reader;
    private Scanner sc;

    /**
     *
     * @param host the machine to contact
     * @param port the port of the TCP connection
     * @throws ConnectionException if the server is unreachable
     */
    public Client(String host, int port) throws ConnectionException {
        try {
            connexion = new Socket(host, port);
            writer = new PrintWriter(connexion.getOutputStream(), true);
            reader = new BufferedInputStream(connexion.getInputStream());
        } catch (IOException e) {
           throw new ConnectionException("Unable to establish the connection");
        }
    }

    public void run() {
        showPrompt();
        sc = new Scanner(System.in);

        while (sc.hasNext()) {
            StringTokenizer tokenizer = new StringTokenizer(sc.nextLine());
            Service service;

            try {
                service = Services.getService(tokenizer.nextToken());
                service.initialize(tokenizer);
            } catch (ServiceException e) {
                System.out.println("Error : " + e.getMessage());
                showPrompt();
                continue;
            }

            writer.write(service.toString());
            writer.flush();

            //On attend la réponse
            /*reader.read(blabla)
            System.out.println( "Réponse reçue " + response);*/

            showPrompt();
        }

        writer.flush();
        writer.close();
    }




    private void showPrompt() {
        System.out.print(" > ");
        System.out.flush();
    }
}