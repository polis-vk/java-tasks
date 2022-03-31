package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String sender;
    private final String recipient;
    private final T message;

    public Mail(String sender, String recipient, T text) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = text;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getText() {
        return message;
    }
}
