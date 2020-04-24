package ru.mail.polis.homework.collections.mail;

public class BasicTransfer<T> {
    
    private final T data;
    private final String sender;
    private final String recipient;
    
    public BasicTransfer(T data, String sender, String recipient) {
        this.data = data;
        this.sender = sender;
        this.recipient = recipient;
    }
    
    T getData() {
        return data;
    }
    
    String getSender() {
        return sender;
    }
    
    String getRecipient() {
        return recipient;
    }
}
