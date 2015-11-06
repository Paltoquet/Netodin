package net;

import exceptions.ConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    ServerSocket serverSocket;

    public Server(int port) throws ConnectionException {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new ConnectionException("Can't create a server socket...");
        }
    }

    public void run() {
        while (true) {
            Socket client;

            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Error accept incoming connection.");
                System.err.println(e.getMessage());
                continue;
            }

            PrintWriter out;
            BufferedReader in;
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (IOException e) {
                System.err.println("can't instantiate input/output streams.");
                System.err.println(e.getMessage());
            }

            try {
                client.close();
            } catch (IOException e) {
                System.err.println("can't close client socket.");
                System.err.println(e.getMessage());
            }
        }
    }
}
