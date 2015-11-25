package communication;

import exceptions.CommunicationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Communication
{
    /**
     * The size of the buffer
     */
    public final static int BUFLEN = 256;

    /**
     * This class should not be instantiated
     */
    private Communication() {}

    /**
     * Send a message to the server/client in TCP mode
     *
     * @param writer where to send the message
     * @param message the message to send
     */
    public static void sendTCP(PrintWriter writer, String message) {
        writer.println(message);
    }

    /**
     * Get the response from the server in TCP mode
     *
     * @return a string which contains the response from the server
     * @throws CommunicationException if there is an error during the communication
     */
    public static String receiveTCP(BufferedReader reader) throws CommunicationException {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new CommunicationException("Can't get the response from the server");
        }
    }

    /**
     * Send a message to the server/client in TCP mode
     *
     * @param socket the associated DatagramSocket
     * @param address the address to send the message
     * @param port the port to send the message
     * @param message the message to send
     * @throws CommunicationException if there is an error during the communication
     */
    public static void sendUDP(DatagramSocket socket, InetAddress address, int port, String message) throws CommunicationException {
        message += "\n";
        for (int i = 0; i < message.length(); i += BUFLEN) {
            byte[] buffer = message.substring(i, (i + BUFLEN < message.length() ? i + BUFLEN : message.length())).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new CommunicationException("Unable to send all command packets... Command aborted");
            }
        }
    }

    /**
     * Get the response from the server in UDP Mode
     *
     * @return a string which contains the response from the server
     * @throws CommunicationException if there is an error during the communication
     */
    public static String receiveUDP(DatagramSocket socket) throws CommunicationException {
        String ret = "";

        while (true) {
            byte[] buf = new byte[BUFLEN];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new CommunicationException("Unable to receive one of the server's packets");
            }
            ret += new String(packet.getData(), 0, packet.getLength());

            if (ret.charAt(ret.length() - 1) == '\n') {
                return ret.substring(0, ret.length() - 1);
            }
        }
    }
}
