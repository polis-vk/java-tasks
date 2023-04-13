package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String receiver;
    private final String sender;
    private final T message;
    Mail(String sender, String receiver, T message) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return receiver;
    }

    public T getMessage() {
        return message;
    }

}
