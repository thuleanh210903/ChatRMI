package com.example.chatsocket.client;

import com.example.chatsocket.server.InterfaceServer;

import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;


public class ChatView extends JFrame implements Runnable{
    private ChatClient client;
    private InterfaceServer server;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private Vector<String> listClients;
    private String name;
    private GroupLayout groupLayout;


    public ChatView(String name, String authorization, InterfaceServer server) {
        initComponents();

        this.server = server;
        this.name = name;


        if (authorization != null && authorization.equals("Administrator")) {
            System.out.print(authorization);
            listConnect.setComponentPopupMenu(jPopupMenu1);
        }

        this.setLocationRelativeTo(null);
        this.setTitle("Chat (" + name + ")");
        ImageIcon iconChat = new ImageIcon("img/chat.jpg");
        setIconImage(iconChat.getImage());

        groupLayout = new GroupLayout(jPanel1);
        jPanel1.setLayout(new GridLayout(100,1));
        jPanel1.setBorder(new EmptyBorder(5, 10, 10, 10));


        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(new JFrame(),
                        "Are you sure you want to close this chat ?", "Close chat?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        server.removeClient(name);
                    } catch (RemoteException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    System.exit(0);
                }else{

                }
            }
        });


        inputMsg.setForeground(Color.GRAY);
        inputMsg.setText("Enter your Message ...");
        inputMsg.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputMsg.getText().equals("Enter your Message ...")) {
                    inputMsg.setText("");
                    inputMsg.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (inputMsg.getText().isEmpty()) {
                    inputMsg.setForeground(Color.GRAY);
                    inputMsg.setText("Enter your Message ...");
                }
            }
        });


        listClients = new Vector<>();
        listConnect.setListData(listClients);

        try{
            client = new ChatClient(name,server,inputMsg,listMessage,jPanel1);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }


        Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                try {
                    int[] indices = listConnect.getSelectedIndices();
                    model.clear();
                    listClients = server.getListClientByName(name);
                    int i=0;
                    while(i<listClients.size()){
                        model.addElement(listClients.get(i));
                        i++;
                    }
                    listConnect.setModel(model);
                    listConnect.setSelectedIndices(indices);
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        };
        minuteur.schedule(tache,0,20000);
        this.setVisible(true);
    }



    @SuppressWarnings("unchecked")

    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        listConnectPanel = new javax.swing.JScrollPane();
        listConnect = new javax.swing.JList<>();
        inputMsgPanel = new javax.swing.JScrollPane();
        inputMsg = new javax.swing.JEditorPane();
        btnSend = new javax.swing.JButton();
        listMessagePanel = new javax.swing.JScrollPane();
        listMessage = new javax.swing.JTextArea();
        connectedLb = new JLabel();
        refreshBtn = new javax.swing.JButton();
        uploadFileBtn= new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        sharedFileLb = new JLabel();
        addGroupBtn = new JButton();
        stickerBtn = new JButton();
        stickerMenu = new JPopupMenu();


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

        listConnect.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        listConnect.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listConnect.setToolTipText("");
        listConnectPanel.setViewportView(listConnect);

//        inputMsg.setColumns(20);
//        inputMsg.setRows(5);
        inputMsg.setToolTipText("Enter your Message ...");
        inputMsg.setMargin(new java.awt.Insets(6, 0, 0, 16));
        inputMsgPanel.setViewportView(inputMsg);
        inputMsg.getAccessibleContext().setAccessibleName("Enter your Message ...");

        btnSend.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSend.setText("Send");
        btnSend.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        listMessage.setEditable(false);
        listMessage.setColumns(20);
        listMessage.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        listMessage.setRows(5);
        listMessage.setRequestFocusEnabled(false);
        listMessagePanel.setViewportView(listMessage);

        connectedLb.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        connectedLb.setText("Connected Clients");

        refreshBtn.setText("Refresh");
        refreshBtn.setActionCommand("");
        refreshBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });


        addGroupBtn.setText("Create group");
        addGroupBtn.setActionCommand("");
        addGroupBtn.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        addGroupBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGroupBtnActionPerform(e);
            }
        });

        uploadFileBtn.setIcon(new ImageIcon("img/file-upload.png"));

        uploadFileBtn.setToolTipText("upload File");
        uploadFileBtn.setBorderPainted(false);
        uploadFileBtn.setContentAreaFilled(false);
        uploadFileBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        uploadFileBtn.setDefaultCapable(false);
        uploadFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadFileBtn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        uploadFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFileBtnActionPerformed(evt);
            }
        });


        ArrayList<String> stickerPaths = new ArrayList<>(Arrays.asList(
                "img/happy.png", "img/cry.png", "img/love.png", "img/sad.png",
                "img/sticker1.png", "img/sticker2.png", "img/sticker3.png", "img/sticker4.png"
        ));

        ArrayList<ImageIcon> stickerIcons = new ArrayList<>();
        for (String path : stickerPaths) {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage();
            Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize the image to 50x50 pixels
            ImageIcon resizedIcon = new ImageIcon(resizedImg);
            resizedIcon.setDescription(path); // Set the description to the file path
            stickerIcons.add(resizedIcon);
        }




        stickerBtn.setIcon(new ImageIcon("img/icon.png"));
        stickerBtn.setToolTipText("upload icon");
        stickerBtn.setBorderPainted(false);
        stickerBtn.setContentAreaFilled(false);
        stickerBtn.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        stickerBtn.setDefaultCapable(false);
        stickerBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        stickerBtn.setMargin(new Insets(0,0,0,0));
        stickerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    stickerIconsBtnActionPerformed(evt, stickerIcons);
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

        jScrollPane4.setViewportView(jPanel1);

        sharedFileLb.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        sharedFileLb.setText("Shared Files");

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
                                                                .addComponent(jScrollPane4)
                                                                .addComponent(listMessagePanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
                                                        .addGap(27, 27, 27))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(inputMsgPanel, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(uploadFileBtn, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(stickerBtn, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                                ))
                                        .addComponent(sharedFileLb))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(connectedLb))
                                        .addComponent(listConnectPanel)
                                        .addComponent(refreshBtn, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                        .addComponent(addGroupBtn, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                        .addComponent(btnSend, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(connectedLb)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(listConnectPanel, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshBtn)
                                                .addComponent(addGroupBtn)
                                        )
                                        .addComponent(listMessagePanel, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16)
                                .addComponent(sharedFileLb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(uploadFileBtn, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(stickerBtn, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(inputMsgPanel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void stickerIconsBtnActionPerformed(ActionEvent evt, ArrayList<ImageIcon> stickerIcons) {
        JPopupMenu stickerMenu = new JPopupMenu();
        for (ImageIcon icon : stickerIcons) {
            JMenuItem menuItem = new JMenuItem(icon);
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ImageIcon selectedIcon = (ImageIcon) ((JMenuItem) e.getSource()).getIcon();
                    String imagePath = selectedIcon.getDescription();

                    try {
                        insertImageInTextArea(inputMsg, imagePath);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    btnSend.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            btnSendImageActionPerformed(evt,imagePath);
                        }
                    });
                }
            });
            stickerMenu.add(menuItem);
        }
        stickerMenu.show(stickerBtn, 0, stickerBtn.getHeight());
    }

    private void btnSendImageActionPerformed(java.awt.event.ActionEvent evt, String imagePath) {
        if(!inputMsg.getText().equals("")) {
            if(!inputMsg.getText().equals("Enter your Message ...")) {
                try {
                    client.sendImageMessage(listConnect.getSelectedValuesList(), imagePath); // Pass the image path to sendMessage
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                inputMsg.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please insert something to set your message", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please insert something to send your message", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }



    private void insertImageInTextArea(JEditorPane editorPane, String imagePath) throws MalformedURLException {
        HTMLEditorKit kit = new HTMLEditorKit();
        HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
        editorPane.setEditorKit(kit);
        editorPane.setDocument(doc);
//        URL baseUrl = getClass().getResource("/");
        kit.getStyleSheet().addRule("body { margin: 0; padding: 0; } img { display: block; }");
        URL url = new URL("file:///E:/projects/ChatSocket/" + imagePath);
        try {
            kit.insertHTML(doc, doc.getLength(), "<img src='"+url+"' width='50' height='50'/>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }



    private void addGroupBtnActionPerform(ActionEvent e) {
            AddGroupView addGroupView = new AddGroupView(name);
        }

    //send message
    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {
        if(!inputMsg.getText().equals("")){
            if(!inputMsg.getText().equals("Enter you Message ...")){
                client.sendMessage(listConnect.getSelectedValuesList());
                inputMsg.setText("");
            }else{
                JOptionPane.showMessageDialog(this,"Please insert something to set your message","Alert",JOptionPane.WARNING_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this,"Please insert something to send your message","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }


    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {
        Thread thread = new Thread(this);
        thread.start();
    }


    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            server.removeClient(listConnect.getSelectedValuesList());
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            server.blockClient(listConnect.getSelectedValuesList());
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //activer clients
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            server.reactiveClient(listConnect.getSelectedValuesList());
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //send file
    private void uploadFileBtnActionPerformed(java.awt.event.ActionEvent evt) {
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

    private javax.swing.JButton btnSend;
    private javax.swing.JEditorPane inputMsg;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton addGroupBtn;
    private javax.swing.JButton stickerBtn;
    private javax.swing.JButton uploadFileBtn;
    private JLabel sharedFileLb;
    private JLabel connectedLb;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane listConnectPanel;
    private javax.swing.JScrollPane inputMsgPanel;
    private javax.swing.JScrollPane listMessagePanel;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> listConnect;
    private javax.swing.JTextArea listMessage;
    private javax.swing.JPopupMenu stickerMenu = new JPopupMenu();

    @Override
    public void run() {
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