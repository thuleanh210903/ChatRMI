package com.example.chatsocket.client;

import com.example.chatsocket.model.Group;
import com.example.chatsocket.server.InterfaceServer;
import kotlin.RequiresOptIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddGroupView extends JFrame {
    private InterfaceServer server;
    private InterfaceClient clientServer;
    private String adminGroup;

    public AddGroupView(String adminGroup) {

        initComponents();

        this.adminGroup = adminGroup;
        this.setLocationRelativeTo(null);

        try {
            server = (InterfaceServer) Naming.lookup("rmi://localhost:4321/remote");
            clientServer = new ChatClient("YourClientName", server, new JTextArea(), new JTextArea(), new JPanel());
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            ex.printStackTrace(); // Print the stack trace for debugging
            System.out.println("Error: " + ex.getMessage());
        }

        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        addGroupBtn = new JButton();
        groupNameLb = new JLabel();
        groupNameField = new JTextField();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Group " + adminGroup);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setResizable(false);

        addGroupBtn.setFont(new Font("Dialog", 1,15));
        addGroupBtn.setText("Create");
        addGroupBtn.setToolTipText("");
        addGroupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addGroupBtn.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                createGroup(e);
            }
        }) ;

        groupNameLb.setFont(new Font("Dialog", 1, 14));
        groupNameLb.setText("Group Name");


        groupNameField.setPreferredSize(new Dimension(200, 27));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);


        // Modify the layout code to set the preferred size for the text field
        // Modify the layout code for horizontal alignment of the "Create" button
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(groupNameLb))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(groupNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(124, 124, 124)
                                                .addComponent(addGroupBtn, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(57, Short.MAX_VALUE))
        );

// Modify the layout code for vertical alignment of the "Create" button
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(groupNameLb)
                                        .addComponent(groupNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addComponent(addGroupBtn)
                                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);


    }

    private void createGroup(ActionEvent e) {
        String nameGroup = groupNameField.getText();
        try{
            Group group = new Group(nameGroup);
            clientServer.createGroup(group, adminGroup);
            JOptionPane.showMessageDialog(this, "Create Group successfully");

            AddMemberView addMemberView = new AddMemberView(nameGroup, clientServer);
            addMemberView.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(AddGroupView.class.getName()).log(Level.SEVERE,null,ex);
            JOptionPane.showMessageDialog(this, "Error"+ ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

//    private void registerUser(ActionEvent e) {
//        String username = groupNameField.getText();
//
//
//        try{
//            User user = new User(username, password);
//            clientServer.registerUser(user);
//            JOptionPane.showMessageDialog(this, "User register successfully");
//        }catch (RemoteException ex){
//            Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);
//
//            JOptionPane.showMessageDialog(this, "Error Registering user: "+ ex.getMessage());
//        }
//    }



    private JButton addGroupBtn;
    private JLabel groupNameLb;
    private JTextField groupNameField;

}
