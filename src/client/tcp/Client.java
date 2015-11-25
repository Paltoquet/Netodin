package client.tcp;

import client.AbstractClient;
import communication.Communication;
import exceptions.CommunicationException;
import exceptions.ConnectionException;

import java.io.*;
import java.net.Socket;


public class Client extends AbstractClient
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

    @Override
    public void run() {
        super.run();
        writer.close();
    }

    @Override
    protected void send(String message) {
        Communication.sendTCP(writer, message);
    }

    @Override
    protected String receive() throws CommunicationException {
        return Communication.receiveTCP(reader);
    }
}