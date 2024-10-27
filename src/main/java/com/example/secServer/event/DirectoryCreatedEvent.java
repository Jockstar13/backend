package com.example.secServer.event;

public class DirectoryCreatedEvent {
    private String roomId;
    private String directoryName;


    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getDirectoryName() { return directoryName; }
    public void setDirectoryName(String directoryName) { this.directoryName = directoryName; }
}
