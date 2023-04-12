package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail  {
    private final String sender;
    private final String recipient;

    public Mail(String sender, String recipient) {
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
