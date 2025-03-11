package com.soumo.chat_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBroker implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker for routing messages.
     * Enables a simple in-memory broker with the `/topic` prefix
     * and sets the application destination prefix to `/app`.
     *
     * Client-side subscriptions: /topic/{destination}
     * Client-side message sending: /app/{destination}
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registers STOMP endpoints for WebSocket connections.
     * Enables SockJS as a fallback for browsers that do not support WebSockets.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-app")
                .setAllowedOrigins(AppCon.FRONTEND_URL) // Development origin
                .withSockJS();
    }

    /**
     * Configures WebSocket transport options for fine-tuning performance.
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(64 * 1024); // 64KB
        registration.setSendBufferSizeLimit(128 * 1024); // 128KB
        registration.setSendTimeLimit(15 * 1000); // 15 seconds
    }
}
