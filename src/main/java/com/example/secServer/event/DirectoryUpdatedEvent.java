package com.example.secServer.event;

public class DirectoryUpdatedEvent {
    private String roomId;
    private String dirId;
    private String[] children; // assuming this is a list of directory contents


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }
}
