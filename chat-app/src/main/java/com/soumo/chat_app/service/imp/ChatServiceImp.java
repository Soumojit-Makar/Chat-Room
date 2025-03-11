package com.soumo.chat_app.service.imp;

import com.soumo.chat_app.entities.Message;
import com.soumo.chat_app.entities.Room;
import com.soumo.chat_app.payload.MessageRequest;
import com.soumo.chat_app.repositories.RoomRepo;
import com.soumo.chat_app.service.ChatService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ChatServiceImp implements ChatService {
    private RoomRepo roomRepo;
    public ChatServiceImp(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }
    @Override
    public Message sendMessage(String roomId,MessageRequest messageRequest) {
        Room room=roomRepo.findByRoomId(roomId).orElse(null);
        Message message=new Message();
        message.setContent(messageRequest.getContent());
        message.setSender(messageRequest.getSender());
        message.setTimestamp(LocalDateTime.now());
        if (room!=null) {
            room.getMessages().add(message);
            roomRepo.save(room);
        }
        else {
            throw new RuntimeException("Room not found");
        }
        return message;
    }
}
