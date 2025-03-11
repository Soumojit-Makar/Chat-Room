package com.soumo.chat_app.service;

import com.soumo.chat_app.entities.Message;
import com.soumo.chat_app.entities.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room createRoom(String RoomId);
    Room getRoom(String RoomId);
    List<Message> getAllRooms(String roomId,int page,int size);

}
