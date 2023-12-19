package com.example.chatsocket.model;

public class GroupMember {
    private int id;
    private int groupId;
    private int user_id;
    private int admin;

    public GroupMember(int id, int groupId, int user_id, int admin){
        this.id = id;
        this.groupId = groupId;
        this.user_id = user_id;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
}
