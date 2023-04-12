package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final T message;
    private final String recipient;
    private final String sender;

    public Mail(String sender, String recipient, T message) {
        this.sender = sender;
        this.recipient = recipient;
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
