

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

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
            String commande=getCommand();
            writer.write(commande);
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
    private String getCommand(){
        String commande=sc.nextLine();
        return commande;
    }

    //Méthode pour lire les réponses du serveur
    private String read(){
        String response = "";
        return response;
    }
}