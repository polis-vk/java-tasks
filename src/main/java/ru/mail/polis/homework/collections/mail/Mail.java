package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final T info;
    public Mail(String recipient, String sender, T info) {
        this.recipient = recipient;
        this.sender = sender;
        this.info = info;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public T getInfo() {
        return info;
    }
}
