package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    public final String recipient;
    public final String sender;
    public final T message;

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
