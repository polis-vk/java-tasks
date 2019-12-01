package ru.mail.polis.homework.collections.mail;

public class MailMessage implements Mail {
    private String recipient;
    private String sender;
    private String text;

    public MailMessage(String recipient, String sender, String text) {
        this.recipient = recipient;
        this.sender = sender;
        this.text = text;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public String getSender() {
        return sender;
    }
}
