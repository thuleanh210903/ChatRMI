package com.example.chatsocket.client;

import com.example.chatsocket.model.Group;
import com.example.chatsocket.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface InterfaceClient extends Remote{
    void registerUser(User user) throws RemoteException;

    void createGroup(Group group, String adminGroup) throws RemoteException;

    void retrieveMessage(String message) throws RemoteException;


    void retrieveMessage(String filename,ArrayList<Integer> inc) throws RemoteException;


    void sendMessage(List<String> list) throws RemoteException;


    String getName()throws RemoteException;


    void closeChat(String message) throws RemoteException;


    void openChat() throws RemoteException;

    void addMember(String username, String groupName) throws RemoteException;

    void sendImageMessage(List<String> list, String imagePath) throws RemoteException;
}

