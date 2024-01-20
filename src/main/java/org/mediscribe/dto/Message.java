package org.mediscribe.dto;

import org.mediscribe.entity.User;
import org.mediscribe.enums.MessageStatus;

import java.util.Date;

public class Message<T> {
    private String id;
    private String type;
    private String recipient;
    private String sender;

    private T content;

    private Date timestamp;
    private MessageStatus status;

    public Message() {
    }

    public Message(String id, String recipient, String sender, MessageStatus status) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}
