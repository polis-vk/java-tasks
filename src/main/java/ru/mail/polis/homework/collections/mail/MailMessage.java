package ru.mail.polis.homework.collections.mail;

public class MailMessage implements Mail{

    private String sender;
    private String recipient;
    private String text;

    public MailMessage(String sender, String recipient, String text) {
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }

}
