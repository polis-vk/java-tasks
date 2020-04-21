package ru.mail.polis.homework.collections.mail;

public abstract class Mail {
    public Mail(String recipient, String sender) {
        this.recipient = recipient;
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    private final String recipient;
    private final String sender;
}
