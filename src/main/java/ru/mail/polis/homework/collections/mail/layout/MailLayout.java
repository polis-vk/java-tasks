package ru.mail.polis.homework.collections.mail.layout;

public class MailLayout<T> {
    private String sender;
    private String recipient;
    private T message;

    public MailLayout(String sender, String recipient, T message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getMessage() {
        return message;
    }
}