package rmi;

/**
 * Created by user on 03/12/2015.
 */
public class ServeurMain {

    //compile javac -d remiclasse Serveur.java Client.java rmi_objets/ListNames.java rmi_interfaces/interfacelist.java
    //start serveur java -classpath remiclasse -Djava.rmi.server.codebase=file:classDir/ example.hello.Server
    //start client java  -classpath remiclasse example.hello.Client

    public static void main(String[] args) {
        Serveur serveur=new Serveur("127.0.0.1",Integer.parseInt(args[0]), args[1]);
        serveur.run();
    }
}
