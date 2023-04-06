package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail {
    private String recipient;
    private String sender;

    public Mail(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
