package com.soumo.chat_app.service.imp;

import com.soumo.chat_app.entities.Message;
import com.soumo.chat_app.entities.Room;
import com.soumo.chat_app.repositories.RoomRepo;
import com.soumo.chat_app.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImp implements RoomService {
    RoomRepo roomRepo;
    public RoomServiceImp(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }
    @Override
    public Room createRoom(String RoomId) {
        if (roomRepo.findByRoomId(RoomId).isPresent()) {
           return null;
        }
        Room room = new Room();
        room.setRoomId(RoomId);
        return roomRepo.save(room);
    }

    @Override
    public Room getRoom(String RoomId) {

        return roomRepo.findByRoomId(RoomId).orElse(null);
    }

    @Override
    public List<Message> getAllRooms(String roomId,int page, int size) {
        Room room= roomRepo.findByRoomId(roomId).orElse(null);
        if (room==null) {
            return null;
        }
        List<Message> messages = room.getMessages();
        if (messages.size()<20) {
            return messages;
        }
        int start = Math.max(0,messages.size()-(page-1)*size);
        int end = Math.min(messages.size(),start+size);
        return messages.subList(start, end);
    }
}
