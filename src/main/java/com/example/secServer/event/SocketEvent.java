package com.example.secServer.event;

public class SocketEvent {
    public static final String JOIN_REQUEST = "join-request";
    public static final String JOIN_ACCEPTED = "join-accepted";
    public static final String USER_JOINED = "user-joined";
    public static final String USER_DISCONNECTED = "user-disconnected";
    public static final String USERNAME_EXISTS = "username-exists";
    public static final String SYNC_FILE_STRUCTURE = "sync-file-structure";
    public static final String DIRECTORY_CREATED = "directory-created";
    public static final String SEND_MESSAGE = "send-message";
    public static final String RECEIVE_MESSAGE = "receive-message";
    public static final String SYNC_DRAWING = "sync-drawing";
    public static final String TYPING_START = "typing-start";
    public static final String TYPING_PAUSE = "typing-pause";
    public static final String REQUEST_DRAWING = "request-drawing";
    public static final String DRAWING_UPDATE = "drawing-update";
    public static final String FILE_CREATED = "file-created";
    public static final String FILE_UPDATED = "file-updated";
    public static final String FILE_RENAMED = "file-renamed";
    public static final String FILE_DELETED = "file-deleted";
    public static final String DIRECTORY_UPDATED = "directory-updated";
    public static final String DIRECTORY_RENAMED = "directory-renamed";
    public static final String DIRECTORY_DELETED = "directory-deleted";
    public static final String USER_OFFLINE = "offline";
    public static final String USER_ONLINE = "online";
}