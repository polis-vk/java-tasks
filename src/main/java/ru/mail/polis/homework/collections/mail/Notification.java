package ru.mail.polis.homework.collections.mail;

public class Notification<C> {
    private final String recipient;
    private final String sender;
    private final C content;


    public Notification(String recipient, String sender, C content) {
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

    public C getContent() {
        return content;
    }
}
