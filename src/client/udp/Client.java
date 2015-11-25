package client.udp;

import client.AbstractClient;
import communication.Communication;
import exceptions.CommunicationException;
import exceptions.ConnectionException;

import java.net.*;

public class Client extends AbstractClient
{
    /**
     * The UDP socket
     */
    private DatagramSocket client;

    /**
     * The server's address
     */
    private InetAddress address;

    /**
     * The server's port
     */
    private int port;

    /**
     *
     * @param host the machine to contact
     * @param port the port of the TCP connection
     * @throws ConnectionException if the server is unreachable
     */
    public Client(String host, int port) throws ConnectionException {
        try {
            address = InetAddress.getByName(host);
            this.port = port;

            client = new DatagramSocket();
        } catch (UnknownHostException e) {
            throw new ConnectionException(e.getMessage());
        } catch (SocketException e) {
            throw new ConnectionException("can't create Socket");
        }
    }

    @Override
    public void run() {
        super.run();
        client.close();
    }

    @Override
    protected void send(String message) throws CommunicationException {
        Communication.sendUDP(client, address, port, message);
    }


    @Override
    protected String receive() throws CommunicationException{
        return Communication.receiveUDP(client);
    }
}