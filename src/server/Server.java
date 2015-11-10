package server;

import exceptions.ConnectionException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    /**
     * The socket of the server
     */
    ServerSocket serverSocket;

    /**
     *
     * @param port the port of the server
     * @param configFile the initial config file of the server
     * @throws ConnectionException if the server can't be created
     */
    public Server(int port, String configFile) throws ConnectionException {
        try {
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println();
        } catch (IOException e) {
            throw new ConnectionException("Can't create a server socket...");
        }

        try {
            System.out.println("Loading configuration");
            loadConfig(configFile);
            System.out.println("Configuration loaded");
            System.out.println();
        } catch (FileNotFoundException e) {
            throw new ConnectionException("Can't load config file : " + e.getMessage());
        }
    }

    private void loadConfig(String configFile) throws FileNotFoundException {
        File file = new File(configFile);
        FileInputStream fis = new FileInputStream(file);
        JSONTokener tokener = new JSONTokener(fis);
        JSONObject json = new JSONObject(tokener);

        Configuration.load(json);

    }

    /**
     * Start waiting for incoming connection
     * and start a new thread to interact with
     * the client
     */
    public void run() {
        System.out.println("Waiting for incoming connection");

        while (true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("new client : " + client);

                new Thread(new ThreadServer(client)).start();
            } catch (IOException e) {
                System.err.println("Error accept incoming connection.");
            }
        }
    }
}
