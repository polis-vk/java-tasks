package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {

    private final T body;
    private final String sender;
    private final String recipient;


    public Mail(T body, String sender, String recipient) {
        this.body = body;
        this.sender = sender;
        this.recipient = recipient;
    }

    public T getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}
