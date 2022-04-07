package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String receiver;
    private final String sender;
    private final T message;

    public Mail(String receiver, String sender, T message) {
        this.receiver = receiver;
        this.message = message;
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public T getMessage() {
        return message;
    }
}
