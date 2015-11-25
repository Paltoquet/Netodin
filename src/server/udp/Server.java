package server.udp;

import communication.Communication;
import exceptions.CommunicationException;
import exceptions.ConnectionException;
import org.json.JSONException;
import org.json.JSONObject;
import server.AbstractServer;
import server.Configuration;
import communication.PacketDetails;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server extends AbstractServer
{
    /**
     * The socket of the server
     */
    private DatagramSocket serverSocket;

    /**
     * The datagram used to send data
     */
    private DatagramPacket datagram;

    /**
     * A map containing the incomplete requests
     * of users (in case of the request doesn't
     * fit in one chunk
     */
    private Map<InetAddress, PacketDetails> data;

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
            serverSocket = new DatagramSocket(port);
            System.out.println("Server started");
            System.out.println();

            byte[] buf = new byte[Communication.BUFLEN];
            datagram = new DatagramPacket(buf, buf.length);

            data = new HashMap<>();
        } catch (IOException e) {
            throw new ConnectionException("Can't create a server socket...");
        }
    }

    /**
     * Start waiting for incoming connection
     * and start a new thread which removes
     * outdated packages
     */
    public void run() {
        System.out.println("Waiting for incoming connection");

        new Thread(new Watcher(data)).start();
        while (true) {
            try {
                String received = receive();
                if (!received.isEmpty()) {
                    data.remove(datagram.getAddress());
                    send(executeCommand(new JSONObject(received)).toString());
                }
            } catch (JSONException e) {
                try {
                    send(getJSONError("Bad formatted JSON").toString());
                } catch (CommunicationException ignore) {}
            } catch (CommunicationException ignore) {}
        }
    }

    @Override
    protected String receive() throws CommunicationException {
        try {
            serverSocket.receive(datagram);
        } catch (IOException e) {
            throw new CommunicationException("Can't receive datagram");
        }

        PacketDetails packet;
        if (!data.containsKey(datagram.getAddress())) {
            packet = new PacketDetails();
        } else {
            packet = data.get(datagram.getAddress());
        }
        packet.updateMessage(new String(datagram.getData(), 0, datagram.getLength()));
        data.put(datagram.getAddress(), packet);

        return packet.isComplete() ? packet.getMessage() : "";
    }

    @Override
    protected void send(String message) throws CommunicationException {
        Communication.sendUDP(serverSocket, datagram.getAddress(), datagram.getPort(), message);
    }
}
