package ru.mail.polis.homework.collections.mail;

public class Message<T> {

    protected final String sender;
    protected final String recipient;
    protected final T content;

    public Message(String sender, String recipient, T content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getContent() {
        return content;
    }
}