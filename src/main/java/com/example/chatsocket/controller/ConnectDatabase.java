package com.example.chatsocket.controller;

import com.example.chatsocket.model.User;

import javax.swing.*;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDatabase {
    private static Connection conn;
    private static String url = "jdbc:mysql://localhost:3306/chatsocket";
    private static String user = "root";
    private static String password = "admin";

    public static Connection getConnection() {
        if(conn == null){
            try{
                conn = DriverManager.getConnection(url, user, password);
            }catch (SQLException e){
                System.out.println("Failed "+ e.getMessage());
            }
        }

        return conn;
    }

    public static void freeConnection() {
        try{
            conn.close();
        }catch (SQLException e){
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static boolean registerUser(String username, String password) {
        boolean ck = false;
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM user WHERE username='" + username + "'");
            if (result.next()) {
                JOptionPane.showMessageDialog(null, "This user is already registered.");
            } else {
                String hashedPassword = hashPassword(password);
                Statement statement2 = getConnection().createStatement();
                statement2.executeUpdate("INSERT INTO user(username, password) VALUES ('" + username + "', '" + hashedPassword + "')");
                statement2.close();
                JOptionPane.showMessageDialog(null, "Registration successful. Please log in!");
                ck = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ck;
    }

    private static String hashPassword(String password) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for(byte b : hashedBytes) {
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new RuntimeException("Error hashing password: "+ e.getMessage());
        }
    }

    public static boolean loginUser(String username, String enteredPassword) {
        boolean success = false;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM user WHERE username=?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedPasswordFromDatabase = resultSet.getString("password");
                if (checkPassword(enteredPassword, hashedPasswordFromDatabase)) {
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Username or password is not valid");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username or password is not valid");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Do not call freeConnection() here
        }
        return success;
    }


    private static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return hashPassword(plainTextPassword).equals(hashedPassword);
    }

    //message
    public static void sendMessage(String message, String senderName, String receiverName, String type) {
        try {
            Statement statement = getConnection().createStatement();
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            int senderId = getUserIdByName(senderName);
            int receiverId = getUserIdByName(receiverName);
            statement.executeUpdate("INSERT INTO message(senderId,receiverId,content,timeStamp) VALUES ('" + senderId + "','"
                    + receiverId + "','" + message + "','" + timestamp + "')");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getUserIdByName(String name) {
        int userId = -1; //no user can be found
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT user_id FROM user WHERE username = '"+name+"'");

            if(resultSet.next()) {
                userId = resultSet.getInt("user_id");

            }

            resultSet.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userId;

    }

    public static boolean createGroup(String groupName, String adminGroup) {
        boolean success = false;
        int userId = getUserIdByName(adminGroup);

        try {
            Connection connection = getConnection();

            // Check if the group already exists
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM `group` WHERE groupName='" + groupName + "'");

            if (result.next()) {
                JOptionPane.showMessageDialog(null, "This group is already created.");
            } else {
                // Insert new group
                Statement insertStatement = connection.createStatement();
                insertStatement.executeUpdate("INSERT INTO `group` (groupName) VALUES ('" + groupName + "')", Statement.RETURN_GENERATED_KEYS);

                // Retrieve the generated groupId
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                int groupId = -1;
                if (generatedKeys.next()) {
                    groupId = generatedKeys.getInt(1);
                }

                insertStatement.close();

                if (groupId != -1) {
                    Date date = new Date();
                    Timestamp joinedAt = new Timestamp(date.getTime());
                    // Insert the adminGroup into the group_member table
                    String insertQuery = "INSERT INTO `group_member` (groupId, user_id, joinedAt, admin) VALUES (" + groupId + ", '" + userId + "', '" + joinedAt + "',1)";
                    statement.executeUpdate(insertQuery);

                    JOptionPane.showMessageDialog(null, "Creation successful. Please add members!");
                    success = true;
                }
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    public static Vector<String> getAllUsernames() {
        Vector<String> usernames = new Vector<>();
        try {
            Connection connection = getConnection();

            Statement statement = connection.createStatement();
            String query = "SELECT username FROM user";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                usernames.add(username);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }


    public static boolean addMember(String username, String groupName) {
        boolean ck = false;
        int userId = getUserIdByName(username);
        int groupId = getGroupIdByName(groupName);
        try {

                Date date = new Date();
                Timestamp joinedAt = new Timestamp(date.getTime());
                Statement statement2 = getConnection().createStatement();
                statement2.executeUpdate("INSERT INTO group_member(groupId,user_id,joinedAt) VALUES ('" + groupId + "', '" + userId + "', '" + joinedAt + "')");
                statement2.close();
                JOptionPane.showMessageDialog(null, "Registration successful. Please log in!");
                ck = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ck;
    }


    private static int getGroupIdByName(String name) {
        int groupId = -1; //no user can be found
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT groupId FROM `group` WHERE groupName = '" + name + "'");


            if(resultSet.next()) {
                groupId = resultSet.getInt("groupId");

            }

            resultSet.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return groupId;

    }

}
