package ru.mail.polis.homework.collections.mail;

public class Mail<T> {
    public Mail(T content, String recipient, String sender) {
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
    }

    public T getContent() {
        return content;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    private final T content;
    private final String recipient;
    private final String sender;
}


