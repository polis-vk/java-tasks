package ru.mail.polis.homework.collections.mail;

public class Mail<T> {
    private final T content;
    private final String sender;
    private final String recipient;

    public Mail(T content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }

    public T getContent() {
        return (T) content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}
