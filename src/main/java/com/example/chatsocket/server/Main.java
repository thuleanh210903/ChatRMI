package com.example.chatsocket.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String []args){
        try {
            ChatServer chatServer = new ChatServer();
            LocateRegistry.createRegistry(4321);
            Naming.rebind("rmi://localhost:4321/remote",chatServer);
            ServerView serverView = new ServerView(chatServer);
            serverView.setVisible(true);
            System.out.println("Server Started ...");
        } catch (MalformedURLException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
