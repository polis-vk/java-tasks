package ru.mail.polis.homework.collections.mail;

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
        return this.recipient;
    }

    public String getSender() {
        return this.sender;
    }

    public T getContent() {
        return this.content;
    }

}
