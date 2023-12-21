package com.example.chatsocket.server;

import com.example.chatsocket.client.InterfaceClient;
import com.example.chatsocket.controller.ConnectDatabase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatServer extends UnicastRemoteObject implements InterfaceServer{
    private final ArrayList<InterfaceClient> clients;
    private final ArrayList<InterfaceClient> blockedClients;


    public ChatServer() throws RemoteException{
        super();
        this.clients = new ArrayList<>();
        blockedClients = new ArrayList<>();
    }


//    @Override
//    public synchronized void broadcastMessage(String message,List<String> list) throws RemoteException {
//        if(list.isEmpty()){
//            int i= 0;
//            while (i < clients.size()){
//                clients.get(i++).retrieveMessage(message);
//            }
//        }else{
//            for (InterfaceClient client : clients) {
//                for(int i=0;i<list.size();i++){
//                    if(client.getName().equals(list.get(i))){
//                        client.retrieveMessage(message);
//                    }
//                }
//            }
//        }
//    }
    @Override
    public synchronized void broadcastMessage(String message, List<String> list) throws RemoteException {
        if (list == null) {
            for (InterfaceClient client : clients) {
                client.retrieveMessage(message);
            }
        } else {
            for (InterfaceClient client : clients) {
                for (String recipient : list) {
                    if (client.getName().equals(recipient)) {
                        client.retrieveMessage(message);
                    }
                }
            }
        }
    }




    @Override
    public synchronized void broadcastMessage(ArrayList<Integer> inc, List<String> list,String filename) throws RemoteException {
        if(list.isEmpty()){
            int i= 0;
            while (i < clients.size()){
                clients.get(i++).retrieveMessage(filename,inc);
            }
        }else{
            for (InterfaceClient client : clients) {
                for(int i=0;i<list.size();i++){
                    if(client.getName().equals(list.get(i))){
                        client.retrieveMessage(filename,inc);
                    }
                }
            }
        }
    }


    @Override
    public synchronized void addClient(InterfaceClient client) throws RemoteException {
        this.clients.add(client);
    }


    @Override
    public synchronized Vector<String> getListClientByName(String name) throws RemoteException {
        Vector<String> list = new Vector<>();
        for (InterfaceClient client : clients) {
            if(!client.getName().equals(name)){
                list.add(client.getName());
            }
        }
        return list;
    }


    @Override
    public synchronized void blockClient(List<String> clients){
        for(int j=0;j<this.clients.size();j++){
            for(int i=0;i<clients.size();i++){
                try {
                    if(this.clients.get(j).getName().equals(clients.get(i))){
                        this.clients.get(j).closeChat(clients + " you are blocked by admin");
                        blockedClients.add(this.clients.get(j));
                    }
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }


    @Override
    public synchronized void removeClient(List<String> clients){
        for(int j=0;j<this.clients.size();j++){
            for(int i=0;i<clients.size();i++){
                try {
                    if(this.clients.get(j).getName().equals(clients.get(i))){
                        this.clients.get(j).closeChat(clients.get(i) + " you are removed from the chat");
                        this.clients.remove(j);
                    }
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }


    @Override
    public synchronized void removeClient(String client){
        for(int j=0;j<this.clients.size();j++){
            try {
                if(this.clients.get(j).getName().equals(clients)){
                    this.clients.remove(j);
                }
            } catch (RemoteException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

    }

    @Override
    public synchronized void deleteClient(String clientName){
        ConnectDatabase.removeClient(clientName);
    }

    @Override
    public synchronized void reactiveClient(List<String> clients) throws RemoteException {
        for(int j=0;j<this.blockedClients.size();j++){
            for(int i=0;i<clients.size();i++){
                try {
                    if(this.blockedClients.get(j).getName().equals(clients.get(i))){
                        this.blockedClients.get(j).openChat();
                        this.blockedClients.remove(j);
                    }
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public boolean checkLogin(String username, String password) throws RemoteException {
        return ConnectDatabase.loginUser(username, password);

    }
}
