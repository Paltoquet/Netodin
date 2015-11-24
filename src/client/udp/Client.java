package client.udp;

import client.services.Service;
import enums.Services;
import exceptions.ConnectionException;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public class Client
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
     * the buffer's size
     */
    private final static int bufLen = 256;

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
        } catch (UnknownHostException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    /**
     * Run the interaction with the client
     */
    public void run() {
        System.out.print("Commande : ");
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            try {
                client = new DatagramSocket();
            } catch (SocketException e) {
                System.err.println("can't create Socket");
            }
            String service = sc.nextLine();
            Service command;

            try {
                command = Services.getClientService(service);
                command.initialize(sc);
            } catch (ServiceException e) {
                System.err.println("Error : " + e.getMessage());
                System.err.flush();
                System.out.print("Commande : ");
                continue;
            }

            if (service.equalsIgnoreCase(String.valueOf(Services.QUIT))) {
                System.out.println("Bye !");
                break;
            }

            DatagramPacket packet;
            String toSend = command.toString() + "\n";

            for (int i = 0; i < toSend.length(); i += bufLen) {
                byte[] buffer = toSend.substring(i, (i + bufLen < toSend.length() ? i + bufLen : toSend.length())).getBytes();
                packet = new DatagramPacket(buffer, buffer.length, address, port);

                try {
                    client.send(packet);
                } catch (IOException e) {
                    System.err.println("Unable to send all command packets... Command aborted");
                }
            }

            try {
                command.parseResult(new JSONObject(getResponse()));
            } catch(JSONException e) {
                System.err.println("Bad formatted json");
                System.err.flush();
            }

            System.out.print("Commande : ");
            client.close();
        }
    }

    /**
     * Get the response from the server
     *
     * @return a string which contains the response from the server
     */
    public String getResponse(){
        String ret = "";

        while (true) {
            byte[] buf = new byte[bufLen];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                client.receive(packet);
            } catch (IOException e) {
                System.err.println("Unable to receive one of the server's packets");
                return "";
            }
            ret += new String(packet.getData(), 0, packet.getLength());

            if (ret.charAt(ret.length() - 1) == '\n') {
                return ret.substring(0, ret.length() - 1);
            }
        }
    }
}