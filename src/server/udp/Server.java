package server.udp;

import enums.Services;
import exceptions.ConnectionException;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import server.Configuration;
import server.commands.Command;
import server.tcp.ThreadServer;
import util.PacketDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Server
{
    /**
     * The socket of the server
     */
    private DatagramSocket serverSocket;

    /**
     * the buffer's size
     */
    private final static int bufLen = 256;

    /**
     *
     * @param port the port of the server
     * @param configFile the initial config file of the server
     * @throws ConnectionException if the server can't be created
     */
    public Server(int port, String configFile) throws ConnectionException {
        try {
            System.out.println("Loading configuration");
            loadConfig(configFile);
            System.out.println("Configuration loaded");
            System.out.println();
        } catch (FileNotFoundException e) {
            throw new ConnectionException("Can't load config file : " + e.getMessage());
        }

        try {
            System.out.println("Starting server...");
            serverSocket = new DatagramSocket(port);
            System.out.println("Server started");
            System.out.println();
        } catch (IOException e) {
            throw new ConnectionException("Can't create a server socket...");
        }
    }

    /**
     * Load a file, convert the file to a JSONObject
     * and then give the json to initialize the server
     *
     * @param configFile the filename to load
     * @throws FileNotFoundException if the file was not found
     */
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

        Map<InetAddress, PacketDetails> data = new HashMap<>();
        new Thread(new Watcher(data)).start();

        while (true) {
            byte[] buf = new byte[bufLen];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

            try {
                serverSocket.receive(datagramPacket);
            } catch (IOException e) {
                continue;
            }

            PacketDetails packet;
            if (!data.containsKey(datagramPacket.getAddress())) {
                packet = new PacketDetails();
            } else {
                packet = data.get(datagramPacket.getAddress());
            }
            packet.updateMessage(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));

            if (packet.isComplete()) {
                System.out.println(packet.getMessage());
                System.out.println(executeCommand(packet.getMessage()).toString());
                send(datagramPacket.getAddress(), datagramPacket.getPort(), executeCommand(packet.getMessage()).toString());
                data.remove(datagramPacket.getAddress());
            } else {
                data.put(datagramPacket.getAddress(), packet);
            }
        }
    }

    private JSONObject executeCommand(String data) {
        JSONObject json;
        Command command;

        try {
            json = new JSONObject(data);
            command = Services.getServerService(json.getString("service"));
        } catch (JSONException e) {
            return getJSONError("This service does not exist");
        } catch (ServiceException e) {
            return getJSONError(e.getMessage());
        }

        command.execute(json);
        if (command.isSuccess()) {
            return command.getResult();
        }
        return command.getError();
    }

    /**
     * Create a json error to
     * send to the client
     *
     * @param reason the reason of the error
     */
    private JSONObject getJSONError(String reason) {
        try {
            JSONObject json = new JSONObject();
            json.put("result", "NOK");
            json.put("reason", reason);

            return json;
        } catch (JSONException ignored) {
            return new JSONObject();
        }
    }

    private void send(InetAddress address, int port, String message) {
        message += "\n";
        for (int i = 0; i < message.length(); i += bufLen) {
            byte[] buf = message.substring(i, (i + bufLen < message.length() ? i + bufLen : message.length())).getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);

            try {
                serverSocket.send(packet);
            } catch (IOException e) {
                System.err.println("Unable to send all command packets... Command aborted");
            }
        }
    }
}
