package server.tcp;

import exceptions.ConnectionException;
import server.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
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
            Configuration.load(configFile);

            System.out.println("Starting server...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println();
        } catch (IOException e) {
            throw new ConnectionException("Can't create a server socket...");
        }
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
                System.out.println("[" + client.getRemoteSocketAddress() + "] Client connected");

                new Thread(new ThreadServer(client)).start();
            } catch (IOException e) {
                System.err.println("Error accept incoming connection.");
            }
        }
    }
}
