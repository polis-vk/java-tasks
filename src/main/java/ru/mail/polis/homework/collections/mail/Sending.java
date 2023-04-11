package ru.mail.polis.homework.collections.mail;

public abstract class Sending {
    private final String sender;
    private final String recipient;

    public Sending(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}
