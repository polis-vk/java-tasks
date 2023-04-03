package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String sender;
    private final String recipient;
    private final T message;

    public Mail(String sender, String recipient, T message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public T getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }
}
