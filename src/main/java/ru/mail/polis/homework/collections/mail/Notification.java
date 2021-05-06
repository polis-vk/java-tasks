package ru.mail.polis.homework.collections.mail;

public class Notification<T> {
    private final String recipient;
    private final String sender;
    private final T content;


    public Notification(String recipient, String sender, T content) {
        this.recipient = recipient;
        this.sender = sender;
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public T getContent() {
        return content;
    }
}
