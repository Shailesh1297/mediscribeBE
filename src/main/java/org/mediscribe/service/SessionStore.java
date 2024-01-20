package org.mediscribe.service;

import org.mediscribe.entity.User;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.HashSet;

import static java.util.Objects.isNull;

@Service
public class SessionStore {
    //userId -> sessionId
    private HashMap<String, String> userSessionMap = new HashMap<String,String>();

    //userId -> userInfo
    private HashMap<String, User> userInfoMap = new HashMap<String, User>();

    //sessionId -> userId
    private HashMap<String,String> sessionUserMap = new HashMap<String, String>();

    private HashSet<String> streamers = new HashSet<>();


    public void addUserAndSession(User user, String session) {
        this.userSessionMap.put(user.getUserId(), session);
        this.sessionUserMap.put(session,user.getUserId());
        this.userInfoMap.put(user.getUserId(), user);
    }

    public void removeSessionByUserId(String userId) {
        String sessionId = getSessionId(userId);
        this.sessionUserMap.remove(sessionId);
        this.userSessionMap.remove(userId);
        this.userInfoMap.remove(userId);
        this.removeStreamer(userId);
    }

    public void removeSessionBySessionId(String sessionId) {
        String userId = getUserId(sessionId);
        this.sessionUserMap.remove(sessionId);
        this.userSessionMap.remove(userId);
        this.userInfoMap.remove(userId);
        this.removeStreamer(userId);
    }

    public HashMap<String, User> getUserInfoMap(){
        return userInfoMap;
    }

    public String getSessionId(String userId) {
        return userSessionMap.get(userId);
    }

    public String getUserId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

    //streaming
    public void addStreamer(String userId) {
        this.streamers.add(userId);
    }

    public void removeStreamer(String userId) {
        this.streamers.remove(userId);
    }

    public HashSet<String> getStreamers(){
        return this.streamers;
    }

    public MessageHeaders createHeader(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if(!isNull(sessionId)) {
            headerAccessor.setSessionId(sessionId);
        }
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
