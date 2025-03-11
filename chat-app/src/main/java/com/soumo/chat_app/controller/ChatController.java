package com.soumo.chat_app.controller;

import com.soumo.chat_app.config.AppCon;
import com.soumo.chat_app.entities.Message;
import com.soumo.chat_app.payload.MessageRequest;
import com.soumo.chat_app.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Tag(name = "Chat Controller", description = "Handles WebSocket-based chat messaging")
@CrossOrigin(AppCon.FRONTEND_URL)
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Handles incoming WebSocket messages and broadcasts them to the room.
     *
     * @param roomId the ID of the chat room
     * @param messageRequest the incoming message payload
     * @return the processed message to be sent to subscribers
     */

    @Operation(
            summary = "Send a message to a chat room",
            description = "Handles WebSocket messages and broadcasts them to the specified chat room."
    )
    @MessageMapping("/sendMessage/{roomId}") // Maps to /app/sendMessage/{roomId}
    @SendTo("/topic/room/{roomId}")          // Subscribers to this topic will receive the message
    public Message sendMessage(
            @DestinationVariable("roomId") String roomId,
            MessageRequest messageRequest) {
        return chatService.sendMessage(roomId, messageRequest);
    }
}
