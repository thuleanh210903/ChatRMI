package com.example.chatsocket.client;

import com.example.chatsocket.controller.ConnectDatabase;
import com.example.chatsocket.model.User;
import com.example.chatsocket.server.InterfaceServer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterView extends JFrame{
    private InterfaceServer server;
    private InterfaceClient clientServer;

    public RegisterView() {
        initComponents();
        this.setLocationRelativeTo(null);

        try {
            server = (InterfaceServer) Naming.lookup("rmi://localhost:4321/remote");
            clientServer = new ChatClient("YourClientName", server, new JTextArea(), new JTextArea(), new JPanel());
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        registerBtn = new JButton();
        usernameLb = new JLabel();
        passwordLb = new JLabel();
        usernameField = new JTextField();
        passwordField = new JTextField();
        jComboBox = new JComboBox<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Register");
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setResizable(false);

        registerBtn.setFont(new Font("Dialog", 1,15));
        registerBtn.setText("Register");
        registerBtn.setToolTipText("");
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser(e);
            }
        }) ;

        usernameLb.setFont(new Font("Dialog", 1, 14));
        usernameLb.setText("Username");

        passwordLb.setFont(new Font("Dialog", 1, 14));
        passwordLb.setText("Password");

//        usernameField.addActionListener(new java.awt.event.ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                checkUserName();
//            }
//        });
//
        jComboBox.setFont(new Font("Dialog", 1, 14));
        jComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Register User"}));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(usernameLb)
                                                        .addComponent(passwordLb))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(usernameField)
                                                        .addComponent(passwordField)
                                                        .addComponent(jComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(124, 124, 124)
                                                .addComponent(registerBtn, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(57, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameLb)
                                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLb)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addComponent(jComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(registerBtn)
                                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);


    }

    private void registerUser(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try{
                User user = new User(username, password);
                clientServer.registerUser(user);
                JOptionPane.showMessageDialog(this, "User register successfully");
                new LoginView();
        }catch (RemoteException ex){
            Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(this, "Error Registering user: "+ ex.getMessage());
        }
    }


    public static void main(String args[]){

        EventQueue.invokeLater(() -> {
            new RegisterView().setVisible(true);
        });
    }
    private JButton registerBtn;
    private JComboBox<String> jComboBox;
    private JLabel usernameLb;
    private JLabel passwordLb;
    private JTextField usernameField;
    private JTextField passwordField;
}
