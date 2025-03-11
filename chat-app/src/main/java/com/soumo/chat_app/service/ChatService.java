package com.soumo.chat_app.service;

import com.soumo.chat_app.entities.Message;
import com.soumo.chat_app.payload.MessageRequest;

public interface ChatService {
    Message sendMessage(String roomId,MessageRequest messageRequest);
}
