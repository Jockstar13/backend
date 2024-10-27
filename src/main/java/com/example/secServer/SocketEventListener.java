package com.example.secServer;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.secServer.event.*;
import com.example.secServer.models.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SocketEventListener {

    private final SocketIOServer server;
    private final Map<String, User> userSocketMap = new HashMap<>();

    public SocketEventListener(SocketIOServer server) {
        this.server = server;
        server.start();
    }

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("Client connected: " + client.getSessionId());
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        User user = userSocketMap.remove(client.getSessionId().toString());
        if (user != null) {
            String roomId = user.getRoomId();
            server.getRoomOperations(roomId).sendEvent(SocketEvent.USER_DISCONNECTED, user);
            client.leaveRoom(roomId);
        }
        System.out.println("Client disconnected: " + client.getSessionId());
    }

    @OnEvent(SocketEvent.JOIN_REQUEST)
    public void onJoinRequest(SocketIOClient client, JoinRequest joinRequest) {
        System.out.println("Received join request for room: " + joinRequest.getRoomId() + " by user: " + joinRequest.getUsername());
        String roomId = joinRequest.getRoomId();
        String username = joinRequest.getUsername();

        boolean usernameExists = userSocketMap.values().stream()
                .anyMatch(user -> user.getRoomId().equals(roomId) && user.getUsername().equals(username));

        if (usernameExists) {
            client.sendEvent(SocketEvent.USERNAME_EXISTS);
            return;
        }

        User user = new User(username, roomId, USER_CONNECTION_STATUS.ONLINE, 0, false, null, client.getSessionId().toString());
        userSocketMap.put(client.getSessionId().toString(), user);
        client.joinRoom(roomId);

        Map<String, User> usersInRoom = getUsersInRoom(roomId);
        client.sendEvent(SocketEvent.JOIN_ACCEPTED, new JoinResponse(user, usersInRoom));
        server.getRoomOperations(roomId).sendEvent(SocketEvent.USER_JOINED, user);
    }

    private Map<String, User> getUsersInRoom(String roomId) {
        Map<String, User> usersInRoom = new HashMap<>();
        for (Map.Entry<String, User> entry : userSocketMap.entrySet()) {
            if (entry.getValue().getRoomId().equals(roomId)) {
                usersInRoom.put(entry.getKey(), entry.getValue());
            }
        }
        return usersInRoom;
    }

    @OnEvent(SocketEvent.SYNC_FILE_STRUCTURE)
    public void onSyncFileStructure(SocketIOClient client, FileStructure fileStructure) {
        client.sendEvent(SocketEvent.SYNC_FILE_STRUCTURE, fileStructure);
    }

    @OnEvent(SocketEvent.DIRECTORY_CREATED)
    public void onDirectoryCreated(SocketIOClient client, DirectoryCreatedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.DIRECTORY_CREATED, event);
    }

    @OnEvent(SocketEvent.DIRECTORY_UPDATED)
    public void onDirectoryUpdated(SocketIOClient client, DirectoryUpdatedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.DIRECTORY_UPDATED, event);
    }

    @OnEvent(SocketEvent.DIRECTORY_RENAMED)
    public void onDirectoryRenamed(SocketIOClient client, DirectoryRenamedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.DIRECTORY_RENAMED, event);
    }

    @OnEvent(SocketEvent.DIRECTORY_DELETED)
    public void onDirectoryDeleted(SocketIOClient client, DirectoryDeletedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.DIRECTORY_DELETED, event);
    }

    @OnEvent(SocketEvent.FILE_CREATED)
    public void onFileCreated(SocketIOClient client, FileCreatedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.FILE_CREATED, event);
    }

    @OnEvent(SocketEvent.FILE_UPDATED)
    public void onFileUpdated(SocketIOClient client, FileUpdatedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.FILE_UPDATED, event);
    }

    @OnEvent(SocketEvent.FILE_RENAMED)
    public void onFileRenamed(SocketIOClient client, FileRenamedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.FILE_RENAMED, event);
    }

    @OnEvent(SocketEvent.FILE_DELETED)
    public void onFileDeleted(SocketIOClient client, FileDeletedEvent event) {
        String roomId = event.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.FILE_DELETED, event);
    }

    @OnEvent(SocketEvent.USER_OFFLINE)
    public void onUserOffline(SocketIOClient client, UserStatusEvent event) {
        User user = userSocketMap.get(client.getSessionId().toString());
        if (user != null) {
            user.setStatus(USER_CONNECTION_STATUS.OFFLINE);
            server.getRoomOperations(user.getRoomId()).sendEvent(SocketEvent.USER_OFFLINE, user);
        }
    }

    @OnEvent(SocketEvent.USER_ONLINE)
    public void onUserOnline(SocketIOClient client, UserStatusEvent event) {
        User user = userSocketMap.get(client.getSessionId().toString());
        if (user != null) {
            user.setStatus(USER_CONNECTION_STATUS.ONLINE);
            server.getRoomOperations(user.getRoomId()).sendEvent(SocketEvent.USER_ONLINE, user);
        }
    }

    @OnEvent(SocketEvent.SEND_MESSAGE)
    public void onSendMessage(SocketIOClient client, ChatMessage chatMessage) {
        String roomId = chatMessage.getRoomId();
        server.getRoomOperations(roomId).sendEvent(SocketEvent.RECEIVE_MESSAGE, chatMessage);
    }

    @OnEvent(SocketEvent.TYPING_START)
    public void onTypingStart(SocketIOClient client, TypingData typingData) {
        User user = userSocketMap.get(client.getSessionId().toString());
        if (user != null) {
            user.setTyping(true);
            server.getRoomOperations(user.getRoomId()).sendEvent(SocketEvent.TYPING_START, user);
        }
    }

    @OnEvent(SocketEvent.TYPING_PAUSE)
    public void onTypingPause(SocketIOClient client) {
        User user = userSocketMap.get(client.getSessionId().toString());
        if (user != null) {
            user.setTyping(false);
            server.getRoomOperations(user.getRoomId()).sendEvent(SocketEvent.TYPING_PAUSE, user);
        }
    }

    @OnEvent(SocketEvent.REQUEST_DRAWING)
    public void onRequestDrawing(SocketIOClient client) {
        User user = userSocketMap.get(client.getSessionId().toString());
        if (user != null) {
            server.getRoomOperations(user.getRoomId()).sendEvent(SocketEvent.REQUEST_DRAWING, user.getSocketId());
        }
    }

    @OnEvent(SocketEvent.SYNC_DRAWING)
    public void onSyncDrawing(SocketIOClient client, DrawingData drawingData) {
        server.getRoomOperations(drawingData.getRoomId()).sendEvent(SocketEvent.SYNC_DRAWING, drawingData);
    }

    @OnEvent(SocketEvent.DRAWING_UPDATE)
    public void onDrawingUpdate(SocketIOClient client, DrawingData drawingData) {
        server.getRoomOperations(drawingData.getRoomId()).sendEvent(SocketEvent.DRAWING_UPDATE, drawingData);
    }
}
