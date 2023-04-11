package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final T message;

    public Mail(String recipient, String sender, T message) {
        this.recipient = recipient;
        this.sender = sender;
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public T getMessage() {
        return message;
    }
}
