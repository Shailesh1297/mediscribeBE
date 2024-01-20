package org.mediscribe.controller;

import org.mediscribe.dto.Message;
import org.mediscribe.entity.User;
import org.mediscribe.service.MessageService;
import org.mediscribe.service.SessionStore;
import org.mediscribe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
public class WebSocketController {

    @Autowired
    SessionStore sessionStore;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @MessageMapping("/all")
    @SendTo("/topic/all")
    public Message<String> sendToAll(Message<String> message) {
        return message;
    }

    @MessageMapping("/private")
    public void sendToPrivate(@Payload Message<String> message) {
        this.messageService.sendMessageToUser(message);
    }


    @MessageMapping("/signal")
    public void webRtc(@Payload Message<Object> message) {
        this.messageService.signalUser(message);
    }

    @MessageMapping("/actives")
    public void refreshActiveUsers() {
        this.messageService.sendActiveUsers();
    }

    @MessageMapping("/stream")
    public void toggleStream(@Payload Message<String> message) {
        if (message.getType().equals("stream")) {
            if (message.getContent().equals("start")) {
                this.sessionStore.addStreamer(message.getSender());
            } else {
                this.sessionStore.removeStreamer(message.getSender());
            }
            this.messageService.sendStreamers();
        }
    }

    @MessageMapping("/streamers")
    public void streamers() {
        this.messageService.sendStreamers();
    }

    @EventListener
    public void onSocketConnected(SessionConnectedEvent connectedEvent) {
        String userId = null,alias = null;
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(connectedEvent.getMessage());
        GenericMessage<?> genericMessage = (GenericMessage<?>) sha.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if(nonNull(genericMessage)) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(genericMessage);
            userId = getUserId(headerAccessor);
            alias = getUserAlias(headerAccessor);

            User connectingUser = userService.getUserById(userId);
            sessionStore.addUserAndSession(connectingUser,sha.getSessionId());
            this.messageService.sendActiveUsers();
        }
    }

    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        sessionStore.removeSessionBySessionId(sha.getSessionId());
        this.messageService.sendActiveUsers();
    }

    private String getUserId(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        List<String> userIdValue = simpMessageHeaderAccessor.getNativeHeader("userId");
        return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
    }

    private String getUserAlias(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        List<String> userIdValue = simpMessageHeaderAccessor.getNativeHeader("alias");
        return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
    }

}
