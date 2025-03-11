package com.soumo.chat_app.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "room")
@Schema(description = "Represents a chat room where users can send and receive messages.")
public class Room {
    @Schema(description = "Unique identifier for the room in MongoDB.", example = "65d7e2c4567a9b3e4c2f5d1a")
    @Id
    private String id;//mongo db :unique identifier
    @Schema(description = "Custom room identifier.", example = "general-chat")
    private String roomId;
    @Schema(description = "List of messages exchanged in this room.")
    private List<Message> messages =new ArrayList<>();
    public Room() {}
    public Room(String roomId, List<Message> messages) {
        this.roomId = roomId;
        this.messages = messages;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public void addMessage(Message message) {
        this.messages.add(message);
    }


}
