package com.smartnotify.config;

import com.smartnotify.security.JwtHandshakeInterceptor;
import com.smartnotify.security.StompPrincipalHandshakeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final StompPrincipalHandshakeHandler principalHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(principalHandshakeHandler)
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Messages FROM the client TO the server go to destinations prefixed "/app"
        registry.setApplicationDestinationPrefixes("/app");

        // Messages the server pushes TO clients go through these broker prefixes
        // "/topic" = broadcast to all subscribers, "/queue" = point-to-point (used with /user/**)
        registry.enableSimpleBroker("/topic", "/queue");

        // Prefix Spring uses internally to route user-specific messages
        registry.setUserDestinationPrefix("/user");
    }
}