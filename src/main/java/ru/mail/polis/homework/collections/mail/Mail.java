package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final T additionalInfo;

    public Mail(String recipient, String sender, T additionalInfo) {
        this.recipient = recipient;
        this.sender = sender;
        this.additionalInfo = additionalInfo;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }
}
