package ru.mail.polis.homework.collections.mail;

public class MailMessage {
    private final String recipient;
    private final String sender;
    private final String text;

    public MailMessage(String recipient, String sender, String text) {
        this.recipient = recipient;
        this.sender = sender;
        this.text = text;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }
}
