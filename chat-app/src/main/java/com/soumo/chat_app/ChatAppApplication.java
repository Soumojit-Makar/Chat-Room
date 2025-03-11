package com.soumo.chat_app;

import com.soumo.chat_app.entities.Room;
import com.soumo.chat_app.repositories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication

public class ChatAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChatAppApplication.class, args);
	}
	@Autowired
	RoomRepo roomRepo;
	@Override
	public void run(String... args) throws Exception {
//		Room room = new Room();
//		room.setRoomId("Room2");
//		roomRepo.save(room);
//		System.out.println("Rooms in database:");
//		roomRepo.findAll().forEach(System.out::println);
//
//		Optional<Room> room1 = roomRepo.findByRoomId("Room2");
//		if (room1.isPresent()) {
//			System.out.println("Room 1: " + room1.get().getRoomId());
//		}
//		else {
//			System.out.println("Room 1 not found");
//		}

	}

}
