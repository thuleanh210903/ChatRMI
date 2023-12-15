package com.example.chatsocket.client;

import com.example.chatsocket.controller.ConnectDatabase;
import com.example.chatsocket.model.User;
import com.example.chatsocket.server.InterfaceServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatClient extends UnicastRemoteObject implements InterfaceClient{
    private final InterfaceServer server;
    private final String name;
    private final JTextArea input;
    private final JTextArea output;
    private final JPanel jpanel;


    public ChatClient(String name , InterfaceServer server,JTextArea jtext1,JTextArea jtext2,JPanel jpanel) throws RemoteException{
        this.name = name;
        this.server = server;
        this.input = jtext1;
        this.output = jtext2;
        this.jpanel = jpanel;
        server.addClient(this);
    }


    @Override
    public void registerUser(User user) throws RemoteException {
        ConnectDatabase.registerUser(user.getUserName(), user.getPassword());
    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        output.setText(output.getText() + "\n" + message);
    }


    @Override
    public void retrieveMessage(String filename, ArrayList<Integer> inc) throws RemoteException {
        JLabel label = new JLabel("<HTML><U><font size=\"4\" color=\"#365899\">" + filename + "</font></U></HTML>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    FileOutputStream out;
                    String separator;
                    if(System.getProperty("os.name").startsWith("Linux") || System.getProperty("os.name").startsWith("MacOS")) separator = "/";
                    else separator = "\\";
                    out = new FileOutputStream(System.getProperty("user.home") + separator + filename);
                    String[] extension = filename.split("\\.");
                    for (int i = 0; i<inc.size(); i++) {
                        int cc = inc.get(i);
                        if(extension[extension.length - 1].equals("txt")||
                                extension[extension.length - 1].equals("java")||
                                extension[extension.length - 1].equals("php")||
                                extension[extension.length - 1].equals("c")||
                                extension[extension.length - 1].equals("cpp")||
                                extension[extension.length - 1].equals("xml")
                        )
                            out.write((char)cc);
                        else{
                            out.write((byte)cc);
                        }
                    }
                    out.flush();
                    out.close();
                    JOptionPane.showMessageDialog(new JFrame(),"your file saved at " + System.getProperty("user.home") + separator + filename,"File Saved",JOptionPane.INFORMATION_MESSAGE);
                } catch (FileNotFoundException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        jpanel.add(label);
        jpanel.repaint();
        jpanel.revalidate();
    }


//    @Override
//    public void sendMessage(List<String> list) {
//        try {
//            String message = name + " : " + input.getText();
//            String senderName = name;
////            server.broadcastMessage(name + " : " + input.getText(),list);
//
//            for (String recipient: list) {
//                String recipientName = recipient;
//
//                if(senderName != null && recipientName !=null) {
//                    server.broadcastMessage(senderName + " : " + message, list);
//                    ConnectDatabase.sendMessage(message, senderName, recipientName, "private");
//                }
//            }
//        } catch (RemoteException ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
//    }

    @Override
    public void sendMessage(List<String> list) {
        try {
            String message = name + " : " + input.getText();
            String senderName = name;

            if (senderName != null) {
                if (list.isEmpty()) {
                    // Gửi tin nhắn đến tất cả client kết nối
                    server.broadcastMessage(senderName + " : " + message, null);
                } else {
                    // Gửi tin nhắn chỉ đến các client được chọn
                    server.broadcastMessage(senderName + " : " + message, list);
                }

                // Lưu thông điệp vào cơ sở dữ liệu cho từng người nhận
                for (String recipient : list) {
                    String recipientName = recipient;
                    ConnectDatabase.sendMessage(message, senderName, recipientName, "private");
                }
            }
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public void closeChat(String message) throws RemoteException {
        input.setEditable(false);
        input.setEnabled(false);
        JOptionPane.showMessageDialog(new JFrame(),message,"Alert",JOptionPane.WARNING_MESSAGE);
    }


    @Override
    public void openChat() throws RemoteException {
        input.setEditable(true);
        input.setEnabled(true);
    }

}
