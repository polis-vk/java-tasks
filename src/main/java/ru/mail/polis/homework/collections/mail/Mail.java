package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final T content;

    public Mail(String recipient, String sender, T content) {
        this.recipient = recipient;
        this.sender = sender;
        this.content = content;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public T getContent() {
        return content;
    }
}
