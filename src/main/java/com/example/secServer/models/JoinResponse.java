package com.example.secServer.models;

import java.util.Map;

public class JoinResponse {
    private User user;
    private Map<String, User> users;

    public JoinResponse(User user, Map<String, User> users) {
        this.user = user;
        this.users = users;
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}

