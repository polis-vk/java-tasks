package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final T content;
    private final String recipient;
    private final String sender;


    public Mail(String sender, String recipient, T content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getContent() {
        return content;
    }
}
