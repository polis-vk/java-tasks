package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<Ty> {
    private final String sender;
    private final String recipient;
    private final Ty payload;

    public Mail(String sender, String recipient, Ty payload) {
        this.sender = sender;
        this.recipient = recipient;
        this.payload = payload;
    }

    String getSender() {
        return sender;
    }

    String getRecipient() {
        return recipient;
    }

    Ty getPayload() {
        return payload;
    }
}
