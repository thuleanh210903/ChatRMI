package com.example.chatsocket.server;

import com.example.chatsocket.client.InterfaceClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public interface InterfaceServer extends Remote{

    void broadcastMessage(String message,List<String> list) throws RemoteException;


    void broadcastMessage(ArrayList<Integer> inc,List<String> list,String filename) throws RemoteException;


    Vector<String> getListClientByName(String name) throws RemoteException;


    void addClient(InterfaceClient client) throws RemoteException;


    void blockClient(List<String> clients) throws RemoteException;


    void removeClient(List<String> clients) throws RemoteException;


    void removeClient(String clients) throws RemoteException;

    void deleteClient(String client) throws RemoteException;


    void reactiveClient(List<String> clients) throws RemoteException;


    boolean checkLogin(String username, String password) throws RemoteException;
}