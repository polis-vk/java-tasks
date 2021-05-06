package ru.mail.polis.homework.collections.mail;

public class MailTemplate<T> {
    private final String sender;
    private final String recipient;
    private final T content;

    public MailTemplate(String sender, String recipient, T content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSender() {
        return this.sender;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public T getContent() {
        return content;
    }
}
