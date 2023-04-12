package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final T message;
    private final String sender;
    private final String recipient;

    public Mail(T message, String sender, String recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }


    public T getMessage() {
        return message;
    }
    public String getSender() {
        return sender;
    }
    public String getRecipient() {
        return recipient;
    }

}
