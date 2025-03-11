package com.soumo.chat_app.repositories;

import com.soumo.chat_app.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepo extends MongoRepository<Room, String>{
    //get room using room id
    Optional<Room> findByRoomId(String roomId);
}
