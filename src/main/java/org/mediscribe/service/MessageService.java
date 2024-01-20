package org.mediscribe.service;

import org.mediscribe.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SessionStore sessionStore;

    public void sendMessageToUser(Message<String> message) {
        String sessionId = sessionStore.getSessionId(message.getRecipient());
        messagingTemplate.convertAndSendToUser(sessionId,"/topic/private",message,sessionStore.createHeader(sessionId));
    }

    public void signalUser(Message<Object> signal){
        String sessionId = sessionStore.getSessionId(signal.getRecipient());
        messagingTemplate.convertAndSendToUser(sessionId,"/topic/signal",signal,sessionStore.createHeader(sessionId));
    }


    public void sendActiveUsers() {
        messagingTemplate.convertAndSend("/topic/users",sessionStore.getUserInfoMap(),sessionStore.createHeader(null));
    }

    public void sendStreamers() {
        messagingTemplate.convertAndSend("/topic/streamers",sessionStore.getStreamers(),sessionStore.createHeader(null));
    }

}
