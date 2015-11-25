package server.tcp;

import enums.Services;
import exceptions.CommunicationException;
import exceptions.Disconnect;
import org.json.JSONObject;
import server.AbstractServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ThreadServer extends AbstractServer implements Runnable
{
    /**
     * The socket of the client
     */
    private Socket socket;

    /**
     * The output buffer
     */
    private PrintWriter out;

    /**
     * The input buffer
     */
    private BufferedReader in;

    /**
     *
     * @param socket the socket of the client
     * @throws SocketException if the socket is null
     */
    public ThreadServer(Socket socket) throws SocketException {
        if (socket == null) {
            throw new SocketException("The socket is null.");
        }

        this.socket = socket;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            closeSocket();
            throw new SocketException("Can't instantiate input/output streams.");
        }
    }

    /**
     * The main thread which talks with
     * the client
     */
    public void run() {
        while (true) {
            try {
                JSONObject json = new JSONObject(receive());
                send(executeCommand(json).toString());

                if (json.has("service") && json.getString("service").equalsIgnoreCase(String.valueOf(Services.QUIT))) {
                    throw new Disconnect();
                }
            } catch (CommunicationException | Disconnect ignore) {
                closeSocket();
                break;
            }
        }
    }

    @Override
    public String receive() throws CommunicationException {
        try {
            String data = in.readLine();
            if (data == null) {
                throw new CommunicationException("Can't read from input stream.");
            }
            return data;
        } catch (IOException e) {
            throw new CommunicationException("Can't read from input stream.");
        }
    }

    @Override
    public void send(String message) throws CommunicationException {
        out.println(message);
    }

    /**
     * Close the client socket
     */
    private void closeSocket() {
        try {
            socket.close();
            System.out.println("[" + socket.getRemoteSocketAddress() + "] Client disconnected");
        } catch (IOException e) {
            System.err.println("Can't close client socket.");
            System.err.println(e.getMessage());
        }
    }
}
