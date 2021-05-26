package ru.mail.polis.homework.collections.mail;

public abstract class MessageContent<T> {
    private final String sender;
    private final String recipient;
    private final T info;

    public MessageContent(String sender, String recipient, T info) {
        this.sender = sender;
        this.recipient = recipient;
        this.info = info;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getInfo() {
        return info;
    }
}
