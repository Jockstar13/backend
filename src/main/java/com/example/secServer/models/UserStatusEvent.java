package com.example.secServer.models;

import com.example.secServer.USER_CONNECTION_STATUS;

public class UserStatusEvent {
    private String roomId;
    private String socketId;
    private USER_CONNECTION_STATUS status;

    // Getters and Setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public USER_CONNECTION_STATUS getStatus() {
        return status;
    }

    public void setStatus(USER_CONNECTION_STATUS status) {
        this.status = status;
    }
}
