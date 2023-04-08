package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail {
    private final String recipient;
    private final String sender;

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
