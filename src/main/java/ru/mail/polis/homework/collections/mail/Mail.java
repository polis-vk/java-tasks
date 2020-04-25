package ru.mail.polis.homework.collections.mail;

public class Mail<T> {
    private T content;
    private String sender;
    private String recipient;

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
