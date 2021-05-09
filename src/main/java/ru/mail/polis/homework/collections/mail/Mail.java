package ru.mail.polis.homework.collections.mail;

public class Mail {
    private final String sender;
    private final String recipient;

    public Mail(String sender, String receiver) {
        this.sender = sender;
        this.recipient = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}
