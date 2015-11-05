

import commands.Add;
import commands.Command;
import exceptions.CommandException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client{

    private Socket connexion;
    private PrintWriter writer;
    private BufferedInputStream reader;
    private Scanner sc;


    public Client(String host, int port){
        sc=new Scanner(System.in);
        try {
            connexion = new Socket(host, port);
            writer = new PrintWriter(connexion.getOutputStream(), true);
            reader = new BufferedInputStream(connexion.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void run() {

        while (sc.hasNext()) {
            StringTokenizer tokenizer=new StringTokenizer(sc.nextLine());
            Command commande= null;
            try {
                commande = getCommand(tokenizer.nextToken());
            } catch (CommandException e) {
                e.printStackTrace();
            }

            commande.parse(tokenizer);
            writer.write(commande.toString());
            //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
            writer.flush();

            System.out.println("Commande " + commande + " envoyée au serveur");

            //On attend la réponse
            String response = read();
            System.out.println( "Réponse reçue " + response);
        }

        writer.write("CLOSE");
        writer.flush();
        writer.close();
    }

    //Méthode qui permet d'envoyer des commandeS de façon aléatoire
    private Command getCommand(String cmd) throws CommandException {
        switch(cmd){
            case "add":
                return new Add();
            default:
                throw new CommandException("invalid command");
        }
    }

    //Méthode pour lire les réponses du serveur
    private String read(){
        String response = "";
        return response;
    }
}