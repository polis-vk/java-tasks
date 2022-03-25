package ru.mail.polis.homework.collections.mail;

public class Mail {

    private final String recipient;
    private final String sender;
    private final String massageContent;

    public Mail(String recipient, String sender, String massageContent) {
        this.recipient = recipient;
        this.sender = sender;
        this.massageContent = massageContent;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getMassageContent() {
        return massageContent;
    }
}
