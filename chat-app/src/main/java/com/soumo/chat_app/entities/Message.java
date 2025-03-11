package com.soumo.chat_app.entities;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Represents a chat message exchanged between users.")
public class Message {
    @Schema(description = "Sender of the message", example = "user123")
    private String sender;
    @Schema(description = "Receiver of the message", example = "user456")
    private String receiver;
    @Schema(description = "Content of the message", example = "Hello, how are you?")
    private String content;
    @Schema(description = "Timestamp when the message was sent", example = "2025-02-18T14:30:00")
    private LocalDateTime timestamp;
    public Message(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = LocalDateTime.now();
    }
    public Message() {}
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
