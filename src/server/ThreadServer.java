package server;

import enums.Services;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;
import server.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ThreadServer implements Runnable
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
    ThreadServer(Socket socket) throws SocketException {
        if (socket == null) {
            throw new SocketException("The socket is null.");
        }

        this.socket = socket;
    }

    /**
     * The main thread which talks with
     * the client
     */
    public void run() {
        while (true) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("can't instantiate input/output streams.");
                System.err.println(e.getMessage());
                closeSocket();
                return ;
            }

            JSONObject json;
            Command command;
            try {
                json = new JSONObject(in.readLine());
                command = Services.getServerService(json.getString("service"));
            } catch (IOException e) {
                closeSocket();
                break;
            } catch (JSONException e) {
                sendError("This service does not exist");
                continue;
            } catch (ServiceException e) {
                sendError(e.getMessage());
                continue;
            }

            command.execute(json);
            if (command.isSuccess()) {
                out.println(command.getResult());
            } else {
                out.println(command.getError());
            }

            if (json.getString("service").equalsIgnoreCase(String.valueOf(Services.QUIT))) {
                closeSocket();
                break;
            }
        }
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

    /**
     * Send a json error to the client
     *
     * @param reason the reason of the error
     */
    private void sendError(String reason) {
        try {
            JSONObject json = new JSONObject();
            json.put("result", "NOK");
            json.put("result", reason);

            out.println(json.toString());
        } catch (JSONException ignored) {}
    }
}
