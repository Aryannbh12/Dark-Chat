package com.example.dark_chat;

public class User {
    String uid, user_phone, user_name, profilePicture;

    public User()
    {
        
    }

    public User(String uid, String user_phone, String user_name, String profilePicture) {
        this.uid = uid;
        this.user_phone = user_phone;
        this.user_name = user_name;
        this.profilePicture = profilePicture;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
