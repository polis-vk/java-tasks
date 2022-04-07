package ru.mail.polis.homework.collections.mail;

public class Mail <T> {
    private String sender;
    private String recipient;
    private T message;

    public Mail(String sender, String recipient, T message) {
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
