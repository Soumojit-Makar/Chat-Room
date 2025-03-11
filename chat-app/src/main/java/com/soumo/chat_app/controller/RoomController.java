package com.soumo.chat_app.controller;

import com.soumo.chat_app.config.AppCon;
import com.soumo.chat_app.entities.Message;
import com.soumo.chat_app.entities.Room;
import com.soumo.chat_app.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-app")
@CrossOrigin(AppCon.FRONTEND_URL)
@Tag(
        name = "Room Controller",
        description = "Handles chat room operations such as creation, retrieval, and message retrieval."
)
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Create a new chat room.
     *
     * @param roomId the ID of the room to be created.
     * @return the created room or an error message if the room already exists.
     */
    @Operation(
            summary = "Create a new chat room",
            description = "Creates a new chat room with the given room ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "409",
                            description = "Created a new room ",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(
                                            implementation = Room.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Room already exists",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Room.class
                                    )
                            )
                    )
            }
    )

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {

        Room room = roomService.createRoom(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    /**
     * Retrieve details of a chat room by its ID.
     *
     * @param roomId the ID of the room to retrieve.
     * @return the room details or an error message if the room does not exist.
     */
    @Operation(
            summary = "Retrieve a chat room",
            description = "Fetches details of a chat room by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Return the room details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Room.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Room does not exist.",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(
                                            implementation = String.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable  String roomId) {
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room does not exist");
        }
        return ResponseEntity.ok(room);
    }

    /**
     * Get paginated messages of a specific room.
     *
     * @param roomId the ID of the room.
     * @param page   the page number (default: 0).
     * @param size   the size of the page (default: 20).
     * @return a list of messages or an error message if the room does not exist.
     */
    @Operation(
            summary = "Get messages of a chat room",
            description = "Fetches paginated messages for a specific chat room.",
            responses = {
                    @ApiResponse(
                       responseCode = "200",
                       description = "A list of messages",
                       content = @Content(
                               mediaType = "application/json",
                               schema = @Schema(
                                       implementation = Message[].class
                               )
                       )
                    )
            }
    )
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        List<Message> allMessages = roomService.getAllRooms(roomId, page, size);
//        log.info(allMessages.toString());

        return ResponseEntity.ok(allMessages);
    }
}
