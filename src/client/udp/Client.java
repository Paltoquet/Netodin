package client.udp;

import client.services.Service;
import enums.Services;
import exceptions.ConnectionException;
import exceptions.ServiceException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;


public class Client
{

    private boolean finReception=false;
    private int _port;
    private InetAddress adresse;
    public static int size=256;
    private DatagramSocket client;
    private byte[] buffer;
    /**
     *
     * @param host the machine to contact
     * @param port the port of the TCP connection
     * @throws ConnectionException if the server is unreachable
     */
    public Client(String host, int port) throws ConnectionException {
        try {
            InetAddress adresse = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        buffer=new byte[256];
        _port=port;

    }

    /**
     * Run the interaction with the client
     */
    public void run() {
        System.out.print("Commande : ");
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            try {
                client=new DatagramSocket();
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
            String envoie=command.toString() + "\n";

            for (int i = 0; i < envoie.length(); i += 256) {
                byte[] buffer = envoie.substring(i, (i+255 < envoie.length() ? i+255 : envoie.length() - 1)).getBytes();
                packet=new DatagramPacket(buffer,buffer.length,adresse,_port);
                packet.setData(buffer);
                try {
                    client.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }

//            int i=0;
//            while(i<buff.length) {
//                for (int j = 0; j < 255 || i==buff.length; i++, j++) {
//                    buffer[j] = buff[i];
//                }
//                packet.setData(buffer);
//                try {
//                    client.send(packet);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            //writer.println(command.toString());

            String reponse=buildReponse();

            try {
                command.parseResult(new JSONObject(reponse.substring(0,reponse.length()-2)));
            } catch(JSONException e) {
                System.err.println("Bad formatted json");
                System.err.flush();
            }

            System.out.print("Commande : ");
            finReception=false;
            client.close();
        }
    }

    public String buildReponse(){
        String reponse="";
        DatagramPacket recu=new DatagramPacket(buffer,size,adresse,_port);
        while(!finReception) {
            try {
                client.receive(recu);
                String tmp = new String(recu.getData());
                reponse+=tmp;
                if(tmp.charAt(tmp.length()-1)=='\n'){
                    finReception=true;
                }
            } catch (IOException e) {
                System.out.println("erreur reception packet");
            }
        }
        return reponse;
    }
}