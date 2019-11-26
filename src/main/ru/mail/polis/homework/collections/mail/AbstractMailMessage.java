package ru.mail.polis.homework.collections.mail;

public abstract class AbstractMailMessage<T> {
    private String sender;
    private String destination;
    private  T content;

    public AbstractMailMessage(String sender, String destination, T content) {
        this.sender = sender;
        this.destination = destination;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getDestination() {
        return destination;
    }

    public T getContent() {
        return content;
    }
}
