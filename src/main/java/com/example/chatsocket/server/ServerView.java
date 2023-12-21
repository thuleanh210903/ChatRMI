package com.example.chatsocket.server;

import com.almasb.fxgl.net.Server;
import com.example.chatsocket.client.ChatClient;
import com.example.chatsocket.controller.ConnectDatabase;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.jar.JarEntry;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;


public class ServerView extends JFrame {
    private ChatClient client;
    private InterfaceServer server;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private Vector<String> listClients;
    private String name;
    private GroupLayout groupLayout;
    private ChatServer chatServer;

    public ServerView(ChatServer chatServer) throws MalformedURLException, NotBoundException, RemoteException {

        this.chatServer = chatServer;

        this.server = (InterfaceServer) Naming.lookup("rmi://localhost:4321/remote");

        initComponents();


        this.setLocationRelativeTo(null);
        this.setTitle("SERVER");

        groupLayout = new GroupLayout(jPanel1);
        jPanel1.setLayout(new GridLayout(100,1));
        jPanel1.setBorder(new EmptyBorder(5, 10, 10, 10));


        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(new JFrame(),
                        "Are you sure you want to close server ?", "Close server",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    dispose();
                    System.exit(0);
                }else{

                }
            }
        });



        listClients = new Vector<>();
        listConnect.setListData(listClients);

        Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                    int[] indices = listConnect.getSelectedIndices();
                    model.clear();
                    listClients = ConnectDatabase.getAllUsernames();
                    System.out.println(listClients);
                    int i=0;
                    while(i<listClients.size()){
                        model.addElement(listClients.get(i));
                        i++;
                    }
                    listConnect.setModel(model);
                    listConnect.setSelectedIndices(indices);

            }
        };
        minuteur.schedule(tache,0,20000);

    }



    @SuppressWarnings("unchecked")

    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        listConnectPanel = new javax.swing.JScrollPane();
        listConnect = new javax.swing.JList<>();
        serverLabel = new JLabel();
        deleteBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        hostLabel = new JLabel();






        jMenuItem1.setText("Remove Users");
        jMenuItem1.setActionCommand("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Block Users");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Reactive Users");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        listConnect.setFont(new java.awt.Font("Dialog", 0, 14));
        listConnectPanel.setPreferredSize(new Dimension(500, 400));
        listConnect.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listConnect.setToolTipText("");
        listConnectPanel.setViewportView(listConnect);



        serverLabel.setFont(new java.awt.Font("Dialog", 0, 16));
        serverLabel.setText("Server Started...");

        hostLabel.setFont(new Font("Dialog", 0, 16));
        hostLabel.setText("Host Name");

        deleteBtn.setText("Delete");
        deleteBtn.setActionCommand("");
        deleteBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedUser = listConnect.getSelectedValue();
                removeUser(selectedUser);
            }
        });





        jPanel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 113, Short.MAX_VALUE)
        );


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                )
                                                        .addGap(27, 27, 27))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(serverLabel)
                                                .addComponent(hostLabel)
                                        )
                                        .addComponent(listConnectPanel)
                                        .addComponent(deleteBtn, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                        )
                                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(serverLabel)
                                                .addComponent(hostLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(listConnectPanel, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(deleteBtn)
                                        ))
                                .addGap(16, 16, 16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void removeUser(String selectedUser) {
        if (selectedUser != null) {
            if (JOptionPane.showConfirmDialog(new JFrame(),
                    "Are you sure you want to delete this user ?", "Delete User?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                try {
                    server.deleteClient(selectedUser);
                    updateListClient(ConnectDatabase.getAllUsernames());
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }else{

            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member first", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateListClient(Vector<String> allUsernames) {
        listClients = allUsernames;
        model.clear();
        for(String client: listClients){
            model.addElement(client);
        }
        listConnect.setModel(model);
    }


    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
//        try {
//            server.removeClient(listConnect.getSelectedValuesList());
//        } catch (RemoteException ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
    }


    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
//        try {
//            server.blockClient(listConnect.getSelectedValuesList());
//        } catch (RemoteException ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
    }

    //activer clients
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
//        try {
//            server.reactiveClient(listConnect.getSelectedValuesList());
//        } catch (RemoteException ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
    }

    //send file
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            String[] extension = file.getName().split("\\.");
            System.out.println(extension.length);
            if(extension[extension.length - 1].equals("txt")||
                    extension[extension.length - 1].equals("java")||
                    extension[extension.length - 1].equals("php")||
                    extension[extension.length - 1].equals("c")||
                    extension[extension.length - 1].equals("cpp")||
                    extension[extension.length - 1].equals("xml")||
                    extension[extension.length - 1].equals("exe")||
                    extension[extension.length - 1].equals("png")||
                    extension[extension.length - 1].equals("jpg")||
                    extension[extension.length - 1].equals("jpeg")||
                    extension[extension.length - 1].equals("pdf")||
                    extension[extension.length - 1].equals("jar")||
                    extension[extension.length - 1].equals("rar")||
                    extension[extension.length - 1].equals("zip")
            ){
                try {
                    ArrayList<Integer> inc;
                    try (FileInputStream in = new FileInputStream(file)) {
                        inc = new ArrayList<>();
                        int c=0;
                        while((c=in.read()) != -1) {
                            inc.add(c);
                        }
                    }
                    server.broadcastMessage(inc, listClients,file.getName());
                } catch (FileNotFoundException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

                JLabel jfile = new JLabel(file.getName() + " Uploaded ...");
                jPanel1.add(jfile);
                jPanel1.repaint();
                jPanel1.revalidate();
            }else{
                JOptionPane.showMessageDialog(this,"You can only upload file have an extension like: xml,exe,jpg,png,jpeg,pdf,c,cpp,jar,java,txt,php ","Alert",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private javax.swing.JButton deleteBtn;
    private JLabel hostLabel;
    private JLabel serverLabel;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane listConnectPanel;
    private javax.swing.JList<String> listConnect;



    public  void run() {
        try {
            model.clear();
            listClients = server.getListClientByName(name);
            int i=0;
            while(i<listClients.size()){
                model.addElement(listClients.get(i));
                i++;
            }
            listConnect.setModel(model);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
}