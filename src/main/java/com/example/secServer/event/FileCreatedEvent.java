package com.example.secServer.event;

public class FileCreatedEvent {
    private String roomId;
    private String parentDirId;
    private String newFile;


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getParentDirId() {
        return parentDirId;
    }

    public void setParentDirId(String parentDirId) {
        this.parentDirId = parentDirId;
    }

    public String getNewFile() {
        return newFile;
    }

    public void setNewFile(String newFile) {
        this.newFile = newFile;
    }
}
