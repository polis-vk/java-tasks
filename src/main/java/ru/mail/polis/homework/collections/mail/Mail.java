package ru.mail.polis.homework.collections.mail;

public class Mail {
    private String recipient;
    private String sender;

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
}
