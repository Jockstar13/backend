package com.example.secServer.models;

import com.example.secServer.USER_CONNECTION_STATUS;

public class User {
    private String username;
    private String roomId;
    private USER_CONNECTION_STATUS status;
    private int cursorPosition;
    private boolean typing;
    private String currentFile;
    private String socketId;

    // Constructor
    public User(String username, String roomId, USER_CONNECTION_STATUS status, int cursorPosition, boolean typing, String currentFile, String socketId) {
        this.username = username;
        this.roomId = roomId;
        this.status = status;
        this.cursorPosition = cursorPosition;
        this.typing = typing;
        this.currentFile = currentFile;
        this.socketId = socketId;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public USER_CONNECTION_STATUS getStatus() { return status; }
    public void setStatus(USER_CONNECTION_STATUS status) { this.status = status; }
    public int getCursorPosition() { return cursorPosition; }
    public void setCursorPosition(int cursorPosition) { this.cursorPosition = cursorPosition; }
    public boolean isTyping() { return typing; }
    public void setTyping(boolean typing) { this.typing = typing; }
    public String getCurrentFile() { return currentFile; }
    public void setCurrentFile(String currentFile) { this.currentFile = currentFile; }
    public String getSocketId() { return socketId; }
    public void setSocketId(String socketId) { this.socketId = socketId; }
}
