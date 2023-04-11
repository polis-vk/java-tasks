package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final T mail;

    public Mail(String recipient, String sender, T mail) {
        this.recipient = recipient;
        this.sender = sender;
        this.mail = mail;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public T getMail() {
        return mail;
    }
}
